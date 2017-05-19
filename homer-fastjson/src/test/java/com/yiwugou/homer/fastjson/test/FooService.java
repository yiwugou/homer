package com.yiwugou.homer.fastjson.test;

import com.yiwugou.homer.core.annotation.RequestMapping;
import com.yiwugou.homer.core.annotation.RequestUrl;

@RequestUrl
public interface FooService {
    @RequestMapping("foo")
    public void foo();
}
