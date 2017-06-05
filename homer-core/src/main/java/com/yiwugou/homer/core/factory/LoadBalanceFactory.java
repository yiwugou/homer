package com.yiwugou.homer.core.factory;

import com.yiwugou.homer.core.loadbalance.LoadBalance;
import com.yiwugou.homer.core.loadbalance.RandomLoadBalance;

public class LoadBalanceFactory {
    public static LoadBalance createInstants(Class<? extends LoadBalance> clazz) {
        try {
            if (clazz != null) {
                return clazz.newInstance();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new RandomLoadBalance();
    }
}
