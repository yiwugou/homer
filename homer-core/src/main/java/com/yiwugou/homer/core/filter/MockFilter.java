package com.yiwugou.homer.core.filter;

import com.yiwugou.homer.core.invoker.Invoker;

/**
 *
 * MockFilter
 *
 * @author zhanxiaoyong
 *
 * @since 2017年5月12日 下午4:02:00
 */
public class MockFilter implements Filter {
    @Override
    public Object invoke(Invoker invoker) throws Throwable {
        Object obj = null;
        try {
            obj = invoker.invoke();
        } catch (Exception e) {
            if (invoker.getMethodOptions().getMock()) {
                e.printStackTrace();
            } else {
                throw e;
            }
        }
        return obj;
    }
}