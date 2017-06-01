package com.yiwugou.homer.core.filter;

import java.util.concurrent.atomic.AtomicInteger;

import com.yiwugou.homer.core.exception.ActiveException;
import com.yiwugou.homer.core.invoker.Invoker;

/**
 *
 * LimitFilter
 *
 * @author zhanxiaoyong
 *
 * @since 2017年5月12日 下午4:01:55
 */
public class ActiveFilter implements Filter {

    private AtomicInteger data = new AtomicInteger(0);

    @Override
    public Object invoke(Invoker invoker, Object[] args) throws Throwable {
        Object obj = null;
        try {
            if (this.data.incrementAndGet() > invoker.getMethodOptions().getActive()) {
                throw new ActiveException("active is " + invoker.getMethodOptions().getActive());
            }
            obj = invoker.invoke(args);
        } finally {
            this.data.decrementAndGet();
        }

        return obj;
    }

}
