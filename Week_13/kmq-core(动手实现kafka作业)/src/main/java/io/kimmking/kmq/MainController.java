package io.kimmking.kmq;

import io.kimmking.kmq.core.Kmq;
import io.kimmking.kmq.core.KmqBroker;
import io.kimmking.kmq.core.KmqConsumer;
import io.kimmking.kmq.core.KmqProducer;
import lombok.NonNull;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

@Controller
public class MainController {

    private AtomicLong incrementKey = new AtomicLong();

    private KmqBroker broker = new KmqBroker();

    private static final Map<String, Map<Long, Integer>> offsetCache = new ConcurrentHashMap<>();

    private Map<Long, String> topicCache = new ConcurrentHashMap<>();



    @RequestMapping(path = "/poll")
    @ResponseBody
    public String poll(long consumerId) {
        String topic = topicCache.get(consumerId);
        Kmq kmq = broker.findKmq(topic);
        return kmq.poll(offsetCache.get(topic).get(consumerId));
    }

    @RequestMapping(path = "/commitoffset")
    @ResponseBody
    public Boolean commitoffset(long consumerId, int offset) {
        try {
            String topic = topicCache.get(consumerId);
            Map<Long, Integer> offsetMap = offsetCache.get(topic);
            if(MapUtils.isNotEmpty(offsetMap)){
                if (offset > offsetMap.get(consumerId)) {
                    offsetMap.put(consumerId, offset);
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @RequestMapping(path = "/send")
    @ResponseBody
    public Boolean send(String topic, String message) {
        Kmq kmq = broker.findKmq(topic);
        if(Objects.isNull(kmq)){
            broker.createTopic(topic);
        }
        return kmq.send(message);
    }


    @RequestMapping(path = "/createConsumer")
    @ResponseBody
    public Long createConsumer(@NonNull String topic, Integer offset){
        if (Objects.isNull(offset)) {
            offset = 0;
        }
        broker.createTopic(topic);
        long consumerId = incrementKey.incrementAndGet();
        Map<Long, Integer> consumerOffset = offsetCache.computeIfAbsent(topic, key -> new ConcurrentHashMap<>());
        consumerOffset.put(consumerId, offset);
        topicCache.put(consumerId, topic);
        return consumerId;
    }

}
