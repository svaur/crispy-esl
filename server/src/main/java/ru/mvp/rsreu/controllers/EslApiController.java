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
import ru.mvp.database.repositories.EslsRepository;
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
    private BaseSaleTemplate baseSaleTemplate;

    @Autowired
    public EslApiController(EslsRepository eslsRepository, BaseSaleTemplate baseSaleTemplate) {
        this.eslsRepository = eslsRepository;
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

//    @RequestMapping("/api/assignEsl")
//    public String assignEsl(@RequestParam("esl") String esl,
//                            @RequestParam("template") String template,
//                            @RequestParam("item") String item,
//                            @RequestParam("type") String type) {
//        boolean result;
//        ESL eslElement = eslDao.searchByESLCode(esl);
//        Item itemElement = itemDao.searchByItemCode(item);
//        if ("add".equalsIgnoreCase(type)) {
//            result = eslDao.assignItem(eslElement, itemElement);
//        } else {
//            result = eslDao.unAssignItem(eslElement);
//        }
//        return result ? "ok" : "error";
//    }
//
//    @RequestMapping("/api/getImage")
//    public String getImage(@RequestParam("eslCode") String eslCode) throws IOException {
//        int width = 152;
//        int height = 152;
//        Item selectedGood = eslDao.searchByESLCode(eslCode).getItem();
//        EslInfoTemplate eslInfoTemplate = new EslInfoTemplate(selectedGood.getItemName(),
//                selectedGood.getItemName(),
//                String.valueOf(selectedGood.getPrice()),
//                String.valueOf(selectedGood.getPromotionPrice()),
//                "рублей",
//                selectedGood.getItemCode());
//        BufferedImage image = baseSaleTemplate.drawEsl(eslInfoTemplate, width, height);
//
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        ImageIO.getWriterFormatNames();
//        if (ImageIO.write(image, "BMP", baos)) {
//            String data = DatatypeConverter.printBase64Binary(baos.toByteArray());
//            String imageString = "data:image/bmp;base64," + data;
//            return new Gson().toJson(imageString);
//        }
//        return "error";//todo сделать нормальный возврат ошибок на фронт
//    }

    private List<HashMap<String, String>> fillEslData(Page<Esls> e) {
        List<HashMap<String, String>> outList= new ArrayList<>();
        e.forEach(element->{
            HashMap<String, String> map = new HashMap<>();
            map.put("eslCode", element.getCode());
            map.put("eslType", element.getEsltype());
            map.put("itemCode", element.getItemsById() == null ? EMPTY_STRING : element.getItemsById().getCode());
            map.put("itemName", element.getItemsById() == null ? EMPTY_STRING : element.getItemsById().getName());
            //todo нет цены
            map.put("price", element.getItemsById() == null ? EMPTY_STRING : "todo цена");
            map.put("lastUpdate", element.getLastUpdate().toString());
            map.put("connectivity", element.getConnectivity());
            map.put("batteryLevel", element.getBatteryLevel());
            map.put("status", element.getStatus());
            outList.add(map);});
        return outList;
    }
}
