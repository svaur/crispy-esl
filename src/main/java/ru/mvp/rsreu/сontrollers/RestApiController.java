package ru.mvp.rsreu.сontrollers;

import com.google.gson.Gson;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mvp.rsreu.db.dao.ESLDao;
import ru.mvp.rsreu.db.dao.ESLService;
import ru.mvp.rsreu.db.entity.ESL;
import ru.mvp.rsreu.db.entity.Item;
import ru.mvp.rsreu.db.util.HibernateUtil;
import ru.mvp.rsreu.templates.BaseSaleTemplate;
import ru.mvp.rsreu.templates.EslInfoTemplate;

import javax.imageio.ImageIO;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class RestApiController {

    private ESLDao eslDao;

    @Autowired
    public RestApiController(ESLDao eslDao) {
        this.eslDao=eslDao;
    }

    @RequestMapping("/api/getTableData")
    public String getTableData(@RequestParam(value = "size", required = false, defaultValue = "10") String size) {//todo получать мапу? не станет ли избыточным?
        int showSize = Integer.valueOf(size);
        List<HashMap<String, String>> tableData = new ArrayList<>(showSize);
        List<ESL> list = eslDao.getAll(showSize);
        list.stream().forEach(e -> {
            HashMap<String, String> hashMap = new HashMap<>();
            Item item = e.getItem();
            hashMap.put("elsCode", e.getElsCode());
            hashMap.put("elsType", e.getElsType());
            hashMap.put("itemCode", item.getItemCode());
            hashMap.put("itemName", item.getItemName());
            hashMap.put("price", String.valueOf(item.getPromotionPrice()));
            hashMap.put("lastUpdate", String.valueOf(e.getLastUpdate()));
            hashMap.put("connectivity", String.valueOf(e.isConnectivity()));
            hashMap.put("batteryLevel", String.valueOf(e.getBatteryLevel()));
            hashMap.put("status", String.valueOf(e.isStatus())); //todo поменять тип
            tableData.add(hashMap);
        });
        Gson g = new Gson();
        return g.toJson(tableData);
    }

    @RequestMapping("/api/searchData")
    public String getTableData(@RequestParam(value = "size", required = false, defaultValue = "10") String size,    //todo для показа конечно и так сойдет, но уж дюже похоже на предыдущий метод, фабрика ESLDao
                               @RequestParam(value = "searchValue") String searchValue) {                           //todo получать мапу? не станет ли избыточным?
        int showSize = Integer.valueOf(size);
        List<HashMap<String, String>> tableData = new ArrayList<>(showSize);
        List<ESL> list = eslDao.searchByValue(searchValue, showSize);
        list.stream().forEach(e -> {
            HashMap<String, String> hashMap = new HashMap<>();
            Item item = e.getItem();
            hashMap.put("elsCode", e.getElsCode());
            hashMap.put("elsType", e.getElsType());
            hashMap.put("itemCode", item.getItemCode());
            hashMap.put("itemName", item.getItemName());
            hashMap.put("price", String.valueOf(item.getPromotionPrice()));
            hashMap.put("lastUpdate", String.valueOf(e.getLastUpdate()));
            hashMap.put("connectivity", String.valueOf(e.isConnectivity()));
            hashMap.put("batteryLevel", String.valueOf(e.getBatteryLevel()));
            hashMap.put("status", String.valueOf(e.isStatus())); //todo поменять тип
            tableData.add(hashMap);
        });
        Gson g = new Gson();
        return g.toJson(tableData);
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

    @RequestMapping("/api/assignEsl")
    public String assignEsl(@RequestParam("esl") String esl,
                            @RequestParam("template") String template,
                            @RequestParam("item") String item,
                            @RequestParam("type") String type) {
        System.out.println(type);
        Session session = HibernateUtil.getSessionFactory().openSession();
        EntityTransaction tx = session.getTransaction();
        tx.begin();
        try {
            ESL eslElement = eslDao.searchByESLCode(esl);
            Item itemElement = eslDao.searchByItemCode(item);
            session.find(ESL.class, eslElement.getItem());
            eslElement.setItem(itemElement);
            session.close();
            TransactionStatus result = ((Transaction) tx).getStatus();
            tx.commit();
            return result.isOneOf(TransactionStatus.COMMITTED) ? "ok" : "error";
        } catch (HibernateException|NoResultException hibernateEx) {
            try {
                tx.rollback();
            } catch (RuntimeException runtimeEx) {
                System.err.printf("Couldn’t Roll Back Transaction", runtimeEx);
            }
            hibernateEx.printStackTrace();
        }finally {
            if (session != null) {
                session.close();
            }
        }
        return "error while commit";
    }

        @Autowired
    BaseSaleTemplate baseSaleTemplate;

    @RequestMapping("/api/getImage")
    public String getImage(@RequestParam("elsCode") String elsCode) throws IOException {
        int width = 152;
        int height = 152;
        Item selectedGood = eslDao.searchByESLCode(elsCode).getItem();
        EslInfoTemplate eslInfoTemplate = new EslInfoTemplate(selectedGood.getItemName(),
                selectedGood.getItemName(),
                String.valueOf(selectedGood.getPrice()),
                String.valueOf(selectedGood.getPromotionPrice()),
                "рублей",
                selectedGood.getItemCode());
        BufferedImage image = baseSaleTemplate.drawEsl(eslInfoTemplate, width, height);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);

        String data = DatatypeConverter.printBase64Binary(baos.toByteArray());
        String imageString = "data:image/png;base64," + data;
        Gson gson = new Gson();
        return gson.toJson(imageString);
    }
}
