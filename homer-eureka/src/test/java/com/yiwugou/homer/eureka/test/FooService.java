package com.yiwugou.homer.eureka.test;

import com.yiwugou.homer.core.annotation.RequestConfig;
import com.yiwugou.homer.core.annotation.RequestMapping;
import com.yiwugou.homer.core.annotation.RequestUrl;
import com.yiwugou.homer.core.loadbalance.RoundRobinLoadBalance;

@RequestUrl("ms-redis-service")
public interface FooService {
    @RequestMapping("foo")
    @RequestConfig(loadBalance = RoundRobinLoadBalance.class)
    public String foo();
}
