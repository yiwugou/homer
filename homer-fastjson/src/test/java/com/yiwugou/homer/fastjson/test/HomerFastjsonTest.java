package com.yiwugou.homer.fastjson.test;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

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
    public void test1() throws Exception {
        Future<?> foo1Future = this.fooService.foo1Future("futureUsername", "futurePassword");
        System.err.println("foo1Future " + foo1Future.get());

        Future<List<Foo>> foo2GetFuture = this.fooService.foo2GetFuture("futurefoo2Username", "futurefoo2Password");

        String fooString = this.fooService.fooString();
        System.err.println(fooString);

        System.err.println("foo2GetFuture " + foo2GetFuture.get());

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
