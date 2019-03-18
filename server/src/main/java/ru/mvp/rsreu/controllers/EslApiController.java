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
import ru.mvp.rsreu.templates.SaleTemplate;
import ru.mvp.rsreu.templates.SecondSaleTemplate;
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
//    private List<SaleTemplate> saleTemplateList;
    private BaseSaleTemplate bsaleTemplateList;
    private SecondSaleTemplate ssaleTemplateList;
    private LoggerDBTools loggerDBTools;
    private RestClient restClient;

    @Autowired
    public EslApiController(EslsRepository eslsRepository,
                            ItemsRepository itemsRepository,
                            //List<SaleTemplate> saleTemplateList,
                            BaseSaleTemplate bsaleTemplateList,
                            SecondSaleTemplate ssaleTemplateList,
                            LoggerDBTools loggerDBTools,
                            RestClient restClient) {
        this.eslsRepository = eslsRepository;
        this.itemsRepository = itemsRepository;
//        this.saleTemplateList = saleTemplateList;
        this.loggerDBTools = loggerDBTools;
        this.bsaleTemplateList = bsaleTemplateList;
        this.ssaleTemplateList = ssaleTemplateList;
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
                byte[] generateByteImage = generateByteImage(itemElement);
                eslElement.setNextImage(generateByteImage);
                eslElement.setCurrentImage(generateImage);
                eslElement.setItemsByItemsId(itemElement);
            } catch (IOException e) {
                return "error " + e.getMessage();
            }
            loggerDBTools.log(new Timestamp(new Date().getTime()), "assignEsl", "assign", "привязан товар " + item + " " + itemElement.getName() + " к ценнику " + esl, user.getUsername());
        } else {
            eslElement.setNextImage(null);
            eslElement.setItemsByItemsId(null);
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
        byte[] currentImage = code.getCurrentImage();
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
    @RequestMapping("/api/updateEslGroup")
    public String updateEslGroup(@RequestParam("taskId") String taskId){
        String server = "http://127.0.0.1:8090";
        return restClient.get(server, "/api/updateEslGroup?taskId="+taskId);
    }
    @RequestMapping("/api/sendFunPic")
    public String sendFunPic(){
        String server = "http://127.0.0.1:8090";
        return restClient.get(server, "/api/sendFunPic");
    }

    private List<HashMap<String, String>> fillEslData(Page<Esls> e) {
        List<HashMap<String, String>> outList= new ArrayList<>();
        e.forEach(element->{
            HashMap<String, String> map = new HashMap<>();
            map.put("eslCode", element.getCode());
            map.put("eslType", element.getEslType());
            map.put("eslFirmWare", element.getFirmware());
            map.put("itemCode", element.getItemsByItemsId() == null ? EMPTY_STRING : element.getItemsByItemsId().getCode());
            map.put("itemName", element.getItemsByItemsId() == null ? EMPTY_STRING : element.getItemsByItemsId().getName());
            map.put("price", element.getItemsByItemsId() == null ? EMPTY_STRING : element.getItemsByItemsId().getSecondPrice().toString());
            map.put("lastUpdate", element.getLastUpdate() == null ? EMPTY_STRING : element.getLastUpdate().toString());
            map.put("connectivity", element.getConnectivity());
            map.put("batteryLevel", element.getBatteryLevel());
            map.put("status", element.getStatus());
            outList.add(map);});
        return outList;
    }
    private byte[] generateImage(Items items) throws IOException{
        BufferedImage image = getBufferedImage(items);

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
    private byte[] generateByteImage(Items items) throws IOException{
        BufferedImage image = getBufferedImage(items);
        List<Integer> outTemp = new ArrayList<>();
        for (int y=0 ; y < image.getHeight() ; y++)
            for (int x=0 ; x < image.getWidth() ; x++){
                int sample = image.getRaster().getSample(x, y, 0);
                outTemp.add(sample == 0 ? 1 : 0);
            }
        return encodeToByteArray(outTemp);
    }

    private BufferedImage getBufferedImage(Items items) {
        int width = 152;
        int height = 152;
        SaleTemplate saleTemplate = items.getAction().equals("1")?bsaleTemplateList:ssaleTemplateList;
        EslInfoTemplate eslInfoTemplate = new EslInfoTemplate(items.getName(),
                "",
                String.valueOf(items.getPrice()),
                String.valueOf(items.getSecondPrice()),
                "рублей",
                items.getCode());
        return saleTemplate.drawEsl(eslInfoTemplate, width, height);
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
