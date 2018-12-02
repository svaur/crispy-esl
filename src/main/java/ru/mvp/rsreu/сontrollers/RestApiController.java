package ru.mvp.rsreu.сontrollers;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mvp.rsreu.db.entity.ESL;
import ru.mvp.rsreu.db.entity.Item;
import ru.mvp.rsreu.db.service.ESLService;
import ru.mvp.rsreu.db.service.IService;
import ru.mvp.rsreu.db.service.ItemService;
import ru.mvp.rsreu.db.service.TaskService;
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

    private final BaseSaleTemplate baseSaleTemplate;
    private final ItemService itemService;
    private final ESLService eslService;
    private final TaskService taskService;

    @Autowired
    public RestApiController(BaseSaleTemplate baseSaleTemplate, ItemService itemService, ESLService eslService, TaskService taskService) {
        this.baseSaleTemplate = baseSaleTemplate;
        this.itemService = itemService;
        this.eslService = eslService;
        this.taskService = taskService;
    }

    @RequestMapping("/api/getEslTableData")
    public String getEslTableData(@RequestParam(value = "size", required = false, defaultValue = "10") String size) {//todo получать мапу? не станет ли избыточным?
        return getElementTableData(Integer.valueOf(size), eslService);
    }

    @RequestMapping("/api/getTaskTableData")
    public String getTaskTableData(@RequestParam(value = "size", required = false, defaultValue = "10") String size) {//todo получать мапу? не станет ли избыточным?
        return getElementTableData(Integer.valueOf(size), taskService);
    }

    @RequestMapping("/api/getItemTableData")
    public String getItemTableData(@RequestParam(value = "size", required = false, defaultValue = "10") String size) {//todo получать мапу? не станет ли избыточным?
        return getElementTableData(Integer.valueOf(size), itemService);
    }

    private <T> String getElementTableData(int showSize, IService<T> service) {
        List<HashMap<String, String>> tableData = new ArrayList<>(showSize);
        Pageable pageable = PageRequest.of(0, showSize);
        Page<T> list = service.findAll(pageable);
        list.forEach(e -> {
            HashMap<String, String> hashMap = service.fillEntityData(e);
            tableData.add(hashMap);
        });
        Gson g = new Gson();
        return g.toJson(tableData);
    }

    @RequestMapping("/api/searchEslData")
    public String searchEslData(@RequestParam(value = "size", required = false, defaultValue = "10") String size,
                                @RequestParam(value = "searchValue") String searchValue) {
        return searchElementData(Integer.valueOf(size), searchValue, eslService);
    }

    @RequestMapping("/api/searchItemData")
    public String searchItemData(@RequestParam(value = "size", required = false, defaultValue = "10") String size,
                                 @RequestParam(value = "searchValue") String searchValue) {
        return searchElementData(Integer.valueOf(size), searchValue, itemService);
    }

    @RequestMapping("/api/searchTaskData")
    public String searchTaskData(@RequestParam(value = "size", required = false, defaultValue = "10") String size,
                                 @RequestParam(value = "searchValue") String searchValue) {
        return searchElementData(Integer.valueOf(size), searchValue, taskService);
    }

    private <T> String searchElementData(int showSize, String searchValue, IService<T> service) {
        List<HashMap<String, String>> tableData = new ArrayList<>(showSize);
        Pageable pageable = PageRequest.of(0, showSize);
        List<T> list = service.searchByValue(searchValue, pageable);
        list.forEach(e -> {
            HashMap<String, String> hashMap = service.fillEntityData(e);
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
        ESL eslElement = eslService.searchByESLCode(esl);
        Item itemElement = itemService.searchByItemCode(item);
        if ("add".equalsIgnoreCase(type)) {
            eslElement.setItem(itemElement);
            eslService.saveEsl(eslElement);
        } else {
            eslElement.setItem(null);
            eslService.saveEsl(eslElement);
        }
        result = true;
        return result ? "ok" : "error";
    }

    @RequestMapping("/api/getImage")
    public String getImage(@RequestParam("eslCode") String eslCode) throws IOException {
        int width = 152;
        int height = 152;
        Item selectedGood = eslService.searchByESLCode(eslCode).getItem();
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
