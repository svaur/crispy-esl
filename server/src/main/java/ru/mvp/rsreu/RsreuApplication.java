package ru.mvp.rsreu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
@ComponentScan("ru.mvp.database")
@ComponentScan("ru.mvp.rsreu")
@SpringBootApplication
public class RsreuApplication {

	public static void main(String[] args) {
		SpringApplication.run(RsreuApplication.class, args);
	}
}
