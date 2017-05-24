package com.yiwugou.homer.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.yiwugou.homer.core.annotation.RequestUrl;
import com.yiwugou.homer.core.client.Client;
import com.yiwugou.homer.core.codec.Decoder;
import com.yiwugou.homer.core.config.ConfigLoader;
import com.yiwugou.homer.core.config.MethodOptions;
import com.yiwugou.homer.core.factory.InstanceCreater;
import com.yiwugou.homer.core.factory.MethodHandlerFactory;
import com.yiwugou.homer.core.factory.MethodOptionsFactory;
import com.yiwugou.homer.core.filter.Filter;
import com.yiwugou.homer.core.hanlder.MethodHandler;
import com.yiwugou.homer.core.interceptor.BasicAuthRequestInterceptor;
import com.yiwugou.homer.core.interceptor.RequestInterceptor;
import com.yiwugou.homer.core.invoker.DefaultInvoker;
import com.yiwugou.homer.core.invoker.Invoker;
import com.yiwugou.homer.core.util.CommonUtils;

/**
 *
 * ProxyInvocationHandler
 *
 * @author zhanxiaoyong@yiwugou.com
 *
 * @since 2017年5月24日 上午10:31:33
 */
public class ProxyInvocationHandler implements InvocationHandler {
    private Class<?> clazz;
    private Client client;
    private ConfigLoader configLoader;
    private List<Filter> filters;
    private List<RequestInterceptor> requestInterceptors;
    private Decoder decoder;
    private InstanceCreater instanceCreater;
    private final Map<Method, MethodHandler> methodHandlerMap = new ConcurrentHashMap<>();

    private final Map<Method, MethodOptions> methodOptionsMap = new ConcurrentHashMap<>();

    public ProxyInvocationHandler(Class<?> clazz, Homer homer) {
        super();
        this.clazz = clazz;
        this.client = homer.getClient();
        this.configLoader = homer.getConfigLoader();
        this.filters = homer.getFilters();
        this.requestInterceptors = homer.getRequestInterceptors();
        this.decoder = homer.getDecoder();
        this.instanceCreater = homer.getInstanceCreater();
        this.initDefaultRequestInterceptors();
    }

    private void initDefaultRequestInterceptors() {
        RequestUrl requestUrl = this.clazz.getAnnotation(RequestUrl.class);
        if (CommonUtils.hasTest(requestUrl.basicAuth())) {
            String[] us = requestUrl.basicAuth().split(":");
            String username = us[0];
            String password = us[1];
            this.requestInterceptors.add(new BasicAuthRequestInterceptor(username, password));
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (this.methodOptionsMap.get(method) == null) {
            this.methodOptionsMap.put(method,
                    new MethodOptionsFactory(method, this.configLoader, this.instanceCreater).create());
        }

        if (this.methodHandlerMap.get(method) == null) {
            this.methodHandlerMap.put(method,
                    new MethodHandlerFactory(this.client, method, this.methodOptionsMap.get(method),
                            this.requestInterceptors, this.decoder, this.instanceCreater).create());
        }

        Invoker invoker = this.buildFilterChain(
                new DefaultInvoker(this.methodHandlerMap.get(method), this.methodOptionsMap.get(method), method, args));
        return invoker.invoke();
    }

    private Invoker buildFilterChain(final Invoker invoker) {
        Invoker last = invoker;
        for (int i = this.filters.size() - 1; i >= 0; i--) {
            final Filter filter = this.filters.get(i);
            final Invoker next = last;
            last = new Invoker() {
                @Override
                public Object invoke() throws Throwable {
                    return filter.invoke(next);
                }

                @Override
                public Method getMethod() {
                    return invoker.getMethod();
                }

                @Override
                public Object[] getArgs() {
                    return invoker.getArgs();
                }

                @Override
                public MethodOptions getMethodOptions() {
                    return invoker.getMethodOptions();
                }
            };
        }
        return last;
    }

}
