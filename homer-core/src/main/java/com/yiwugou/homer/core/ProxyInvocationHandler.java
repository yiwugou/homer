package com.yiwugou.homer.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

        if (handle.getMethodModel() == MethodModelEnum.FUTURE) {
            return this.futureInvoke(handle, args);
        }

        return handle.invoke(args);
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
