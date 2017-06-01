package com.yiwugou.homer.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.yiwugou.homer.core.factory.MethodHandlerFactory;
import com.yiwugou.homer.core.hanlder.MethodHandler;

/**
 *
 * ProxyInvocationHandler
 *
 * @author zhanxiaoyong@yiwugou.com
 *
 * @since 2017年5月24日 上午10:31:33
 */
public class ProxyInvocationHandler implements InvocationHandler {
    private Class<?> clazz;
    private Homer homer;

    private final Map<Method, MethodHandler> methodHandlerMap = new ConcurrentHashMap<>();

    public ProxyInvocationHandler(Class<?> clazz, Homer homer) {
        super();
        this.clazz = clazz;
        this.homer = homer;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (this.methodHandlerMap.get(method) == null) {
            this.methodHandlerMap.put(method, new MethodHandlerFactory(this.clazz, this.homer, method).create());
        }
        return this.methodHandlerMap.get(method).invoke(args);
    }

}
