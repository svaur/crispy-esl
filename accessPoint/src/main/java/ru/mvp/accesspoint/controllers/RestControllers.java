package ru.mvp.accesspoint.controllers;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mvp.accesspoint.ConsoleTools;

import java.io.IOException;
import java.util.HashMap;


@RestController
public class RestControllers {
    private ConsoleTools consoleTools;

    @Autowired
    public RestControllers(ConsoleTools consoleTools) {
        this.consoleTools = consoleTools;
    }

    @RequestMapping("/api/getServerData")
    public String getServerData() throws IOException {
        HashMap<String, String> map = new HashMap<>();
        String ramInfo = consoleTools.runConsoleCommand("cat /proc/meminfo | grep Mem");
        String hddInfo = "Использовано<br>";
        hddInfo += "/ : " + consoleTools.runConsoleCommand("df / | awk '{ print $5 }' | tail -1") + "<br>";
        hddInfo += "/dev : " + consoleTools.runConsoleCommand("df /dev | awk '{ print $5 }' | tail -1") + "<br>";
        hddInfo += "/tmp : " + consoleTools.runConsoleCommand("df /tmp | awk '{ print $5 }' | tail -1") + "<br>";
        String cpuInfo = consoleTools.runConsoleCommand("vmstat -s | grep cpu");
        map.put("ram", ramInfo);
        map.put("hdd", hddInfo);
        map.put("cpu", cpuInfo);
        return new Gson().toJson(map);
    }

    @RequestMapping("/api/updateEsl")
    public String updateEsl(@RequestParam("esl") String esl){
        consoleTools.getByteImage(esl);
        return "ok";
    }
    @RequestMapping("/api/updateEslGroup")
    public String updateEslGroup(@RequestParam("taskId") String taskId){
        consoleTools.updateTask(taskId);
        return "ok";
    }
    @RequestMapping("/api/sendFunPic")
    public String sendFunPic() throws Exception{
        consoleTools.sendFinePic();
        return "ok";
    }
}
