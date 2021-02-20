package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 3. (必做)给前面课程提供的 Student/Klass/School 实现自动配置和 Starter。
 */
@SpringBootApplication(scanBasePackages = "com.example.starter.test",exclude= {DataSourceAutoConfiguration.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
