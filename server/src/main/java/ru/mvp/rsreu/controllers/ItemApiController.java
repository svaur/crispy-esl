package ru.mvp.rsreu.controllers;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mvp.rsreu.db.dao.ItemDao;
import ru.mvp.rsreu.db.entity.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class ItemApiController {

    private ItemDao itemDao;

    @Autowired
    public ItemApiController(ItemDao itemDao) {
        this.itemDao = itemDao;
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
        return new Gson().toJson(tableData);
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
        return new Gson().toJson(tableData);
    }

    private HashMap<String, String> fillItemData(Item e) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("itemCode", e.getItemCode());
        hashMap.put("itemName", e.getItemName());
        hashMap.put("price", String.valueOf(e.getPromotionPrice()));
        hashMap.put("lastUpdate", String.valueOf(e.getLastUpdated()));
        if (e.getEsl()!=null)
            hashMap.put("associate", e.getEsl().getEslCode());
        else
            hashMap.put("associate", "нет");
        hashMap.put("active", "true");
        return hashMap;
    }
}