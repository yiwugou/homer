package com.yiwugou.homer.core.test;

import com.yiwugou.homer.core.annotation.RequestConfig;
import com.yiwugou.homer.core.annotation.RequestHeader;
import com.yiwugou.homer.core.annotation.RequestHeaders;
import com.yiwugou.homer.core.annotation.RequestMapping;
import com.yiwugou.homer.core.annotation.RequestParam;
import com.yiwugou.homer.core.annotation.RequestUrl;
import com.yiwugou.homer.core.enums.MethodEnum;
import com.yiwugou.homer.core.loadbalance.RandomLoadBalance;
import com.yiwugou.homer.core.loadbalance.RoundRobinLoadBalance;

@RequestUrl(value = { "http://127.0.0.1:8762,weight=10", "http://127.0.0.1:8763,weight=2",
        "http://127.0.0.1:8764,weight=5" }, fallback = DemoServiceFallback.class)
@RequestHeaders({ @RequestHeader(name = "", value = "") })
public interface DemoService {

    @RequestMapping("foo")
    @RequestConfig(execute = 10000, active = 10000, loadBalance = RoundRobinLoadBalance.class)
    String foo();

    @RequestMapping(value = "foo1/{username}/{password}", method = MethodEnum.GET)
    @RequestHeaders({ @RequestHeader(name = "", value = "") })
    String foo1(@RequestParam("username") String username, @RequestParam("password") String password);

    @RequestMapping(value = "foo2", method = MethodEnum.GET)
    String foo2Get(@RequestParam("username") String username, @RequestParam("password") String password);

    @RequestMapping(value = "foo2", method = MethodEnum.POST)
    String foo2Post(@RequestParam("username") String username, @RequestParam("password") String password);

    @RequestMapping("foo")
    @RequestConfig(execute = 10000, active = 10000, loadBalance = RandomLoadBalance.class)
    String randomLoadBalance();

}
