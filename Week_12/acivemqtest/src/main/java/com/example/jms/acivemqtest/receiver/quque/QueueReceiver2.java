package com.example.jms.acivemqtest.receiver.quque;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class QueueReceiver2 {

    @JmsListener(destination="test.queue", containerFactory="queueListener")
    public void readActiveQueue(String message) {
        System.out.println("QueueReceiver2接受到：" + message);
    }
}
