package io.kimmking.kmq.core;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.IOException;

/**
 * 生产者
 */
public class KmqProducer {

    private String url = "http://localhost:8080/send";

    public boolean send(String topic, String message) throws Exception {
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        formBodyBuilder.add("topic", topic);
        formBodyBuilder.add("message", message);
        FormBody formBody = formBodyBuilder.build();
        final Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        client.newCall(request).execute().body().string();
        return true;
    }

    public static void main(String[] args) throws Exception {
        KmqProducer kmqProducer = new KmqProducer();
        for(int i = 0; i < 100; i ++){
            kmqProducer.send("test", "message" + i);
        }
    }
}
