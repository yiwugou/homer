package com.yiwugou.homer.eureka.test;

import com.yiwugou.homer.core.annotation.RequestMapping;
import com.yiwugou.homer.core.annotation.RequestUrl;

@RequestUrl
public interface BarService {
    @RequestMapping("foo")
    public String foo();
}
