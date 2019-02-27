package ru.mvp.rsreu.controllers;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mvp.database.entities.Esls;
import ru.mvp.database.entities.Items;
import ru.mvp.database.repositories.EslsRepository;
import ru.mvp.database.repositories.ItemsRepository;
import ru.mvp.rsreu.templates.BaseSaleTemplate;
import ru.mvp.rsreu.templates.EslInfoTemplate;
import ru.mvp.database.LoggerDBTools;
import ru.mvp.rsreu.tools.RestClient;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
public class EslApiController {

    private static final String EMPTY_STRING = "";
    private EslsRepository eslsRepository;
    private ItemsRepository itemsRepository;
    private BaseSaleTemplate baseSaleTemplate;
    private LoggerDBTools loggerDBTools;
    private RestClient restClient;

    @Autowired
    public EslApiController(EslsRepository eslsRepository, ItemsRepository itemsRepository, BaseSaleTemplate baseSaleTemplate, LoggerDBTools loggerDBTools, RestClient restClient) {
        this.eslsRepository = eslsRepository;
        this.itemsRepository = itemsRepository;
        this.baseSaleTemplate = baseSaleTemplate;
        this.loggerDBTools = loggerDBTools;
        this.restClient = restClient;
    }

    @RequestMapping("/api/getEslTableData")
    public String getEslTableData(@RequestParam(value = "size") Integer size,
                                  @RequestParam(value = "pageNum") Integer pageNum,
                                  @RequestParam(value = "searchValue") String searchValue) {
        Page<Esls> output;
        if (searchValue.isEmpty())
            output = eslsRepository.findAll(PageRequest.of(pageNum, size, Sort.Direction.ASC, "code"));
        else
            output = eslsRepository.findByFilter(PageRequest.of(pageNum, size, Sort.Direction.ASC, "code"), searchValue);

        return new Gson().toJson(fillEslData(output));
    }

    @RequestMapping("/api/assignEsl")
    public String assignEsl(@RequestParam("esl") String esl,
                            @RequestParam("template") String template,
                            @RequestParam("item") String item,
                            @RequestParam("type") String type) {
        Esls eslElement = eslsRepository.findByCode(esl);
        Items itemElement = itemsRepository.findByCode(item);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if ("add".equalsIgnoreCase(type)) {
            try {
                byte[] generateImage = generateImage(itemElement);
                eslElement.setNextImage(generateImage);
                itemElement.setEslsByEslId(eslElement);
            } catch (IOException e) {
                return "error " + e.getMessage();
            }
            loggerDBTools.log(new Timestamp(new Date().getTime()), "assignEsl", "assign", "привязан товар " + item + " " + itemElement.getName() + " к ценнику " + esl, user.getUsername());
        } else {
            eslElement.setNextImage(null);
            itemElement.setEslsByEslId(null);
            loggerDBTools.log(new Timestamp(new Date().getTime()), "assignEsl", "unassign", "отвязан товар " + item + " " + itemElement.getName() +" от ценника " + esl, user.getUsername());
        }
        itemsRepository.saveAndFlush(itemElement);
        eslsRepository.saveAndFlush(eslElement);
        return "ok";
    }

    @RequestMapping("/api/getImage")
    public String getImage(@RequestParam("eslCode") String eslCode){
        Esls code = eslsRepository.findByCode(eslCode);
        //todo для проверки.
        byte[] currentImage = code.getNextImage();
        if (currentImage !=null) {
            String data = "data:image/bmp;base64," + DatatypeConverter.printBase64Binary(currentImage);
            return new Gson().toJson(data);
        }
        return "ERROR";//todo сделать нормальный возврат ошибок на фронт
    }

    @RequestMapping("/api/updateEsl")
    public String updateEsl(@RequestParam("eslCode") String eslCode){
        String server = "http://127.0.0.1:8090";
        return restClient.get(server, "/api/updateEsl?esl="+eslCode);
    }

    private List<HashMap<String, String>> fillEslData(Page<Esls> e) {
        List<HashMap<String, String>> outList= new ArrayList<>();
        e.forEach(element->{
            HashMap<String, String> map = new HashMap<>();
            map.put("eslCode", element.getCode());
            map.put("eslType", element.getEslType());
            map.put("eslFirmWare", element.getFirmware());
            map.put("itemCode", element.getItemsById() == null ? EMPTY_STRING : element.getItemsById().getCode());
            map.put("itemName", element.getItemsById() == null ? EMPTY_STRING : element.getItemsById().getName());
            map.put("price", element.getItemsById() == null ? EMPTY_STRING : element.getItemsById().getPrice().toString());
            map.put("lastUpdate", element.getLastUpdate() == null ? EMPTY_STRING : element.getLastUpdate().toString());
            map.put("connectivity", element.getConnectivity());
            map.put("batteryLevel", element.getBatteryLevel());
            map.put("status", element.getStatus());
            outList.add(map);});
        return outList;
    }
    private byte[] generateImage(Items items) throws IOException{
        int width = 152;
        int height = 152;
        EslInfoTemplate eslInfoTemplate = new EslInfoTemplate(items.getName(),
                items.getName(),
                String.valueOf(items.getPrice()+10),
                String.valueOf(items.getPrice()),
                "рублей",
                items.getCode());
        BufferedImage image = baseSaleTemplate.drawEsl(eslInfoTemplate, width, height);
        List<Integer> outTemp = new ArrayList<>();
        for (int y=0 ; y < image.getHeight() ; y++)
            for (int x=0 ; x < image.getWidth() ; x++){
                //for (int c=0 ; c < image.getRaster().getNumBands() ; c++) {
                    int sample = image.getRaster().getSample(x, y, 0);
                    outTemp.add(sample == 0 ? 0 : 1);
                }
        return encodeToByteArray(outTemp);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        ImageIO.getWriterFormatNames();
//        if (ImageIO.write(image, "BMP", baos)) {
//            return baos.toByteArray();
////            String data = DatatypeConverter.printBase64Binary(baos.toByteArray());
////            return "data:image/bmp;base64," + data;
//        }else{
//            throw new IOException();
//        }
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
