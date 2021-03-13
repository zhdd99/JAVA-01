package com.example.week802xa;

import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.apache.shardingsphere.transaction.core.TransactionTypeHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Service
public class XAOrderService {
    
    private final JdbcTemplate jdbcTemplate;
    
    @Autowired
    public XAOrderService(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    @ShardingTransactionType(TransactionType.XA)
    public TransactionType insert(final int count) {
        return jdbcTemplate.execute("INSERT INTO platform_order (user_id, order_id) VALUES (?, ?)", (PreparedStatementCallback<TransactionType>) preparedStatement -> {
            doInsert(count, preparedStatement);
            return TransactionTypeHolder.get();
        });
    }

    @Transactional
    @ShardingTransactionType(TransactionType.XA)
    public void insertFailed(final int count) {
        jdbcTemplate.execute("INSERT INTO platform_order (user_id, order_id) VALUES (?, ?)", (PreparedStatementCallback<TransactionType>) preparedStatement -> {
            doInsert(count, preparedStatement);
            throw new SQLException("mock transaction failed");
        });
    }
    
    private void doInsert(final int count, final PreparedStatement preparedStatement) throws SQLException {
        for (int i = 0; i < count; i++) {
            preparedStatement.setObject(1, i);
            preparedStatement.setObject(2, i);
            preparedStatement.executeUpdate();
        }
    }

    int selectAll() {
        return jdbcTemplate.queryForObject("SELECT COUNT(1) AS count FROM platform_order", Integer.class);
    }
}
