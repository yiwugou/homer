package com.yiwugou.homer.eureka;

import com.yiwugou.homer.core.annotation.RequestUrl;
import com.yiwugou.homer.core.config.ConfigLoader;
import com.yiwugou.homer.core.factory.InstanceCreater;
import com.yiwugou.homer.core.server.ServerHandler;
import com.yiwugou.homer.eureka.server.EurekaServerHandler;

public class EurekaInstanceCreater implements InstanceCreater {

    @Override
    public ServerHandler createServerHandler(RequestUrl requestUrl, Class<?> clazz, ConfigLoader configLoader) {
        ServerHandler serverHandler = new EurekaServerHandler(requestUrl, clazz, configLoader);
        return serverHandler;
    }

}
