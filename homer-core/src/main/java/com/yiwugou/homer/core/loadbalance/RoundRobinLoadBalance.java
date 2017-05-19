package com.yiwugou.homer.core.loadbalance;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.yiwugou.homer.core.Server;

public class RoundRobinLoadBalance implements LoadBalance {
    private AtomicInteger CYCLIC = new AtomicInteger(0);

    @Override
    public Server choose(List<Server> servers, Method method, Object[] args) {
        if (servers == null || servers.size() == 0) {
            return null;
        }
        int size = servers.size();
        if (size == 1) {
            return servers.get(0);
        }
        int index = this.incrementAndGetModulo(size);

        return servers.get(index);
    }

    private int incrementAndGetModulo(int modulo) {
        for (;;) {
            int current = this.CYCLIC.get();
            int next = (current + 1) % modulo;
            if (this.CYCLIC.compareAndSet(current, next)) {
                return next;
            }
        }
    }

}
