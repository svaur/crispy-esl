package ru.mvp.accesspoint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class AccessPointApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccessPointApplication.class, args);
    }
}
