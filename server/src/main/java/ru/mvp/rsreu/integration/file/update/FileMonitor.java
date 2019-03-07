package ru.mvp.rsreu.integration.file.update;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.mvp.database.entities.Esls;
import ru.mvp.database.entities.Items;
import ru.mvp.database.repositories.EslsRepository;
import ru.mvp.database.repositories.ItemsRepository;
import ru.mvp.rsreu.integration.file.parsers.ParserFactory;
import ru.mvp.rsreu.integration.file.parsers.TypeEntity;
import ru.mvp.database.LoggerDBTools;
import ru.mvp.rsreu.templates.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.StreamSupport;

/**
 * Created by Art on 14.11.2018.
 */
@Component
@EnableScheduling
public class FileMonitor {
    @Autowired
    EmptySaleTemplate emptySaleTemplate;
    @Autowired
    BaseSaleTemplate baseSaleTemplate;
    @Autowired
    SecondSaleTemplate secondSaleTemplate;

    private final static Logger LOGGER = LoggerFactory.getLogger(FileMonitor.class);
    //разумное время для перезапуска полера
    private final static long CHECK_INTERVAL = 5000;
    //todo Временный хардкод для показа
    private String itemDirectory = "itemDir/";
    private String eslDirectory = "eslDir/";
    private Map<String, FileInfo> fileInfoMap = new HashMap<>();
    ItemsRepository itemsRepository;
    EslsRepository eslsRepository;
    ParserFactory factory;
    LoggerDBTools loggerDBTools;

    public FileMonitor(ItemsRepository itemsRepository, EslsRepository eslsRepository, ParserFactory factory, LoggerDBTools loggerDBTools) {
        this.itemsRepository = itemsRepository;
        this.eslsRepository = eslsRepository;
        this.factory = factory;
        this.loggerDBTools = loggerDBTools;
    }

    @Scheduled(fixedDelay = CHECK_INTERVAL)
    @Transactional
    public void task() {
        LOGGER.debug("Start FileMonitor task. FileInfoMap: {}", fileInfoMap);
        Set<String> currentFileSet = new HashSet<>();
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(Paths.get(itemDirectory))) {
            StreamSupport.stream(ds.spliterator(), false)
                    .filter(e -> {
                        File file = e.toFile();
                        currentFileSet.add(file.getName());
                        return checkChangeFile(file);})
                    .forEach(e -> {
                        List<Items> parse = (List<Items>) factory.getParser(e, TypeEntity.ITEM).parse(e);
                        //todo подумать как сделать поиск дубликатов элегантнее. ща топорно.
                        parse.forEach(el->{
                            if (itemsRepository.findDuplicate(el.getCode(), el.getName(), el.getPrice(), el.getStorageUnit())==null) {
                                Items findByCodeElement = itemsRepository.findByCode(el.getCode());
                                if(findByCodeElement == null){
                                    //новый элемент. Просто вставляем
                                    itemsRepository.save(el);
                                    loggerDBTools.log(new Timestamp(new Date().getTime()),"item", "new", "добавлен новый товар " + el.toString(), "integration");
                                }else{
                                    //такой айдишник есть. Надо апдейтить
                                    el.setId(findByCodeElement.getId());
                                    Collection<Esls> eslsById = findByCodeElement.getEslsById();
                                    if (eslsById!=null) {
                                        for (Esls eslById : eslsById) {
                                            try {
                                                eslById.setCurrentImage(generateImage(el, baseSaleTemplate));
                                                eslById.setNextImage(generateByteImage(el, baseSaleTemplate));
                                                eslsRepository.save(eslById);
                                            } catch (Exception e1) {
                                                System.out.println(e1);
                                                loggerDBTools.log(new Timestamp(new Date().getTime()), "item", "error", "ошибка обновления товара "+e1, "integration");
                                            }
                                        }
                                    }
                                    itemsRepository.save(el);
                                    loggerDBTools.log(new Timestamp(new Date().getTime()), "item", "edit", "обновлен товар <br>было: " + findByCodeElement.toString() + " <br>стало " + el.toString(), "integration");

                                }
                            }
                        });
                        eslsRepository.flush();
                        itemsRepository.flush();
                    });
        } catch (IOException e) {
            LOGGER.error("Catch error: ", e);
        }
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(Paths.get(eslDirectory))) {
            StreamSupport.stream(ds.spliterator(), false)
                    .filter(e -> {
                        File file = e.toFile();
                        currentFileSet.add(file.getName());
                        return checkChangeFile(file);})
                    .forEach(e -> {
                        List<Esls> parse = (List<Esls>) factory.getParser(e, TypeEntity.ESL).parse(e);
                        parse.forEach(el->{
                            if (eslsRepository.findDuplicate(el.getCode(), el.getEslType(), el.getFirmware())==null) {
                                Esls findByCodeElement = eslsRepository.findByCode(el.getCode());
                                if(findByCodeElement == null){
                                    //новый элемент. Просто вставляем
                                    try {
                                        el.setCurrentImage(generateEmptyImage());
                                        el.setNextImage(generateEmptyByteImage());
                                    }catch (Exception e1){
                                        System.out.println(e1);
                                    }
                                    eslsRepository.save(el);
                                    loggerDBTools.log(new Timestamp(new Date().getTime()), "esl", "new", "добавлен новый ценник " + el.toString(), "integration");
                                }else{
                                    //такой айдишник есть. Надо апдейтить
                                    el.setId(findByCodeElement.getId());
                                    eslsRepository.save(el);
                                    loggerDBTools.log(new Timestamp(new Date().getTime()), "esl", "edit", "обновлен ценник <br>было: " + findByCodeElement.toString() + " <br>стало " + el.toString(), "integration");
                                }
                            }
                        });
                        eslsRepository.flush();
                    });
        } catch (IOException e) {
            LOGGER.error("Catch error: ", e);
        }
        //удаляем из мапы старые файлы(нужно если файлы из папки будут удаляться)
        cleanOldEntry(currentFileSet);
        LOGGER.debug("End FileMonitor task. FileInfoMap: {}", fileInfoMap);
    }

    private boolean checkChangeFile(File file) {
        String fileName = file.getName();
        if (!fileInfoMap.containsKey(fileName)) {
            FileInfo fileInfo = new FileInfo(file.length(), file.lastModified());
            fileInfoMap.put(fileName, fileInfo);
            LOGGER.debug("FileInfoMap doesn't contains key: {}. Add new FileInfo: {}", fileName, fileInfo);
            return false;
        }
        FileInfo fileInfo = fileInfoMap.get(fileName);
        long currentTime = new Date().getTime();
        //основное условие, по которому понимаем что файл не изменяется и готов к обработке
        boolean primaryFlag = fileInfo.getLastSize() == file.length() &&
                fileInfo.getLastUpdate() == file.lastModified() &&
                currentTime - fileInfo.lastUpdate > CHECK_INTERVAL;
        //если тру то файл давно обработан и не трогаем его
        if (primaryFlag && fileInfo.isDone()) {
            LOGGER.debug("File: {} has been processed previously", fileName);
            return false;
        } else if (primaryFlag && !fileInfo.isDone()) {
            fileInfo.setDone(true);
            fileInfoMap.put(fileName, fileInfo);
            LOGGER.debug("File: {} is ready for processing", fileName);
            return true;
        } else {
            fileInfo.setLastSize(file.length());
            fileInfo.setLastUpdate(file.lastModified());
            fileInfo.setDone(false);
            fileInfoMap.put(fileName, fileInfo);
            LOGGER.debug("File: {} is modified and not ready", fileName);
            return false;
        }
    }

    private void cleanOldEntry(Set<String> currentPathSet){
        Iterator<Map.Entry<String, FileInfo>> iterator = fileInfoMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, FileInfo> entry = iterator.next();
            String key = entry.getKey();
            if(!currentPathSet.contains(key)){
                iterator.remove();
                LOGGER.info("Remove old entry: {}", key);
            }
        }
    }

    /**
     * Класс для хранения атрибутов файлов
     * */
    private class FileInfo {
        private long lastSize;
        private long lastUpdate;
        //обработан ли файл
        private boolean done;

        FileInfo(long lastSize, long lastUpdate) {
            this.lastSize = lastSize;
            this.lastUpdate = lastUpdate;
        }

        long getLastSize() {
            return lastSize;
        }

        void setLastSize(long lastSize) {
            this.lastSize = lastSize;
        }

        long getLastUpdate() {
            return lastUpdate;
        }

        void setLastUpdate(long lastUpdate) {
            this.lastUpdate = lastUpdate;
        }

        public boolean isDone() {
            return done;
        }

        public void setDone(boolean done) {
            this.done = done;
        }

        @Override
        public String toString() {
            return "FileInfo{" +
                    "lastSize=" + lastSize +
                    ", lastUpdate=" + lastUpdate +
                    ", done=" + done +
                    '}';
        }
    }





    //дубликат УБРАТЬ

    private byte[] generateImage(Items items, SaleTemplate saleTemplate) throws IOException{
        BufferedImage image = getBufferedImage(items, saleTemplate);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.getWriterFormatNames();
        if (ImageIO.write(image, "BMP", baos)) {
            return baos.toByteArray();
//            String data = DatatypeConverter.printBase64Binary(baos.toByteArray());
//            return "data:image/bmp;base64," + data;
        }else{
            throw new IOException();
        }
    }
    private byte[] generateEmptyImage() throws IOException{
        BufferedImage image = getBufferedImage();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.getWriterFormatNames();
        if (ImageIO.write(image, "BMP", baos)) {
            return baos.toByteArray();
//            String data = DatatypeConverter.printBase64Binary(baos.toByteArray());
//            return "data:image/bmp;base64," + data;
        }else{
            throw new IOException();
        }
    }
    private byte[] generateByteImage(Items items, SaleTemplate saleTemplate) throws IOException{
        BufferedImage image = getBufferedImage(items, saleTemplate);
        List<Integer> outTemp = new ArrayList<>();
        for (int y=0 ; y < image.getHeight() ; y++)
            for (int x=0 ; x < image.getWidth() ; x++){
                int sample = image.getRaster().getSample(x, y, 0);
                outTemp.add(sample == 0 ? 1 : 0);
            }
        return encodeToByteArray(outTemp);
    }
    private byte[] generateEmptyByteImage() throws IOException{
        BufferedImage image = getBufferedImage();
        List<Integer> outTemp = new ArrayList<>();
        for (int y=0 ; y < image.getHeight() ; y++)
            for (int x=0 ; x < image.getWidth() ; x++){
                int sample = image.getRaster().getSample(x, y, 0);
                outTemp.add(sample == 0 ? 1 : 0);
            }
        return encodeToByteArray(outTemp);
    }

    private BufferedImage getBufferedImage(Items items, SaleTemplate saleTemplate) {
        int width = 152;
        int height = 152;
        EslInfoTemplate eslInfoTemplate = new EslInfoTemplate(items.getName(),
                items.getName(),
                String.valueOf(items.getPrice() + 10),
                String.valueOf(items.getPrice()),
                "рублей",
                items.getCode());
        return saleTemplate.drawEsl(eslInfoTemplate, width, height);
    }
    private BufferedImage getBufferedImage() {
        int width = 152;
        int height = 152;
        return emptySaleTemplate.drawEsl(null, width, height);
    }

    private static byte[] encodeToByteArray(List<Integer> inputArray) {
        byte[] results = new byte[(inputArray.size() + 7) / 8];
        int byteValue = 0;
        int index;
        for (index = 0; index < inputArray.size(); index++) {

            byteValue = (byteValue << 1) | inputArray.get(index);

            if (index %8 == 7) {
                results[index / 8] = (byte) byteValue;
            }
        }
//игнор при котором у нас не влезает все в массив
//        if (index % 8 != 0) {
//            results[index / 8] = (byte) byteValue << (8 - (index % 8));
//        }

        return results;
    }
}
