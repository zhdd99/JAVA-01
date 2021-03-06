package com.example.jms.acivemqtest.receiver.topic;


import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class TopicReceiver2 {
    //topic模式的消费者
    @JmsListener(destination="test.topic", containerFactory="topicListener")
    public void readActiveQueue(String message) {
        System.out.println("TopicReceiver2接受到：" + message);
    }
}
