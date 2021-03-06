package org.zhdd99.homework07;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class BeanConfigration {

    @Bean(name = "hikariDataSource", destroyMethod = "close")
    public DataSource hikariDataSource() {
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

    //@Bean
    //public JdbcTemplate jdbcTemplate(@Qualifier("hikariDataSource") DataSource hikariDataSource){
    //    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    //    jdbcTemplate.setDataSource(hikariDataSource);
    //    return jdbcTemplate;
    //}
}
