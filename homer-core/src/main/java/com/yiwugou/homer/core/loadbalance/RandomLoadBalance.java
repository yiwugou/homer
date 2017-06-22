package com.yiwugou.homer.core.loadbalance;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Random;

import com.yiwugou.homer.core.server.Server;

public class RandomLoadBalance implements LoadBalance {
    private final Random random = new Random();

    @Override
    public Server choose(List<Server> servers, Method method, Object[] args) {
        if (servers == null || servers.size() == 0) {
            return null;
        }
        int length = servers.size();
        if (servers.size() == 1) {
            return servers.get(0);
        }

        boolean sameWeight = true;
        int totalWeight = 0;
        for (int i = 0; i < length; i++) {
            int weight = servers.get(i).getWeight();
            totalWeight += weight;
            if (sameWeight && i > 0 && weight != servers.get(i - 1).getWeight()) {
                sameWeight = false;
            }
        }
        if (totalWeight > 0 && !sameWeight) {
            int offset = this.random.nextInt(totalWeight);
            for (int i = 0; i < length; i++) {
                offset -= servers.get(i).getWeight();
                if (offset < 0) {
                    return servers.get(i);
                }
            }
        }

        return servers.get(this.random.nextInt(length));
    }

}
