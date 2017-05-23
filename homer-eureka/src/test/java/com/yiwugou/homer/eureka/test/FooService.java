package com.yiwugou.homer.eureka.test;

import com.yiwugou.homer.core.annotation.RequestConfig;
import com.yiwugou.homer.core.annotation.RequestMapping;
import com.yiwugou.homer.core.annotation.RequestUrl;
import com.yiwugou.homer.core.enums.LoadBalanceEnum;

@RequestUrl("ms-redis-service")
public interface FooService {
    @RequestMapping("foo")
    @RequestConfig(loadBalance = LoadBalanceEnum.ROUND_ROBIN)
    public String foo();
}
