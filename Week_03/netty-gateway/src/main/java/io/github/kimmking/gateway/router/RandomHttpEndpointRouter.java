package io.github.kimmking.gateway.router;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomHttpEndpointRouter implements HttpEndpointRouter {
    @Override
    public String route(List<String> urls) {
        int size = urls.size();
        return urls.get(ThreadLocalRandom.current().nextInt(size));
    }
}
