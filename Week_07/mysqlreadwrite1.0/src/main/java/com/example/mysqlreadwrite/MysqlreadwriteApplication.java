package com.example.mysqlreadwrite;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(scanBasePackages = {"com.example.mysqlreadwrite"}, exclude={DataSourceAutoConfiguration.class})
@MapperScan("com.example.mysqlreadwrite.mapper")
public class MysqlreadwriteApplication {

    public static void main(String[] args) {
        SpringApplication.run(MysqlreadwriteApplication.class, args);
    }
}
