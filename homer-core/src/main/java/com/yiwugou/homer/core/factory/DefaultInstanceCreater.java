package com.yiwugou.homer.core.factory;

import com.yiwugou.homer.core.annotation.RequestUrl;
import com.yiwugou.homer.core.config.ConfigLoader;
import com.yiwugou.homer.core.server.DefaultServerHandler;
import com.yiwugou.homer.core.server.ServerHandler;

public class DefaultInstanceCreater implements InstanceCreater {

    @Override
    public ServerHandler createServerHandler(RequestUrl requestUrl, Class<?> clazz, ConfigLoader configLoader) {
        ServerHandler serverHandler = new DefaultServerHandler(requestUrl, clazz, configLoader);

        return serverHandler;
    }

}
