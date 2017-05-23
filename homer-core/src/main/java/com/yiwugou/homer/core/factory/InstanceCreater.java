package com.yiwugou.homer.core.factory;

import com.yiwugou.homer.core.annotation.RequestUrl;
import com.yiwugou.homer.core.config.ConfigLoader;
import com.yiwugou.homer.core.server.ServerHandler;

public interface InstanceCreater {
    ServerHandler createServerHandler(RequestUrl requestUrl, Class<?> clazz, ConfigLoader configLoader);
}
