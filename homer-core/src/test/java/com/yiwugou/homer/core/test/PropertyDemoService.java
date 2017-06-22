package com.yiwugou.homer.core.test;

import com.yiwugou.homer.core.annotation.RequestConfig;
import com.yiwugou.homer.core.annotation.RequestHeader;
import com.yiwugou.homer.core.annotation.RequestHeaders;
import com.yiwugou.homer.core.annotation.RequestMapping;
import com.yiwugou.homer.core.annotation.RequestUrl;
import com.yiwugou.homer.core.loadbalance.RoundRobinLoadBalance;

@RequestUrl()
@RequestHeaders({ @RequestHeader(name = "", value = "") })
public interface PropertyDemoService {

    @RequestMapping("foo")
    @RequestConfig(execute = 10000, active = 10000, loadBalance = RoundRobinLoadBalance.class)
    String foo();

}
