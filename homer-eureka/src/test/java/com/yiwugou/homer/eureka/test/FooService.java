package com.yiwugou.homer.eureka.test;

import com.yiwugou.homer.core.annotation.RequestConfig;
import com.yiwugou.homer.core.annotation.RequestMapping;
import com.yiwugou.homer.core.annotation.RequestUrl;
import com.yiwugou.homer.core.loadbalance.RoundRobinLoadBalance;

/**
 *
 * FooService
 *
 * @author zhanxiaoyong@yiwugou.com
 *
 * @since 2017年10月16日 下午4:21:13
 */
@RequestUrl("ms-redis-service")
public interface FooService {
    @RequestMapping("foo")
    @RequestConfig(loadBalance = RoundRobinLoadBalance.class)
    public String foo();
}
