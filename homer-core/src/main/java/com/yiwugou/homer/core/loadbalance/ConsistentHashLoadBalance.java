package com.yiwugou.homer.core.loadbalance;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import com.yiwugou.homer.core.server.Server;

public class ConsistentHashLoadBalance implements LoadBalance {

    public int hash(Method method, Object[] args) {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(args);
        result = prime * result + ((method == null) ? 0 : method.hashCode());
        return result;
    }

    @Override
    public Server choose(List<Server> servers, Method method, Object[] args) {
        if (servers == null || servers.size() == 0) {
            return null;
        }
        if (servers.size() == 1) {
            return servers.get(0);
        }

        int hash = Math.abs(this.hash(method, args));
        int index = hash % servers.size();
        return servers.get(index);
    }

}
