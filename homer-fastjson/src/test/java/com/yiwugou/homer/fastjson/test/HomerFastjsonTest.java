package com.yiwugou.homer.fastjson.test;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.yiwugou.homer.core.Homer;
import com.yiwugou.homer.fastjson.FastjsonDecoder;

public class HomerFastjsonTest {
    private FooService fooService;

    @Before
    public void before() {
        this.fooService = Homer.builder().decoder(new FastjsonDecoder()).build().proxy(FooService.class);
    }

    @Test
    public void test1() {
        List<Foo> foos = this.fooService.foo2Get("hello", "world");
        System.err.println(foos);

        Foo foo = this.fooService.foo1("hello", "world");
        System.err.println(foo);

        Map<String, Object> map = this.fooService.foo();
        System.err.println(map);

        Long l = this.fooService.foo3();
        System.err.println(l);

        Map<String, Foo> map1 = this.fooService.foo4();
        System.err.println(map1);
    }

}
