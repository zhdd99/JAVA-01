package com.example.week801;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

@SpringBootTest(classes = Week801Application.class)
class Week801ApplicationTests {

    @Autowired
    DataSource dataSource;

    @Test
    public void insertSql() throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("insert into platform_order (gmt_create, gmt_modified, order_id, user_id, amount, status, gmt_pay) values (?, ?, ?, ?, ?, ?, ?)");
        {
            Date date = new Date(System.currentTimeMillis());
            connection.setAutoCommit(false);
            for (int i = 1; i <= 10000; i++) {
                preparedStatement.setDate(1, date);
                preparedStatement.setDate(2, date);
                preparedStatement.setLong(3, i);
                preparedStatement.setLong(4, i);
                preparedStatement.setLong(5, 10000L);
                preparedStatement.setInt(6, 0);
                preparedStatement.setDate(7, date);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            connection.commit();
            connection.close();
        }
    }
}
