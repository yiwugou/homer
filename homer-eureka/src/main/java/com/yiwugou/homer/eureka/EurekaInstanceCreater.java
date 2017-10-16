package com.yiwugou.homer.eureka;

import java.util.Properties;

import com.yiwugou.homer.core.annotation.RequestUrl;
import com.yiwugou.homer.core.config.ConfigLoader;
import com.yiwugou.homer.core.factory.InstanceCreater;
import com.yiwugou.homer.core.server.ServerHandler;

/**
 *
 * EurekaInstanceCreater
 *
 * @author zhanxiaoyong@yiwugou.com
 *
 * @since 2017年10月16日 下午4:20:02
 */
public class EurekaInstanceCreater implements InstanceCreater {
    private String namespace;
    private Properties properties;

    public EurekaInstanceCreater(String namespace, Properties properties) {
        this.namespace = namespace;
        this.properties = properties;
    }

    public EurekaInstanceCreater() {

    }

    @Override
    public ServerHandler createServerHandler(RequestUrl requestUrl, Class<?> clazz, ConfigLoader configLoader) {
        ServerHandler serverHandler = new EurekaServerHandler(requestUrl, clazz, configLoader, this.namespace,
                this.properties);
        return serverHandler;
    }

}
