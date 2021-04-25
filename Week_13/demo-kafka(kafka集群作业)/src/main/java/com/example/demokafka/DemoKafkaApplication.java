package com.example.demokafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.io.IOException;

@SpringBootApplication(scanBasePackages = {"com.example.demokafka"})
@ConfigurationPropertiesScan
public class DemoKafkaApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(DemoKafkaApplication.class, args);
    }

}
