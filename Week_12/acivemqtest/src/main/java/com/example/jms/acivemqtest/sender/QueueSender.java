package com.example.jms.acivemqtest.sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;

@Component
public class QueueSender {

    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    private Queue queue;

    public void send(String i) {
        jmsTemplate.send(queue, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(i);
            }
        });
    }

//    @PostConstruct
//    public void init(){
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        for(int i = 0; i < 10; i ++){
//            send("quque item " + i);
//        }
//    }
}
