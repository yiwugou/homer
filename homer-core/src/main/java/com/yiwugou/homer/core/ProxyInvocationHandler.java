package com.yiwugou.homer.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yiwugou.homer.core.enums.MethodModelEnum;
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
    private static final Logger log = LoggerFactory.getLogger(ProxyInvocationHandler.class);
    private Class<?> clazz;
    private Homer homer;

    private final Map<Method, MethodHandler> methodHandlerMap = new ConcurrentHashMap<>();

    private ExecutorService executor;

    public ProxyInvocationHandler(Class<?> clazz, Homer homer) {
        super();
        this.clazz = clazz;
        this.homer = homer;
        this.executor = Executors.newFixedThreadPool(homer.getThreadPoolSize());
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (this.methodHandlerMap.get(method) == null) {
            synchronized (method) {
                if (this.methodHandlerMap.get(method) == null) {
                    this.methodHandlerMap.put(method,
                            new MethodHandlerFactory(this.clazz, this.homer, method).create());
                }
            }
        }

        MethodHandler handle = this.methodHandlerMap.get(method);
        long start = System.currentTimeMillis();
        Object obj = null;
        if (handle.getMethodModel() == MethodModelEnum.FUTURE) {
            obj = this.futureInvoke(handle, args);
        } else {
            obj = handle.invoke(args);
        }
        log.debug("method is {}, args is {}, back is {}, waste time is {}", method, args, obj,
                System.currentTimeMillis() - start);
        return obj;
    }

    public Future<Object> futureInvoke(final MethodHandler handle, final Object[] args) {
        Future<Object> future = this.executor.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                try {
                    Object obj = handle.invoke(args);
                    return obj;
                } catch (Throwable e) {
                    throw new Exception(e);
                }
            }
        });
        return future;
    }

}
