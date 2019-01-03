//package ru.mvp.rsreu.integration.file.update;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import ru.mvp.rsreu.db.dao.ESLDao;
//import ru.mvp.rsreu.db.dao.ItemDao;
//import ru.mvp.rsreu.db.entity.ESL;
//import ru.mvp.rsreu.db.entity.Item;
//import ru.mvp.rsreu.integration.file.parsers.ParserFactory;
//import ru.mvp.rsreu.integration.file.parsers.TypeEntity;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.DirectoryStream;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.*;
//import java.util.stream.StreamSupport;
//
///**
// * Created by Art on 14.11.2018.
// */
//@Component
//@EnableScheduling
//public class FileMonitor {
//
//    private final static Logger LOGGER = LoggerFactory.getLogger(FileMonitor.class);
//    //разумное время для перезапуска полера
//    private final static long CHECK_INTERVAL = 5000;
//    //todo Временный хардкод для показа
//    private String itemDirectory = "server/src/main/resources/itemDir";
//    private String eslDirectory = "server/src/main/resources/eslDir/";
//    private Map<String, FileInfo> fileInfoMap = new HashMap<>();
//    @Autowired
//    ItemDao itemService;
//    @Autowired
//    ESLDao eslService;
//    @Autowired
//    ParserFactory factory;
//
//
//    @Scheduled(fixedDelay = CHECK_INTERVAL)
//    public void task() {
//        LOGGER.debug("Start FileMonitor task. FileInfoMap: {}", fileInfoMap);
//        Set<String> currentFileSet = new HashSet<>();
//        try (DirectoryStream<Path> ds = Files.newDirectoryStream(Paths.get(itemDirectory))) {
//            StreamSupport.stream(ds.spliterator(), false)
//                    .filter(e -> {
//                        File file = e.toFile();
//                        currentFileSet.add(file.getName());
//                        return checkChangeFile(file);})
//                    .forEach(e -> {
//                        List<Item> parse = (List<Item>)factory.getParser(e, TypeEntity.ITEM).parse(e);
//                        itemService.insertOrUpdateItems(parse);
//                    });
//        } catch (IOException e) {
//            LOGGER.error("Catch error: ", e);
//        }
//        try (DirectoryStream<Path> ds = Files.newDirectoryStream(Paths.get(eslDirectory))) {
//            StreamSupport.stream(ds.spliterator(), false)
//                    .filter(e -> {
//                        File file = e.toFile();
//                        currentFileSet.add(file.getName());
//                        return checkChangeFile(file);})
//                    .forEach(e -> {
//                        List<ESL> parse = (List<ESL>)factory.getParser(e, TypeEntity.ESL).parse(e);
//                        eslService.insertOrUpdateEsls(parse);
//                    });
//        } catch (IOException e) {
//            LOGGER.error("Catch error: ", e);
//        }
//        //удаляем из мапы старые файлы(нужно если файлы из папки будут удаляться)
//        cleanOldEntry(currentFileSet);
//        LOGGER.debug("End FileMonitor task. FileInfoMap: {}", fileInfoMap);
//    }
//
//    private boolean checkChangeFile(File file) {
//        String fileName = file.getName();
//        if (!fileInfoMap.containsKey(fileName)) {
//            FileInfo fileInfo = new FileInfo(file.length(), file.lastModified());
//            fileInfoMap.put(fileName, fileInfo);
//            LOGGER.debug("FileInfoMap doesn't contains key: {}. Add new FileInfo: {}", fileName, fileInfo);
//            return false;
//        }
//        FileInfo fileInfo = fileInfoMap.get(fileName);
//        long currentTime = new Date().getTime();
//        //основное условие, по которому понимаем что файл не изменяется и готов к обработке
//        boolean primaryFlag = fileInfo.getLastSize() == file.length() &&
//                fileInfo.getLastUpdate() == file.lastModified() &&
//                currentTime - fileInfo.lastUpdate > CHECK_INTERVAL;
//        //если тру то файл давно обработан и не трогаем его
//        if (primaryFlag && fileInfo.isDone()) {
//            LOGGER.debug("File: {} has been processed previously", fileName);
//            return false;
//        } else if (primaryFlag && !fileInfo.isDone()) {
//            fileInfo.setDone(true);
//            fileInfoMap.put(fileName, fileInfo);
//            LOGGER.debug("File: {} is ready for processing", fileName);
//            return true;
//        } else {
//            fileInfo.setLastSize(file.length());
//            fileInfo.setLastUpdate(file.lastModified());
//            fileInfo.setDone(false);
//            fileInfoMap.put(fileName, fileInfo);
//            LOGGER.debug("File: {} is modified and not ready", fileName);
//            return false;
//        }
//    }
//
//    private void cleanOldEntry(Set<String> currentPathSet){
//        Iterator<Map.Entry<String, FileInfo>> iterator = fileInfoMap.entrySet().iterator();
//        while (iterator.hasNext()){
//            Map.Entry<String, FileInfo> entry = iterator.next();
//            String key = entry.getKey();
//            if(!currentPathSet.contains(key)){
//                iterator.remove();
//                LOGGER.info("Remove old entry: {}", key);
//            }
//        }
//    }
//
//    /**
//     * Класс для хранения атрибутов файлов
//     * */
//    private class FileInfo {
//        private long lastSize;
//        private long lastUpdate;
//        //обработан ли файл
//        private boolean done;
//
//        public FileInfo(long lastSize, long lastUpdate) {
//            this.lastSize = lastSize;
//            this.lastUpdate = lastUpdate;
//        }
//
//        public long getLastSize() {
//            return lastSize;
//        }
//
//        public void setLastSize(long lastSize) {
//            this.lastSize = lastSize;
//        }
//
//        public long getLastUpdate() {
//            return lastUpdate;
//        }
//
//        public void setLastUpdate(long lastUpdate) {
//            this.lastUpdate = lastUpdate;
//        }
//
//        public boolean isDone() {
//            return done;
//        }
//
//        public void setDone(boolean done) {
//            this.done = done;
//        }
//
//        @Override
//        public String toString() {
//            return "FileInfo{" +
//                    "lastSize=" + lastSize +
//                    ", lastUpdate=" + lastUpdate +
//                    ", done=" + done +
//                    '}';
//        }
//    }
//}
