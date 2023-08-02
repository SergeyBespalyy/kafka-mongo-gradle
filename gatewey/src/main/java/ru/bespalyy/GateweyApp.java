package ru.bespalyy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class GateweyApp {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(GateweyApp.class, args);

    }

}