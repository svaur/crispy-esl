package ru.mvp.rsreu.controllers;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mvp.database.entities.Esls;
import ru.mvp.database.entities.Items;
import ru.mvp.database.repositories.EslsRepository;
import ru.mvp.database.repositories.ItemsRepository;
import ru.mvp.rsreu.templates.BaseSaleTemplate;
import ru.mvp.rsreu.templates.EslInfoTemplate;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class EslApiController {

    private static final String EMPTY_STRING = "";
    private EslsRepository eslsRepository;
    private ItemsRepository itemsRepository;
    private BaseSaleTemplate baseSaleTemplate;

    @Autowired
    public EslApiController(EslsRepository eslsRepository, ItemsRepository itemsRepository, BaseSaleTemplate baseSaleTemplate) {
        this.eslsRepository = eslsRepository;
        this.itemsRepository = itemsRepository;
        this.baseSaleTemplate = baseSaleTemplate;
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
        if ("add".equalsIgnoreCase(type)) {
            try {
                byte[] generateImage = generateImage(itemElement);
                eslElement.setNextImage(generateImage);
                itemElement.setEslsByEslId(eslElement);
            } catch (IOException e) {
                return "error " + e.getMessage();
            }
        } else {
            eslElement.setNextImage(null);
            itemElement.getEslsByEslId().remove
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
                String.valueOf(items.getPrice()),
                String.valueOf(items.getStorageUnit()),
                "рублей",
                items.getCode());
        BufferedImage image = baseSaleTemplate.drawEsl(eslInfoTemplate, width, height);

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
}
