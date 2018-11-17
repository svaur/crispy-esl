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
import ru.mvp.rsreu.db.dao.ItemDao;
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
    private ItemDao itemDao;
    private BaseSaleTemplate baseSaleTemplate;

    @Autowired
    public RestApiController(ESLDao eslDao, ItemDao itemDao, BaseSaleTemplate baseSaleTemplate) {
        this.eslDao = eslDao;
        this.itemDao = itemDao;
        this.baseSaleTemplate = baseSaleTemplate;
    }

    @RequestMapping("/api/getEslTableData")
    public String getEslTableData(@RequestParam(value = "size", required = false, defaultValue = "10") String size) {//todo получать мапу? не станет ли избыточным?
        int showSize = Integer.valueOf(size);
        List<HashMap<String, String>> tableData = new ArrayList<>(showSize);
        List<ESL> list = eslDao.getAll(showSize);
        list.forEach(e -> {
            HashMap<String, String> hashMap = fillEslData(e);
            tableData.add(hashMap);
        });
        Gson g = new Gson();
        return g.toJson(tableData);
    }
    @RequestMapping("/api/getItemTableData")
    public String getItemTableData(@RequestParam(value = "size", required = false, defaultValue = "10") String size) {//todo получать мапу? не станет ли избыточным?
        int showSize = Integer.valueOf(size);
        List<HashMap<String, String>> tableData = new ArrayList<>(showSize);
        List<Item> list = itemDao.getAll(showSize);
        list.forEach(e -> {
            HashMap<String, String> hashMap = fillItemData(e);
            tableData.add(hashMap);
        });
        Gson g = new Gson();
        return g.toJson(tableData);
    }

    @RequestMapping("/api/searchEslData")
    public String getEslTableData(@RequestParam(value = "size", required = false, defaultValue = "10") String size,    //todo для показа конечно и так сойдет, но уж дюже похоже на предыдущий метод, фабрика ESLDao
                               @RequestParam(value = "searchValue") String searchValue) {                           //todo получать мапу? не станет ли избыточным?
        int showSize = Integer.valueOf(size);
        List<HashMap<String, String>> tableData = new ArrayList<>(showSize);
        List<ESL> list = eslDao.searchByValue(searchValue, showSize);
        list.forEach(e -> {
            HashMap<String, String> hashMap = fillEslData(e);
            tableData.add(hashMap);
        });
        Gson g = new Gson();
        return g.toJson(tableData);
    }

    @RequestMapping("/api/searchItemData")
    public String getItemTableData(@RequestParam(value = "size", required = false, defaultValue = "10") String size,    //todo для показа конечно и так сойдет, но уж дюже похоже на предыдущий метод, фабрика ESLDao
                               @RequestParam(value = "searchValue") String searchValue) {                           //todo получать мапу? не станет ли избыточным?
        int showSize = Integer.valueOf(size);
        List<HashMap<String, String>> tableData = new ArrayList<>(showSize);
        List<Item> list = itemDao.searchByValue(searchValue, showSize);
        list.forEach(e -> {
            HashMap<String, String> hashMap = fillItemData(e);
            tableData.add(hashMap);
        });
        Gson g = new Gson();
        return g.toJson(tableData);
    }

    @RequestMapping("/api/assignEsl")
    public String assignEsl(@RequestParam("esl") String esl,
                            @RequestParam("template") String template,
                            @RequestParam("item") String item,
                            @RequestParam("type") String type) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        EntityTransaction tx = session.getTransaction();
        switch (type){
            case "delete":
                return "TODO не сделана схема бд. Невозможно отвязать датчик";
            case "add":
                try {
                    tx.begin();
                    ESL eslElement = eslDao.searchByESLCode(esl);
                    Item itemElement = itemDao.searchByItemCode(item);
                    session.find(ESL.class, eslElement.getItem());
                    eslElement.setItem(itemElement);
                    session.close();
                    //TODO НЕВОЗМОЖНО ПРИВЯЗАТЬ ДАТЧИК. Датчик не является отдельной сущностью
                    TransactionStatus result = ((Transaction) tx).getStatus();
                    tx.commit();
                    return result.isOneOf(TransactionStatus.COMMITTED) ? "ok" : "error";
                }finally {
                    if (session != null) {
                        session.close();
                    }
                }
            default:
                return "unknown type";

        }
    }

    @RequestMapping("/api/getImage")
    public String getImage(@RequestParam("eslCode") String eslCode) throws IOException {
        int width = 152;
        int height = 152;
        Item selectedGood = eslDao.searchByESLCode(eslCode).getItem();
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
    private HashMap<String, String> fillEslData(ESL e){
        HashMap<String, String> hashMap = new HashMap<>();
        Item item = e.getItem();
        hashMap.put("eslCode", e.getElsCode());
        hashMap.put("eslType", e.getElsType());
        hashMap.put("itemCode", item.getItemCode());
        hashMap.put("itemName", item.getItemName());
        hashMap.put("price", String.valueOf(item.getPromotionPrice()));
        hashMap.put("lastUpdate", String.valueOf(e.getLastUpdate()));
        hashMap.put("connectivity", String.valueOf(e.isConnectivity()));
        hashMap.put("batteryLevel", String.valueOf(e.getBatteryLevel()));
        hashMap.put("status", String.valueOf(e.isStatus())); //todo поменять тип
        return hashMap;
    }
    private HashMap<String, String> fillItemData(Item e){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("itemCode", e.getItemCode());
        hashMap.put("itemName", e.getItemName());
        hashMap.put("price", String.valueOf(e.getPromotionPrice()));
        hashMap.put("lastUpdate", String.valueOf(e.getLastUpdated()));
        hashMap.put("active", "true");
        return hashMap;
    }
}
