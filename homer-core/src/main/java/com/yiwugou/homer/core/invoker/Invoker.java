package com.yiwugou.homer.core.invoker;

import java.lang.reflect.Method;

import com.yiwugou.homer.core.config.MethodOptions;

/**
 *
 * Invoker
 * 
 * @author zhanxiaoyong@yiwugou.com
 *
 * @since 2017年7月10日 下午3:14:19
 */
public interface Invoker {
    Object invoke(Object[] args) throws Throwable;

    Method getMethod();

    MethodOptions getMethodOptions();

}
