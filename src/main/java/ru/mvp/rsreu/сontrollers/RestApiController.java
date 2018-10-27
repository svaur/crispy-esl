package ru.mvp.rsreu.сontrollers;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mvp.rsreu.db.dao.ESLDao;
import ru.mvp.rsreu.db.dao.ESLService;
import ru.mvp.rsreu.db.dao.ItemDao;
import ru.mvp.rsreu.db.dao.ItemService;
import ru.mvp.rsreu.db.entity.ESL;
import ru.mvp.rsreu.templates.BaseTemplate;
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
public class RestApiController {

    @RequestMapping("/api/getTableData")
    public String getTableData() {
        List<HashMap<String, String>> test = new ArrayList<>();
        ESLDao eslDao = new ESLService();
        List<ESL> list = eslDao.getAll();
        list.stream().forEach(e -> {
            HashMap<String, String> hashMap = new HashMap<>();
            Item item = e.getItem();
            hashMap.put("key1", e.getElsCode());
            hashMap.put("key2", e.getElsType());
            hashMap.put("key3", item.getItemCode());
            hashMap.put("key4", item.getItemName());
            hashMap.put("key5", String.valueOf(item.getPrice()));
            hashMap.put("key6", String.valueOf(e.getLastUpdate()));
            hashMap.put("key7", String.valueOf(e.isConnectivity()));
            hashMap.put("key8", String.valueOf(e.getBatteryLevel()));
            hashMap.put("key9", String.valueOf(e.isStatus())); //todo поменять тип
            test.add(hashMap);
        });
        Gson g = new Gson();
        return g.toJson(test);
    }

    @RequestMapping("/api/getAnotherTableData")
    public String getAnotherTableData() {
        List<HashMap<String, String>> test = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("key0", i + "тест тест тест тест " + i);
            hashMap.put("key1", i + "тест тест тест тест " + i);
            hashMap.put("key2", i + "тест тест тест тест " + i);
            hashMap.put("key3", i + "тест тест тест тест " + i);
            hashMap.put("key4", i + "тест тест тест тест " + i);
            hashMap.put("key5", i + "тест тест тест тест " + i);
            hashMap.put("key6", i + "тест тест тест тест " + i);
            hashMap.put("key7", i + "тест тест тест тест " + i);
            hashMap.put("key8", i + "тест тест тест тест " + i);
            hashMap.put("key9", i + "тест тест тест тест " + i);
            test.add(hashMap);
        }
        Gson g = new Gson();
        return g.toJson(test);
    }

    @RequestMapping("/api/getRandStr")
    public String getRandStr() {

        return "kjlklk";
    }

    @Autowired
    BaseTemplate baseTemplate;

    @RequestMapping("/api/getImage")
    public String getImage(@RequestParam("eslId") String eslId) throws IOException {
        int width = 200;
        int height = 200;
        EslInfoTemplate eslInfoTemplate = new EslInfoTemplate("мужские перчатки",
                "из натуральной кожи"+eslId,
                "2 400",
                "1 678",
                "рублей",
                "2343254234523452345");
        BufferedImage image = baseTemplate.drawEsl(eslInfoTemplate, width, height);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);

        String data = DatatypeConverter.printBase64Binary(baos.toByteArray());
        String imageString = "data:image/png;base64," + data;
        Gson gson = new Gson();
        return gson.toJson(imageString);
    }
}
