package ru.mvp.accesspoint.controllers;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mvp.accesspoint.ConsoleTools;
import ru.mvp.accesspoint.entity.ESL;


@RestController
public class RestControllers {
    private ConsoleTools consoleTools;

    @Autowired
    public RestControllers(ConsoleTools consoleTools) {
        this.consoleTools = consoleTools;
    }

    @RequestMapping("/accessPoint/getStatus")
    public String getStatus(@RequestParam(value = "eslcode") String eslcode) {
        try{
            String driverResult = consoleTools.runConsoleCommand("dir");
            System.out.printf("тут будет парсинг ответа от драйвера. пока не проработан формат"+ driverResult);

            ESL esl = new ESL();
            esl.setBatteryLevel("high");
            esl.setConnectivity("connected");
            esl.setEslCode(eslcode);
            esl.setStatus("active");
            Gson g = new Gson();
            return g.toJson(esl);
        }
        catch (Exception e){
            return "non ok";
        }
    }
    @RequestMapping("/accessPoint/setEsl")
    public String setEsl(@RequestParam(value = "eslcode") String eslcode) {
        try{
            String driverResult = consoleTools.runConsoleCommand("dir");
            System.out.printf("тут будет парсинг ответа от драйвера. пока не проработан формат"+ driverResult);

            ESL esl = new ESL();
            esl.setBatteryLevel("high");
            esl.setConnectivity("connected");
            esl.setEslCode(eslcode);
            esl.setStatus("active");
            Gson g = new Gson();
            return g.toJson(esl);
        }
        catch (Exception e){
            return "non ok";
        }
    }
}
