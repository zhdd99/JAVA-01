package com.example.jms.acivemqtest.receiver.quque;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class QueueReceiver1 {

    //queue模式的消费者
    @JmsListener(destination="test.queue", containerFactory="queueListener")
    public void readActiveQueue(String message) {
        System.out.println("QueueReceiver1接受到：" + message);
    }
}
