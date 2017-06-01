package com.yiwugou.homer.core.invoker;

import java.lang.reflect.Method;

import com.yiwugou.homer.core.config.MethodOptions;

public interface Invoker {
    Object invoke(Object[] args) throws Throwable;

    Method getMethod();

    MethodOptions getMethodOptions();

}
