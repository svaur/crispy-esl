package ru.mvp.rsreu.сontrollers;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController extends WebMvcAutoConfiguration {
    @RequestMapping("/")
    public String index() {
        return "index";
    }
}
