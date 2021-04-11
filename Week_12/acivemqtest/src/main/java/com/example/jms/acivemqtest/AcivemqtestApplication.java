package com.example.jms.acivemqtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication(scanBasePackages = "com.example.jms.acivemqtest")
@EnableJms
public class AcivemqtestApplication {

    public static void main(String[] args) {
        SpringApplication.run(AcivemqtestApplication.class, args);
    }

}
