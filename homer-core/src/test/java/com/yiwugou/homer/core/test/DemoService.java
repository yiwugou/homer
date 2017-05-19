package com.yiwugou.homer.core.test;

import com.yiwugou.homer.core.annotation.RequestConfig;
import com.yiwugou.homer.core.annotation.RequestMapping;
import com.yiwugou.homer.core.annotation.RequestParam;
import com.yiwugou.homer.core.annotation.RequestUrl;
import com.yiwugou.homer.core.enums.MethodEnum;

@RequestUrl({ "http://127.0.0.1:8762", "http://127.0.0.1:8763", "http://127.0.0.1:8764" })

public interface DemoService {

    @RequestMapping("foo")
    @RequestConfig(execute = 10000, active = 10000)
    String foo();

    @RequestMapping(value = "foo1/{username}/{password}", method = MethodEnum.GET)

    String foo1(@RequestParam("username") String username, @RequestParam("password") String password);

    @RequestMapping(value = "foo2", method = MethodEnum.GET)

    String foo2Get(@RequestParam("username") String username, @RequestParam("password") String password);

    @RequestMapping(value = "foo2", method = MethodEnum.POST)

    String foo2Post(@RequestParam("username") String username, @RequestParam("password") String password);
}
