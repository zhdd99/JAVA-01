package com.example.week802xa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import static org.hamcrest.CoreMatchers.is;

@SpringBootTest
class Week802xaApplicationTests {

    @Autowired
    private XAOrderService orderService;

    @Test
    public void assertInsertFailed() {
        try {
            orderService.insertFailed(10000);
        } catch (final Exception ignore) {
        }
        Assert.isTrue(orderService.selectAll() == 0, "assertInsertFailed fail");
    }

    @Test
    public void assertInsert() {
        try {
            orderService.insert(10000);
        } catch (final Exception ignore) {
        }
        Assert.isTrue(orderService.selectAll() == 10000, "assertInsert fail");
    }

}
