package com.yiwugou.homer.eureka;

import java.util.Properties;

import com.yiwugou.homer.core.annotation.RequestUrl;
import com.yiwugou.homer.core.config.ConfigLoader;
import com.yiwugou.homer.core.factory.InstanceCreater;
import com.yiwugou.homer.core.server.ServerHandler;

public class EurekaInstanceCreater implements InstanceCreater {

    private Properties properties;

    public EurekaInstanceCreater(Properties properties) {
        this.properties = properties;
    }

    public EurekaInstanceCreater() {

    }

    @Override
    public ServerHandler createServerHandler(RequestUrl requestUrl, Class<?> clazz, ConfigLoader configLoader) {
        ServerHandler serverHandler = new EurekaServerHandler(requestUrl, clazz, configLoader, this.properties);
        return serverHandler;
    }

}
