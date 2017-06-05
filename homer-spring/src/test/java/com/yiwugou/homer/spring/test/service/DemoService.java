package com.yiwugou.homer.spring.test.service;

import com.yiwugou.homer.core.annotation.RequestConfig;
import com.yiwugou.homer.core.annotation.RequestMapping;
import com.yiwugou.homer.core.annotation.RequestParam;
import com.yiwugou.homer.core.annotation.RequestUrl;
import com.yiwugou.homer.core.enums.MethodEnum;
import com.yiwugou.homer.core.loadbalance.RoundRobinLoadBalance;

@RequestUrl({ "http://127.0.0.1:8762", "http://127.0.0.1:8763", "http://127.0.0.1:8764" })
@RequestConfig(retry = 1, execute = 1000, active = 100, mock = true, loadBalance = RoundRobinLoadBalance.class)
public interface DemoService {

    @RequestMapping("foo")
    @RequestConfig()
    String foo();

    @RequestMapping(value = "foo1/{username}/{password}", method = MethodEnum.GET)
    @RequestConfig()
    String foo1(@RequestParam("username") String username, @RequestParam("password") String password);

    @RequestMapping(value = "foo2", method = MethodEnum.GET)
    @RequestConfig()
    String foo2Get(@RequestParam("username") String username, @RequestParam("password") String password);

    @RequestMapping(value = "foo2", method = MethodEnum.POST)
    @RequestConfig()
    String foo2Post(@RequestParam("username") String username, @RequestParam("password") String password);
}
