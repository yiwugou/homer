package com.yiwugou.homer.eureka.spring.test;

import com.yiwugou.homer.core.annotation.RequestMapping;
import com.yiwugou.homer.core.annotation.RequestParam;
import com.yiwugou.homer.core.annotation.RequestUrl;
import com.yiwugou.homer.core.enums.MethodEnum;

@RequestUrl
public interface DemoService {

    @RequestMapping("foo")
    String foo();

    @RequestMapping(value = "foo1/{username}/{password}", method = MethodEnum.GET)
    String foo1(@RequestParam("username") String username, @RequestParam("password") String password);

    @RequestMapping(value = "foo2", method = MethodEnum.GET)
    String foo2Get(@RequestParam("username") String username, @RequestParam("password") String password);

    @RequestMapping(value = "foo2", method = MethodEnum.POST)
    String foo2Post(@RequestParam("username") String username, @RequestParam("password") String password);
}
