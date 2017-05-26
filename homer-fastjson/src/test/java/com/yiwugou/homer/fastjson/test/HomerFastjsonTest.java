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
        String fooString = this.fooService.fooString();
        System.err.println(fooString);

        Map<String, Object> fooMap = this.fooService.fooMap();
        System.err.println(fooMap);

        List<Foo> foo2Get = this.fooService.foo2Get("hello", "world");
        System.err.println(foo2Get);

        List<Foo> foo2Post = this.fooService.foo2Post("hellopost", "worldpost", 5);
        System.err.println(foo2Post);

        Foo foo1 = this.fooService.foo1("hello", "world");
        System.err.println(foo1);

        Long foo3 = this.fooService.foo3();
        System.err.println(foo3);

        Map<String, Foo> foo4 = this.fooService.foo4();
        System.err.println(foo4);

        this.fooService.foo5();
        System.err.println();

        Map<String, List<Foo>> foo6 = this.fooService.foo6();
        System.err.println(foo6);
    }

}
