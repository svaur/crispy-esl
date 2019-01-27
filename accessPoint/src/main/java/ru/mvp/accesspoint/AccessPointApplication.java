package ru.mvp.accesspoint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@ComponentScan("ru.mvp.database")
@ComponentScan("ru.mvp.accesspoint")
public class AccessPointApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccessPointApplication.class, args);
    }
}
