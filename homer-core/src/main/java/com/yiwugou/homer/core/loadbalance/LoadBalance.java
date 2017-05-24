package com.yiwugou.homer.core.loadbalance;

import java.lang.reflect.Method;
import java.util.List;

import com.yiwugou.homer.core.server.Server;

public interface LoadBalance {
    Server choose(List<Server> servers, Method method, Object[] args);
}
