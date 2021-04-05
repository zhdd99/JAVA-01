package io.kimmking.rpcfx.client;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;

import io.kimmking.rpcfx.api.Filter;
import io.kimmking.rpcfx.api.LoadBalancer;
import io.kimmking.rpcfx.api.Router;
import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResponse;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public final class RpcfxAop {

    static {
        ParserConfig.getGlobalInstance().addAccept("io.kimmking");
    }

    /**
     * 创建代理对象
     *
     * @return
     */
    public static <T> T getInstance(final Class<T> serviceClass, final String host, int port, Filter... filters) throws Exception {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(serviceClass);
        //回调方法
        enhancer.setCallback(new RpcfxInvocationCglibHandler(serviceClass, host, port, filters));
        //创建代理
        return (T)enhancer.create();
    }


    public static class RpcfxInvocationCglibHandler implements MethodInterceptor {

        public static final MediaType JSONTYPE = MediaType.get("application/json; charset=utf-8");

        private final Class<?> serviceClass;
        private final String host;
        private final int port;
        private final Filter[] filters;
        private NettyHttpClient nettyHttpClient;


        public <T> RpcfxInvocationCglibHandler(Class<T> serviceClass, String host, int port, Filter[] filters) throws Exception {
            this.serviceClass = serviceClass;
            this.host = host;
            this.port = port;
            this.filters = filters;
            this.nettyHttpClient = new NettyHttpClient();
            nettyHttpClient.connect(host, port);
        }

        //回调方法
        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
            // 加filter地方之二
            // mock == true, new Student("hubao");

            RpcfxRequest request = new RpcfxRequest();
            request.setServiceClass(this.serviceClass);
            request.setMethod(method.getName());
            request.setParams(args);

            if (null!=filters) {
                for (Filter filter : filters) {
                    if (!filter.filter(request)) {
                        return null;
                    }
                }
            }

            RpcfxResponse response = post(request);

            // 加filter地方之三
            // Student.setTeacher("cuijing");

            // 这里判断response.status，处理异常
            // 考虑封装一个全局的RpcfxException

            return JSON.parse(response.getResult().toString());
        }


        private RpcfxResponse post(RpcfxRequest req) throws Exception {
            String reqJson = JSON.toJSONString(req);
            System.out.println("req json: "+reqJson);

            // 1.可以复用client
            // 2.尝试使用httpclient或者netty client
            String respJson = nettyHttpClient.post(reqJson, "/");
            System.out.println("resp json: "+respJson);
            return JSON.parseObject(respJson, RpcfxResponse.class);
        }


    }

}
