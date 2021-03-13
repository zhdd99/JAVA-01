package com.example.week801;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.week801.config")
public class Week801Application {

    public static void main(String[] args) {
        SpringApplication.run(Week801Application.class, args);
    }

}
