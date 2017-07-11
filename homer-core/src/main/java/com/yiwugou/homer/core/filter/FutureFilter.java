package com.yiwugou.homer.core.filter;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.yiwugou.homer.core.invoker.Invoker;

public class FutureFilter implements Filter {
    private ExecutorService executor;

    public FutureFilter(int threadPoolSize) {
        this.executor = Executors.newFixedThreadPool(threadPoolSize);
    }

    @Override
    public Object invoke(final Invoker invoker, final Object[] args) throws Throwable {
        Future<Object> future = this.executor.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                try {
                    Object obj = invoker.invoke(args);
                    return obj;
                } catch (Throwable e) {
                    throw new Exception(e);
                }
            }
        });
        return future;
    }

}
