package io.kimmking.kmq.core;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 消费者
 * @param
 */
public class KmqConsumer {

    private static final String url = "http://localhost:8080/";

    private AtomicInteger offset = new AtomicInteger(0);

    private Long consumerId;

    public void commitoffset() throws Exception{
        String currentUrl = url + "commitoffset";
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        formBodyBuilder.add("consumerId", String.valueOf(consumerId));
        formBodyBuilder.add("offset", String.valueOf(offset.get()));
        FormBody formBody = formBodyBuilder.build();
        final Request request = new Request.Builder()
                .url(currentUrl)
                .post(formBody)
                .build();
        client.newCall(request).execute().body().string();
    }

    public String poll() throws Exception{
        String currentUrl = url + "poll";
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        formBodyBuilder.add("consumerId", String.valueOf(consumerId));
        FormBody formBody = formBodyBuilder.build();
        final Request request = new Request.Builder()
                .url(currentUrl)
                .post(formBody)
                .build();
        String result = client.newCall(request).execute().body().string();
        if(StringUtils.hasText(result)){
            offset.incrementAndGet();
        }
        return result;
    }


    public KmqConsumer() throws IOException {
        String currentUrl = url + "createConsumer";
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        formBodyBuilder.add("topic", "test");
        FormBody formBody = formBodyBuilder.build();
        final Request request = new Request.Builder()
                .url(currentUrl)
                .post(formBody)
                .build();
        String result = client.newCall(request).execute().body().string();
        consumerId = Long.valueOf(result);
    }

    public static void main(String[] args) throws Exception {
        KmqConsumer consumer = new KmqConsumer();
        while (true){
            String message = consumer.poll();
            if(StringUtils.hasText(message)){
                System.out.println(message);
            }
            consumer.commitoffset();
            Thread.sleep(1000);
        }
    }

}
