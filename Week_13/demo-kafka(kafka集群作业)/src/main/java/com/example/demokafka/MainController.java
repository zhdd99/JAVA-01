package com.example.demokafka;

import com.example.demokafka.message.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.atomic.AtomicLong;

@Controller
public class MainController {

    public static final AtomicLong atomicLong = new AtomicLong(0);

    @Autowired
    private Producer producer;

    @RequestMapping("/test")
    @ResponseBody
    String mongo() {
        producer.send("message : " + atomicLong.getAndIncrement());
        return "OK";
    }
}
