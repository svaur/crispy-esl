package ru.mvp.rsreu.сontrollers;

import com.google.gson.Gson;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mvp.rsreu.db.dao.ESLDao;
import ru.mvp.rsreu.db.dao.ESLService;
import ru.mvp.rsreu.db.entity.ESL;
import ru.mvp.rsreu.fontedit.FontEditor;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class RestApiController {
    @Autowired
    FontEditor fontEditor;

    @RequestMapping("/api/getTableData")
    public String getTableData() {
        List<HashMap<String, String>> test = new ArrayList<>();
        ESLDao eslDao = new ESLService();
        List<ESL> list = eslDao.getAll();
        list.stream().forEach(e -> {
            HashMap<String, String> hashMap = new HashMap<>();
//            hashMap.put("key1", String.valueOf(e.getNumber()));
//            hashMap.put("key2", e.getLocation());
            hashMap.put("key3", String.valueOf(e.isStatus()));//todo геттер
//            hashMap.put("key4", String.valueOf(e.getBattery()));
//            hashMap.put("key5", e.getMerchandise().getArticleNumber());
//            hashMap.put("key6", e.getMerchandise().getName());
//            hashMap.put("key7", String.valueOf(e.getMerchandise().getQuantity()));
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
    public String getRandStr() throws IOException {

        return "kjlklk";
    }


    @RequestMapping("/api/getImage")
    public String getImage() throws IOException {
        int width = 200;
        int height = 200;
        BufferedImage image = new BufferedImage(
                width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, width, height);
        // create a string with yellow
        g2d.setColor(Color.BLACK);

        fontEditor.drawString(new Font("TimesRoman", Font.BOLD, 12),
                "мужские перчатки",
                g2d,
                width,
                10);
        fontEditor.drawString(new Font("TimesRoman", Font.PLAIN, 12),
                "из натуральной",
                g2d,
                width,
                20);
        fontEditor.drawString(new Font("TimesRoman", Font.PLAIN, 22),
                "2 400",
                g2d,
                width,
                60);
        fontEditor.drawString(new Font("TimesRoman", Font.BOLD, 30),
                "1 678",
                g2d,
                width,
                120);
        fontEditor.drawString(new Font("TimesRoman", Font.BOLD, 10),
                "рублей",
                g2d,
                width,
                160);
        fontEditor.drawString(new Font("TimesRoman", Font.PLAIN, 10),
                "2343254234523452345",
                g2d,
                width,
                190);
        // Disposes of this graphics context and releases any system resources that it is using.
        g2d.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);

        String data = DatatypeConverter.printBase64Binary(baos.toByteArray());
        String imageString = "data:image/png;base64," + data;
        String outImage = "<img src='" + imageString + "'>";
        Gson gson = new Gson();
        return gson.toJson(outImage);
    }


}
