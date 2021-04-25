package io.kimmking.kmq.core;

import lombok.SneakyThrows;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 队列
 */
public final class Kmq {

    private String topic;

    private int capacity;

    private AtomicLong start = new AtomicLong(0);

    private AtomicLong end = new AtomicLong(0);

    private String[] queue;

    public Kmq(String topic, int capacity) {
        this.topic = topic;
        this.capacity = capacity;
        this.queue = new String[capacity];
    }

    public boolean send(String message) {
        long end = this.end.getAndIncrement();
        int index = (int) (end % capacity);
        queue[index] = message;
        return true;
    }

    public String poll(int offset) {
        if (offset > end.get()) {
            return null;
        }
        int index = offset % capacity;
        return queue[index];
    }
}
