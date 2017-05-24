package com.yiwugou.homer.fastjson.test;

import java.util.List;
import java.util.Map;

import com.yiwugou.homer.core.annotation.RequestMapping;
import com.yiwugou.homer.core.annotation.RequestParam;
import com.yiwugou.homer.core.annotation.RequestUrl;

@RequestUrl({ "http://127.0.0.1:8762", "http://127.0.0.1:8763", "http://127.0.0.1:8764" })
public interface FooService {

    @RequestMapping("foo")
    String fooString();

    @RequestMapping("foo")
    Map<String, Object> foo();

    @RequestMapping("foo1/{username}/{password}")
    Foo foo1(@RequestParam("username") String username, @RequestParam("password") String password);

    @RequestMapping("foo2")
    List<Foo> foo2Get(@RequestParam("username") String username, @RequestParam("password") String password);

    @RequestMapping("foo3")
    Long foo3();

    @RequestMapping("foo4")
    Map<String, Foo> foo4();
}
