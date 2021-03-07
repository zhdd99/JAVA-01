package com.example.mysqlreadwrite02;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.jta.JtaAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(scanBasePackages = {"com.example.mysqlreadwrite02"}, exclude = DataSourceAutoConfiguration.class)
@MapperScan("com.example.mysqlreadwrite.mapper")
public class Mysqlreadwrite02Application {

    public static void main(String[] args) {
        try (ConfigurableApplicationContext applicationContext = SpringApplication.run(Mysqlreadwrite02Application.class, args)) {
            System.out.println(applicationContext.getBeanDefinitionNames());
        }
    }

}
