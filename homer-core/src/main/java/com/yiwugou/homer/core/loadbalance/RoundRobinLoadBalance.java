package com.yiwugou.homer.core.loadbalance;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.yiwugou.homer.core.server.Server;

public class RoundRobinLoadBalance implements LoadBalance {
    private AtomicInteger CYCLIC = new AtomicInteger(0);

    @Override
    public Server choose(List<Server> servers, Method method, Object[] args) {
        if (servers == null || servers.size() == 0) {
            return null;
        }
        int length = servers.size();
        if (length == 1) {
            return servers.get(0);
        }

        boolean sameWeight = true;
        for (int i = 0; i < length; i++) {
            int weight = servers.get(i).getWeight();
            if (sameWeight && i > 0 && weight != servers.get(i - 1).getWeight()) {
                sameWeight = false;
            }
        }
        if (!sameWeight) {
            List<Server> weightServers = new ArrayList<>();
            for (int i = 0; i < length; i++) {
                Server server = servers.get(i);
                for (int j = 0, weight = server.getWeight(); j < weight; j++) {
                    weightServers.add(server);
                }
            }
            servers = weightServers;
            length = servers.size();
        }

        int index = this.incrementAndGetModulo(length);

        return servers.get(index);
    }

    private int incrementAndGetModulo(int modulo) {
        while (true) {
            int current = this.CYCLIC.get();
            int next = (current + 1) % modulo;
            if (this.CYCLIC.compareAndSet(current, next)) {
                return next;
            }
        }
    }

}
