package ru.mvp.rsreu.controllers;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mvp.database.entities.AccessPointsInfo;
import ru.mvp.database.repositories.AccessPointsInfoRepository;
import ru.mvp.database.repositories.EntityLogRepository;
import ru.mvp.database.repositories.EslsRepository;
import ru.mvp.database.repositories.ItemsRepository;
import ru.mvp.rsreu.tools.RestClient;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.*;

@RestController
public class WelcomeController {
    private EslsRepository eslsRepository;
    private ItemsRepository itemsRepository;
    private AccessPointsInfoRepository accessPointsInfoRepository;
    private EntityLogRepository entityLogRepository;
    private RestClient restClient;

    @Autowired
    public WelcomeController(EslsRepository eslsRepository, ItemsRepository itemsRepository, AccessPointsInfoRepository accessPointsInfoRepository, EntityLogRepository entityLogRepository, RestClient restClient) {
        this.eslsRepository = eslsRepository;
        this.itemsRepository = itemsRepository;
        this.accessPointsInfoRepository = accessPointsInfoRepository;
        this.entityLogRepository = entityLogRepository;
        this.restClient = restClient;
    }

    @RequestMapping("/api/getWelcomeData")
    public String getWelcomeData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("esl", String.valueOf(eslsRepository.count()));
        map.put("item", String.valueOf(itemsRepository.count()));
        map.put("assignUpdated", "?");
        map.put("itemUpdated", "?");
        String allAP = String.valueOf(accessPointsInfoRepository.count());
        int countOnlineAP = 0;
        List<AccessPointsInfo> accessPointsInfoList = accessPointsInfoRepository.findAll();
        for (AccessPointsInfo element:accessPointsInfoList) {
            String server = "http://" + element.getIp() + ":" + element.getPort();
            Type type = new TypeToken<Map<String, String>>(){}.getType();
            String json = restClient.get(server, "/api/getServerData");
            try {
                LinkedTreeMap<String, String> serverInfo = new Gson().fromJson(json, type);
                if (serverInfo.get("ram") != null) {
                    countOnlineAP++;
                }
            }catch (Exception e){
                //todo тут какое т говнище. Ща ночь и мне лень думать.
            }
        }
        map.put("accessPoint", countOnlineAP + " из " + allAP);
        map.put("errorsEsl", String.valueOf(entityLogRepository.countAllByTypeAndNameAndTime("error", "task", new Timestamp(new Date().getTime()))));
        map.put("nextUpdate", "?");
        return new Gson().toJson(map);
    }
}
