package ru.mvp.rsreu.сontrollers;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mvp.rsreu.db.dao.ESLDao;
import ru.mvp.rsreu.db.dao.ItemDao;
import ru.mvp.rsreu.db.dao.TaskDao;
import ru.mvp.rsreu.db.entity.ESL;
import ru.mvp.rsreu.db.entity.Item;
import ru.mvp.rsreu.db.entity.Task;
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
public class RestApiController {

    private static final String EMPTY_STRING = "";
    private ESLDao eslDao;
    private ItemDao itemDao;
    private TaskDao taskDao;
    private BaseSaleTemplate baseSaleTemplate;

    @Autowired
    public RestApiController(ESLDao eslDao, ItemDao itemDao, TaskDao taskDao, BaseSaleTemplate baseSaleTemplate) {
        this.eslDao = eslDao;
        this.itemDao = itemDao;
        this.taskDao = taskDao;
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
    @RequestMapping("/api/getTaskTableData")
    public String getTaskTableData(@RequestParam(value = "size", required = false, defaultValue = "10") String size) {//todo получать мапу? не станет ли избыточным?
        int showSize = Integer.valueOf(size);
        List<HashMap<String, String>> tableData = new ArrayList<>(showSize);
        List<Task> list = taskDao.getAll(showSize);
        list.forEach(e -> {
            HashMap<String, String> hashMap = fillTaskData(e);
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
    public String searchEslData(@RequestParam(value = "size", required = false, defaultValue = "10") String size,    //todo для показа конечно и так сойдет, но уж дюже похоже на предыдущий метод, фабрика ESLDao
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
    public String searchItemData(@RequestParam(value = "size", required = false, defaultValue = "10") String size,    //todo для показа конечно и так сойдет, но уж дюже похоже на предыдущий метод, фабрика ESLDao
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

    @RequestMapping("/api/searchTaskData")
    public String searchTaskData(@RequestParam(value = "size", required = false, defaultValue = "10") String size,    //todo для показа конечно и так сойдет, но уж дюже похоже на предыдущий метод, фабрика ESLDao
                               @RequestParam(value = "searchValue") String searchValue) {                           //todo получать мапу? не станет ли избыточным?
        int showSize = Integer.valueOf(size);
        List<HashMap<String, String>> tableData = new ArrayList<>(showSize);
        List<Task> list = taskDao.searchByValue(searchValue, showSize);
        list.forEach(e -> {
            HashMap<String, String> hashMap = fillTaskData(e);
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
        boolean result;
        ESL eslElement = eslDao.searchByESLCode(esl);
        Item itemElement = itemDao.searchByItemCode(item);
        if("add".equalsIgnoreCase(type)){
            result = eslDao.assignItem(eslElement, itemElement);
        } else {
            result = eslDao.unAssignItem(eslElement);
        }
        return result ? "ok" : "error";
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
        ImageIO.getWriterFormatNames();
        if (ImageIO.write(image, "BMP", baos)){
            String data = DatatypeConverter.printBase64Binary(baos.toByteArray());
            String imageString = "data:image/bmp;base64," + data;
            Gson gson = new Gson();
            return gson.toJson(imageString);
        }
        return "error";//todo сделать нормальный возврат ошибок на фронт
    }
    private HashMap<String, String> fillEslData(ESL e){
        HashMap<String, String> hashMap = new HashMap<>();
        Item item = e.getItem();
        hashMap.put("eslCode", e.getEslCode());
        hashMap.put("eslType", e.getEslType());
        hashMap.put("itemCode", item == null ? EMPTY_STRING : item.getItemCode());
        hashMap.put("itemName", item == null ? EMPTY_STRING : item.getItemName());
        hashMap.put("price", String.valueOf(item == null ? EMPTY_STRING : item.getPromotionPrice()));
        hashMap.put("lastUpdate", String.valueOf(e.getLastUpdate()));
        hashMap.put("connectivity", e.getConnectivity());
        hashMap.put("batteryLevel", String.valueOf(e.getBatteryLevel()));
        hashMap.put("status", e.getStatus());
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
    private HashMap<String, String> fillTaskData(Task e){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("taskName", e.getTaskName());
        hashMap.put("taskType", e.getTaskType());
        hashMap.put("frequency", String.valueOf(e.getFrequency()));
        hashMap.put("lastUpdate", String.valueOf(e.getLastUpdated()));
        hashMap.put("nextShedule", String.valueOf(e.getNextSheduled()));
        return hashMap;
    }
}
