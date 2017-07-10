package com.yiwugou.homer.core.factory;

import com.yiwugou.homer.core.loadbalance.LoadBalance;
import com.yiwugou.homer.core.loadbalance.RandomLoadBalance;

/**
 *
 * LoadBalanceFactory
 * 
 * @author zhanxiaoyong@yiwugou.com
 *
 * @since 2017年7月10日 下午3:12:35
 */
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
