package com.yiwugou.homer.core.filter;

import com.yiwugou.homer.core.invoker.Invoker;

public class RetryFilter implements Filter {

    @Override
    public Object invoke(Invoker invoker, Object[] args) throws Throwable {
        int retry = invoker.getMethodOptions().getRetry();
        while (true) {
            try {
                Object obj = invoker.invoke(args);
                return obj;
            } catch (Exception e) {
                if (--retry < 0) {
                    throw e;
                }
            }
        }
    }

}
