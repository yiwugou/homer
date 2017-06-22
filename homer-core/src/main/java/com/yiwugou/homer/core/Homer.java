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
import com.yiwugou.homer.core.factory.DefaultInstanceCreater;
import com.yiwugou.homer.core.factory.InstanceCreater;
import com.yiwugou.homer.core.filter.Filter;
import com.yiwugou.homer.core.filter.cache.FilterCache;
import com.yiwugou.homer.core.interceptor.RequestInterceptor;

import lombok.Getter;

/**
 *
 * Homer
 *
 * @author zhanxiaoyong@yiwugou.com
 *
 * @since 2017年5月24日 上午10:30:41
 */
public final class Homer {

    @Getter
    private final Client client;
    @Getter
    private final ConfigLoader configLoader;
    @Getter
    private final FilterCache filterCache;
    @Getter
    private final Decoder decoder;
    @Getter
    private final InstanceCreater instanceCreater;
    @Getter
    private final int threadPoolSize;
    @Getter
    private final List<Filter> filters;
    @Getter
    private final List<RequestInterceptor> requestInterceptors;

    private Homer(Client client, ConfigLoader configLoader, FilterCache filterCache, Decoder decoder,
            InstanceCreater instanceCreater, int threadPoolSize, List<Filter> filters,
            List<RequestInterceptor> requestInterceptors) {
        this.client = client;
        this.configLoader = configLoader;
        this.filterCache = filterCache;
        this.decoder = decoder;
        this.instanceCreater = instanceCreater;
        this.threadPoolSize = threadPoolSize;
        this.filters = filters;
        this.requestInterceptors = requestInterceptors;
    }

    public <T> T proxy(Class<T> clazz) {
        InvocationHandler invocationHandler = new ProxyInvocationHandler(clazz, this);
        T proxy = (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[] { clazz }, invocationHandler);
        return proxy;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Client client = new HttpClient();

        private ConfigLoader configLoader = new NoneConfigLoader();

        private FilterCache filterCache = null;

        private Decoder decoder = new DefaultDecoder();

        private InstanceCreater instanceCreater = new DefaultInstanceCreater();

        private int threadPoolSize = 10;

        private List<Filter> filters = new LinkedList<>();

        private List<RequestInterceptor> requestInterceptors = new ArrayList<>();

        public Builder client(Client client) {
            this.client = client;
            return this;
        }

        public Builder configLoader(ConfigLoader configLoader) {
            this.configLoader = configLoader;
            return this;
        }

        public Builder filterCache(FilterCache filterCache) {
            this.filterCache = filterCache;
            return this;
        }

        public Builder decoder(Decoder decoder) {
            this.decoder = decoder;
            return this;
        }

        public Builder instanceCreater(InstanceCreater instanceCreater) {
            this.instanceCreater = instanceCreater;
            return this;
        }

        public Builder threadPoolSize(int threadPoolSize) {
            this.threadPoolSize = threadPoolSize;
            return this;
        }

        public Builder addFilter(int index, Filter filter) {
            this.filters.add(index, filter);
            return this;
        }

        public Builder addFilter(Filter filter) {
            this.filters.add(filter);
            return this;
        }

        public Builder addRequestInterceptor(RequestInterceptor requestInterceptor) {
            this.requestInterceptors.add(requestInterceptor);
            return this;
        }

        public Builder addRequestInterceptor(int index, RequestInterceptor requestInterceptor) {
            this.requestInterceptors.add(index, requestInterceptor);
            return this;
        }

        public <T> T proxy(Class<T> clazz) {
            return this.build().proxy(clazz);
        }

        public Homer build() {
            return new Homer(this.client, this.configLoader, this.filterCache, this.decoder, this.instanceCreater,
                    this.threadPoolSize, this.filters, this.requestInterceptors);
        }
    }

}
