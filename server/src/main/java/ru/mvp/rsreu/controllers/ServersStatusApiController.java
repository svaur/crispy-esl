package ru.mvp.rsreu.controllers;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mvp.database.entities.AccessPointsInfo;
import ru.mvp.database.repositories.AccessPointsInfoRepository;
import ru.mvp.rsreu.tools.ConsoleTools;
import ru.mvp.rsreu.tools.RestClient;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ServersStatusApiController {

    private AccessPointsInfoRepository accessPointsInfoRepository;
    private RestClient restClient;
    private ConsoleTools consoleTools;

    @Autowired
    public ServersStatusApiController(AccessPointsInfoRepository accessPointsInfoRepository, RestClient restClient, ConsoleTools consoleTools) {
        this.accessPointsInfoRepository = accessPointsInfoRepository;
        this.restClient = restClient;
        this.consoleTools = consoleTools;
    }

    @RequestMapping("/api/getServersStatusTableData")
    public String getItemTableData(@RequestParam(value = "size") Integer size,
                                   @RequestParam(value = "pageNum") Integer pageNum,
                                   @RequestParam(value = "searchValue") String searchValue) {
        Page<AccessPointsInfo> output;
        if (searchValue.isEmpty())
            output = accessPointsInfoRepository.findAll(PageRequest.of(pageNum, size, Sort.Direction.ASC, "ip"));
        else
            output = accessPointsInfoRepository.findByFilter(PageRequest.of(pageNum, size, Sort.Direction.ASC, "ip"), searchValue);

        return new Gson().toJson(fillItemData(output));
    }

    private List<HashMap<String, String>> fillItemData(Page<AccessPointsInfo> e) {
        List<HashMap<String, String>> outList= new ArrayList<>();
        e.forEach(element->{
            String server = "http://" + element.getIp() + ":" + element.getPort();
            Type type = new TypeToken<Map<String, String>>(){}.getType();
            HashMap<String, String> map = new HashMap<>();
            try {
                String json = restClient.get(server, "/api/getServerData");
                LinkedTreeMap<String, String> serverInfo = new Gson().fromJson(json, type);
                map.put("ip", element.getIp());
                map.put("port", element.getPort());
                map.put("status", "онлайн");
                map.put("app", "онлайн");
                map.put("ram", serverInfo.get("ram"));
                map.put("hdd", serverInfo.get("hdd"));
                map.put("cpu", serverInfo.get("cpu"));
            }catch (Exception e1){
                map.put("ip", element.getIp());
                map.put("port", element.getPort());
                map.put("status", "недоступно");
                map.put("app", e1.getLocalizedMessage());
                map.put("ram", "");
                map.put("hdd", "");
                map.put("cpu", "");
            }
            outList.add(map);});
        return outList;
    }
    @RequestMapping("/api/getLocalUtilizationData")
    public String getItemTableData() throws IOException {
        HashMap<String, String> map = new HashMap<>();
        try {
            String ramInfo = consoleTools.runConsoleCommand("cat /proc/meminfo | grep Mem");
            String hddInfo = "Использовано<br>";
            hddInfo += "/ : " + consoleTools.runConsoleCommand("df / | awk '{ print $5 }' | tail -1") + "<br>";
            hddInfo += "/dev : " + consoleTools.runConsoleCommand("df /dev | awk '{ print $5 }' | tail -1") + "<br>";
            hddInfo += "/tmp : " + consoleTools.runConsoleCommand("df /tmp | awk '{ print $5 }' | tail -1") + "<br>";
            String cpuInfo = consoleTools.runConsoleCommand("vmstat -s | grep cpu");
            map.put("ip", "сервер");
            map.put("port", "");
            map.put("status", "онлайн");
            map.put("app", "онлайн");
            map.put("ram", ramInfo);
            map.put("hdd", hddInfo);
            map.put("cpu", cpuInfo);
        }catch (Exception e){
            map.put("ip", "сервер");
            map.put("port", "");
            map.put("status", "недоступно");
            map.put("app", e.getLocalizedMessage());
            map.put("ram", "");
            map.put("hdd", "");
            map.put("cpu", "");
        }
        return new Gson().toJson(map);
    }
}