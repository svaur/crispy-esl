package ru.mvp.rsreu.сontrollers;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mvp.rsreu.db.dao.ESLDao;
import ru.mvp.rsreu.db.dao.ESLService;
import ru.mvp.rsreu.db.entity.ESL;

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
        list.stream().forEach(e ->{
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
