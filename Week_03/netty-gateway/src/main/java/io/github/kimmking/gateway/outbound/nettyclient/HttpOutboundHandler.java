package io.github.kimmking.gateway.outbound.nettyclient;


import io.github.kimmking.gateway.filter.HeaderHttpRequestFilter;
import io.github.kimmking.gateway.filter.HeaderHttpResponseFilter;
import io.github.kimmking.gateway.filter.HttpRequestFilter;
import io.github.kimmking.gateway.filter.HttpResponseFilter;
import io.github.kimmking.gateway.router.HttpEndpointRouter;
import io.github.kimmking.gateway.router.RandomHttpEndpointRouter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

import java.net.URI;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;


public class HttpOutboundHandler {
    
    private HttpClient httpclient;
    private ExecutorService proxyService;
    private List<String> backendUrls;


    HttpResponseFilter responseFilter = new HeaderHttpResponseFilter();
    HttpRequestFilter requestFilter = new HeaderHttpRequestFilter();
    HttpEndpointRouter router = new RandomHttpEndpointRouter();

    public HttpOutboundHandler(List<String> backends) {
        this.backendUrls = backends.stream().map(this::formatUrl).collect(Collectors.toList());
        int cores = Runtime.getRuntime().availableProcessors();
        long keepAliveTime = 1000;
        int queueSize = 2048;
        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();//.DiscardPolicy();
        proxyService = new ThreadPoolExecutor(cores, cores,
                keepAliveTime, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(queueSize),
                new NamedThreadFactory("proxyService"), handler);
        httpclient = new HttpClient();
    }

    private String formatUrl(String backend) {
        return backend.endsWith("/")?backend.substring(0,backend.length()-1):backend;
    }
    
    public void handle(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx) {
        String backendUrl = router.route(this.backendUrls);
        final String url = backendUrl + fullRequest.uri();
        requestFilter.filter(fullRequest, ctx);
        proxyService.submit(()->fetchGet(fullRequest, ctx, url));
    }
    
    private void fetchGet(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, final String url) {
        URI uri = URI.create(url);
        try {
            httpclient.connect(uri.getHost(), uri.getPort(), uri.getPath(), ctx);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
    
    
}
