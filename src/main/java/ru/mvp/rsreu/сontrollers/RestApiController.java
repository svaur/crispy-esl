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
            hashMap.put("key0","какая то строка номер "+i);
            hashMap.put("key1","какая то строка номер "+i);
            hashMap.put("key2","какая то строка номер "+i);
            hashMap.put("key3","какая то строка номер "+i);
            hashMap.put("key4","какая то строка номер "+i);
            hashMap.put("key5","какая то строка номер "+i);
            hashMap.put("key6","какая то строка номер "+i);
            hashMap.put("key7","какая то строка номер "+i);
            hashMap.put("key8","какая то строка номер "+i);
            hashMap.put("key9","какая то строка номер "+i);
            test.add(hashMap);
        }
        Gson g = new Gson();
        return g.toJson(test);
    }
    @RequestMapping("/api/getAnotherTableData")
    public String getAnotherTableData() {
        List<HashMap<String, String>> test = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("key0", i+"тест тест тест тест "+i);
            hashMap.put("key1", i+"тест тест тест тест "+i);
            hashMap.put("key2", i+"тест тест тест тест "+i);
            hashMap.put("key3", i+"тест тест тест тест "+i);
            hashMap.put("key4", i+"тест тест тест тест "+i);
            hashMap.put("key5", i+"тест тест тест тест "+i);
            hashMap.put("key6", i+"тест тест тест тест "+i);
            hashMap.put("key7", i+"тест тест тест тест "+i);
            hashMap.put("key8", i+"тест тест тест тест "+i);
            hashMap.put("key9", i+"тест тест тест тест "+i);
            test.add(hashMap);
        }
        Gson g = new Gson();
        return g.toJson(test);
    }
    @RequestMapping("/api/getRandStr")
    public String getRandStr() {
        Gson g = new Gson();
        return g.toJson("Не удается получить доступ к сайту\n" +
                "Превышено время ожидания ответа от сайта material-ui.com.\n" +
                "Попробуйте сделать следующее:\n" +
                "\n" +
                "Проверьте подключение к Интернету.\n" +
                "Проверьте настройки прокси-сервера и брандмауэра.\n" +
                "Выполните диагностику сети в Windows\n" +
                "ERR_CONNECTION_TIMED_OUT");
    }
}
