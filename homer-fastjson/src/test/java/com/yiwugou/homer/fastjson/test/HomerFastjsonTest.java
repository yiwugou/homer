package com.yiwugou.homer.fastjson.test;

import com.yiwugou.homer.core.Homer;
import com.yiwugou.homer.fastjson.FastjsonDecoder;

public class HomerFastjsonTest {
    public void test1() {
        FooService fooService = Homer.instance().setDecoder(new FastjsonDecoder()).build(FooService.class);
    }
}
