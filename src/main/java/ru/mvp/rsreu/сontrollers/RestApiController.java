package ru.mvp.rsreu.сontrollers;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class RestApiController {
    @RequestMapping("/api/getTableData")
    public String getTableData() {
        List<HashMap<String, String>> test = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("key1","Random value sfdsdfsdfsdf"+i);
            hashMap.put("key2","Random value sfdsdfsdfsdf"+i);
            hashMap.put("key3","Random value sfdsdfsdfsdf"+i);
            hashMap.put("key4","Random value sfdsdfsdfsdf"+i);
            hashMap.put("key5","Random value sfdsdfsdfsdf"+i);
            hashMap.put("key6","Random value sfdsdfsdfsdf"+i);
            hashMap.put("key7","Random value sfdsdfsdfsdf"+i);
            hashMap.put("key8","Random value sfdsdfsdfsdf"+i);
            hashMap.put("key9","Random value sfdsdfsdfsdf"+i);
            hashMap.put("key0","Random value sfdsdfsdfsdf"+i);
            test.add(hashMap);
        }
        Gson g = new Gson();
        return g.toJson(test);
    }
}
