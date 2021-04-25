package com.example.demokafka.message;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class Consumer {

    @KafkaListener(topics = "${kafka.topic}")
    public void listen(String foo) {
        System.out.println("Received: " + foo);
    }
}
