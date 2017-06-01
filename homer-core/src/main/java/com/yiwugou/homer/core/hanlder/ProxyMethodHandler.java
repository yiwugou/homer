package com.yiwugou.homer.core.hanlder;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import com.yiwugou.homer.core.Homer;
import com.yiwugou.homer.core.Request;
import com.yiwugou.homer.core.Response;
import com.yiwugou.homer.core.annotation.RequestUrl;
import com.yiwugou.homer.core.client.Client;
import com.yiwugou.homer.core.codec.Decoder;
import com.yiwugou.homer.core.config.MethodOptions;
import com.yiwugou.homer.core.exception.ResponseException;
import com.yiwugou.homer.core.exception.ServerException;
import com.yiwugou.homer.core.factory.MethodOptionsFactory;
import com.yiwugou.homer.core.factory.RequestFactory;
import com.yiwugou.homer.core.filter.ActiveFilter;
import com.yiwugou.homer.core.filter.CacheFilter;
import com.yiwugou.homer.core.filter.ExecuteFilter;
import com.yiwugou.homer.core.filter.Filter;
import com.yiwugou.homer.core.filter.MockFilter;
import com.yiwugou.homer.core.interceptor.BasicAuthRequestInterceptor;
import com.yiwugou.homer.core.interceptor.RequestInterceptor;
import com.yiwugou.homer.core.invoker.DefaultInvoker;
import com.yiwugou.homer.core.invoker.Invoker;
import com.yiwugou.homer.core.server.Server;
import com.yiwugou.homer.core.util.CommonUtils;

public class ProxyMethodHandler extends AbstractMethodHandler {
    private Client client;
    private Method method;
    private MethodOptions methodOptions;
    private List<RequestInterceptor> requestInterceptors;
    private Decoder decoder;
    private Class<?> clazz;
    private List<Filter> filters;

    private Invoker invoker;

    public ProxyMethodHandler(Homer homer, Method method) {
        super();
        this.client = homer.getClient();
        this.method = method;
        this.requestInterceptors = homer.getRequestInterceptors();
        this.decoder = homer.getDecoder();
        this.clazz = this.method.getDeclaringClass();
        this.filters = homer.getFilters();
        this.methodOptions = new MethodOptionsFactory(method, homer.getConfigLoader(), homer.getInstanceCreater())
                .create();
        this.addDefaultFilters(homer);
        this.initDefaultRequestInterceptors();
        this.initInvoke();
    }

    @Override
    public Object invoke(Object[] args) throws Throwable {
        return this.invoker.invoke(args);
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

    private void addDefaultFilters(Homer homer) {
        this.filters.add(0, new ExecuteFilter());
        this.filters.add(0, new ActiveFilter());
        this.filters.add(0, new MockFilter());
        if (homer.getFilterCache() != null) {
            this.filters.add(1, new CacheFilter(homer.getFilterCache()));
        }
    }

    private void initInvoke() {
        this.invoker = this.buildFilterChain(new DefaultInvoker(this.methodOptions, this.method, new MethodHandler() {
            @Override
            public Object invoke(Object[] args) throws Throwable {
                return ProxyMethodHandler.this.invoker(args);
            }
        }));
    }

    private Object invoker(Object[] args) throws Exception {
        while (true) {
            Server server = this.methodOptions.getLoadBalance()
                    .choose(this.methodOptions.getServerHandler().getUpServers(), this.method, args);
            if (server == null) {
                throw new ServerException("no server is available! down servers is "
                        + this.methodOptions.getServerHandler().getDownServers());
            }
            try {
                Request request = new RequestFactory(this.method, this.methodOptions, args, server).create();
                if (this.requestInterceptors != null) {
                    for (RequestInterceptor requestInterceptor : this.requestInterceptors) {
                        requestInterceptor.apply(request);
                    }
                }
                Object obj = this.executeAndDecode(request);
                this.methodOptions.getServerHandler().getServerCheck().serverUp(server);
                return obj;
            } catch (IOException e) {
                this.methodOptions.getServerHandler().getServerCheck().serverDown(server, e);
            }
        }
    }

    private Invoker buildFilterChain(final Invoker invoker) {
        Invoker last = invoker;
        for (int i = this.filters.size() - 1; i >= 0; i--) {
            final Filter filter = this.filters.get(i);
            final Invoker next = last;
            last = new Invoker() {
                @Override
                public Object invoke(Object[] args) throws Throwable {
                    return filter.invoke(next, args);
                }

                @Override
                public Method getMethod() {
                    return invoker.getMethod();
                }

                @Override
                public MethodOptions getMethodOptions() {
                    return invoker.getMethodOptions();
                }
            };
        }
        return last;
    }

    private Object executeAndDecode(Request request) throws Exception {
        int retry = this.methodOptions.getRetry();
        while (true) {
            try {
                Response response = this.client.execute(request);
                if (response.getCode() >= 400) {
                    throw new ResponseException(response);
                }
                return this.decoder.decode(response, this.method.getGenericReturnType());
            } catch (Exception e) {
                if (--retry < 0) {
                    throw e;
                }
            }
        }
    }

}
