package com.yiwugou.homer.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yiwugou.homer.core.client.Client;
import com.yiwugou.homer.core.client.HttpClient;
import com.yiwugou.homer.core.codec.Decoder;
import com.yiwugou.homer.core.codec.DefaultDecoder;
import com.yiwugou.homer.core.config.ConfigLoader;
import com.yiwugou.homer.core.config.NoneConfigLoader;
import com.yiwugou.homer.core.factory.DefaultInstanceCreater;
import com.yiwugou.homer.core.factory.InstanceCreater;
import com.yiwugou.homer.core.filter.cache.FilterCache;

import lombok.Getter;
import lombok.ToString;

/**
 *
 * Homer
 *
 * @author zhanxiaoyong@yiwugou.com
 *
 * @since 2017年5月24日 上午10:30:41
 */
@ToString
public final class Homer {
    private static final Logger log = LoggerFactory.getLogger(Homer.class);
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

    private Homer(Client client, ConfigLoader configLoader, FilterCache filterCache, Decoder decoder,
            InstanceCreater instanceCreater, int threadPoolSize) {
        this.client = client;
        this.configLoader = configLoader;
        this.filterCache = filterCache;
        this.decoder = decoder;
        this.instanceCreater = instanceCreater;
        this.threadPoolSize = threadPoolSize;
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

        public <T> T proxy(Class<T> clazz) {
            return this.build().proxy(clazz);
        }

        public Homer build() {
            Homer homer = new Homer(this.client, this.configLoader, this.filterCache, this.decoder,
                    this.instanceCreater, this.threadPoolSize);
            log.debug("homer is {}", homer);
            return homer;
        }
    }

}
