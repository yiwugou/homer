package com.yiwugou.homer.core.invoker;

import java.lang.reflect.Method;

import com.yiwugou.homer.core.config.MethodOptions;

public interface Invoker {
    Object invoke() throws Throwable;

    Method getMethod();

    Object[] getArgs();

    MethodOptions getMethodOptions();

}
