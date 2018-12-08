package ru.mvp.gateway.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mvp.gateway.ConsoleTools;


@RestController
public class RestControllers {
    private ConsoleTools consoleTools;

    @Autowired
    public RestControllers(ConsoleTools consoleTools) {
        this.consoleTools = consoleTools;
    }

    @RequestMapping("/api/runCommand")
    public String assignEsl() {
        try{ return consoleTools.runConsoleCommand("dir");}
        catch (Exception e){
            return "non ok";
        }
    }
}
