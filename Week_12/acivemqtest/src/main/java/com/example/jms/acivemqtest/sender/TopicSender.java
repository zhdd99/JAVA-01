package com.example.jms.acivemqtest.sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.Topic;

@Component
public class TopicSender {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Topic topic;

    public void send(String i) {
        jmsTemplate.send(topic, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(i);
            }
        });
    }
}
