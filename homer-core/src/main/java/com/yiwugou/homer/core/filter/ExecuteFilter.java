package com.yiwugou.homer.core.filter;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.yiwugou.homer.core.exception.ExecuteException;
import com.yiwugou.homer.core.invoker.Invoker;

/**
 *
 * ExecuteFilter
 *
 * @author zhanxiaoyong
 *
 * @since 2017年5月12日 下午4:01:44
 */
public class ExecuteFilter implements Filter {

    private LoadingCache<String, AtomicInteger> counter = CacheBuilder.newBuilder()
            .expireAfterWrite(1, TimeUnit.SECONDS).build(new CacheLoader<String, AtomicInteger>() {
                @Override
                public AtomicInteger load(String key) throws Exception {
                    return new AtomicInteger(0);
                }
            });

    @Override
    public Object invoke(Invoker invoker, Object[] args) throws Throwable {
        String methodName = invoker.getMethod().getName();
        if (this.counter.get(methodName).incrementAndGet() > invoker.getMethodMetadata().getExecute()) {
            throw new ExecuteException("execute is " + invoker.getMethodMetadata().getExecute());
        }
        return invoker.invoke(args);
    }

}
