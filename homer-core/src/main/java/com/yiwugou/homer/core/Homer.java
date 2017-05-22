package com.yiwugou.homer.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.yiwugou.homer.core.client.Client;
import com.yiwugou.homer.core.client.HttpClient;
import com.yiwugou.homer.core.codec.Decoder;
import com.yiwugou.homer.core.codec.DefaultDecoder;
import com.yiwugou.homer.core.config.ConfigLoader;
import com.yiwugou.homer.core.config.NoneConfigLoader;
import com.yiwugou.homer.core.filter.ActiveFilter;
import com.yiwugou.homer.core.filter.CacheFilter;
import com.yiwugou.homer.core.filter.ExecuteFilter;
import com.yiwugou.homer.core.filter.Filter;
import com.yiwugou.homer.core.filter.MockFilter;
import com.yiwugou.homer.core.filter.cache.FilterCache;
import com.yiwugou.homer.core.filter.cache.JvmFilterCache;
import com.yiwugou.homer.core.interceptor.RequestInterceptor;

import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public final class Homer {
    public static Homer instance() {
        return new Homer();
    }

    @Setter
    private Client client = new HttpClient();
    @Setter
    private ConfigLoader configLoader = new NoneConfigLoader();
    @Setter
    private FilterCache filterCache = new JvmFilterCache();
    @Setter
    private Decoder decoder = new DefaultDecoder();

    private List<Filter> filters = new LinkedList<>();

    private List<RequestInterceptor> requestInterceptors = new ArrayList<>();

    public Homer addFilter(int index, Filter filter) {
        this.filters.add(index, filter);
        return this;
    }

    public Homer addFilter(Filter filter) {
        this.filters.add(filter);
        return this;
    }

    public Homer addRequestInterceptor(RequestInterceptor requestInterceptor) {
        this.requestInterceptors.add(requestInterceptor);
        return this;
    }

    private void addDefaultFilters() {
        this.addFilter(0, new ExecuteFilter()).addFilter(0, new ActiveFilter())
                .addFilter(0, new CacheFilter(this.filterCache)).addFilter(0, new MockFilter());
    }

    public <T> T build(Class<T> clazz) {
        this.addDefaultFilters();
        InvocationHandler invocationHandler = new ProxyInvocationHandler(clazz, this.client, this.configLoader,
                this.filters, this.requestInterceptors, this.decoder);
        T proxy = (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[] { clazz }, invocationHandler);
        return proxy;
    }

}
