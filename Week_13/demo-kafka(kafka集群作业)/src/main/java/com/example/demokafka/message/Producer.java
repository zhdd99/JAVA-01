package com.example.demokafka.message;

import com.example.demokafka.config.ConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Producer {
    @Autowired
    private ConfigProperties configProperties;

    @Autowired
    private KafkaTemplate<String, String> template;

    public void send(String foo) {
        this.template.send(this.configProperties.getTopic(), foo);
    }
}
