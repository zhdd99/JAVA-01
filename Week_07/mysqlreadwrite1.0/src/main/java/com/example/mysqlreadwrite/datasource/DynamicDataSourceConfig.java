package com.example.mysqlreadwrite.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 配置多数据源
 */
@Configuration
public class DynamicDataSourceConfig {

    @Bean
    public DataSource firstDataSource(){
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.setJdbcUrl("jdbc:mysql://localhost:3306/java_study?rewriteBatchedStatements=true");
        config.setUsername("root");
        config.setPassword("123456");
        config.setConnectionTestQuery("select 1");
        HikariDataSource dataSource = new HikariDataSource(config);
        dataSource.setMaximumPoolSize(100);
        dataSource.setMinimumIdle(10);
        dataSource.setMaxLifetime(15 * 60 * 1000);
        return dataSource;
    }

    @Bean
    public DataSource secondDataSource(){
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.setJdbcUrl("jdbc:mysql://localhost:3307/java_study?rewriteBatchedStatements=true");
        config.setUsername("root");
        config.setPassword("123456");
        config.setConnectionTestQuery("select 1");
        HikariDataSource dataSource = new HikariDataSource(config);
        dataSource.setMaximumPoolSize(100);
        dataSource.setMinimumIdle(10);
        dataSource.setMaxLifetime(15 * 60 * 1000);
        return dataSource;
    }

    @Bean
    @Primary
    public DynamicDataSource dataSource(@Qualifier("firstDataSource") DataSource firstDataSource,@Qualifier("secondDataSource") DataSource secondDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>(5);
        targetDataSources.put(DataSourceNames.FIRST, firstDataSource);
        targetDataSources.put(DataSourceNames.SECOND, secondDataSource);
        return new DynamicDataSource(firstDataSource, targetDataSources);
    }

}
