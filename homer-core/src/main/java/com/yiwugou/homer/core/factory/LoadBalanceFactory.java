package com.yiwugou.homer.core.factory;

import com.yiwugou.homer.core.enums.LoadBalanceEnum;
import com.yiwugou.homer.core.loadbalance.ConsistentHashLoadBalance;
import com.yiwugou.homer.core.loadbalance.LoadBalance;
import com.yiwugou.homer.core.loadbalance.RandomLoadBalance;
import com.yiwugou.homer.core.loadbalance.RoundRobinLoadBalance;

public class LoadBalanceFactory {
    public static LoadBalance createInstants(String name) {
        if (LoadBalanceEnum.CONSISTENT_HASH.toString().equalsIgnoreCase(name)) {
            return new ConsistentHashLoadBalance();
        }
        if (LoadBalanceEnum.ROUND_ROBIN.toString().equalsIgnoreCase(name)) {
            return new RoundRobinLoadBalance();
        }
        return new RandomLoadBalance();
    }
}
