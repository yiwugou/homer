package com.yiwugou.homer.core.filter;

import com.yiwugou.homer.core.invoker.Invoker;

/**
 *
 * RetryFilter
 * 
 * @author zhanxiaoyong@yiwugou.com
 *
 * @since 2017年7月10日 下午3:13:12
 */
public class RetryFilter implements Filter {

    @Override
    public Object invoke(Invoker invoker, Object[] args) throws Throwable {
        int retry = invoker.getMethodMetadata().getRetry();
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
