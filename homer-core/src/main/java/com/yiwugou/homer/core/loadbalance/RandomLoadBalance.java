package com.yiwugou.homer.core.loadbalance;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Random;

import com.yiwugou.homer.core.Server;

public class RandomLoadBalance implements LoadBalance {
    private final Random random = new Random();

    @Override
    public Server choose(List<Server> servers, Method method, Object[] args) {
        if (servers == null || servers.size() == 0) {
            return null;
        }
        if (servers.size() == 1) {
            return servers.get(0);
        }
        int length = servers.size();
        return servers.get(this.random.nextInt(length));
    }

}
