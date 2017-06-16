package com.yiwugou.homer.core.filter;

import com.yiwugou.homer.core.annotation.RequestUrl;
import com.yiwugou.homer.core.factory.ClassFactory;
import com.yiwugou.homer.core.invoker.Invoker;

/**
 *
 * FallbackFilter
 *
 * @author zhanxiaoyong@yiwugou.com
 *
 * @since 2017年6月16日 上午10:35:54
 */
public class FallbackFilter implements Filter {
    @Override
    public Object invoke(Invoker invoker, Object[] args) throws Throwable {
        try {
            Object obj = invoker.invoke(args);
            return obj;
        } catch (Exception e) {
            Class<?> interfaceClass = invoker.getMethod().getDeclaringClass();
            RequestUrl requestUrl = interfaceClass.getAnnotation(RequestUrl.class);
            if (void.class.equals(requestUrl.fallback())) {
                throw e;
            }
            try {
                Object fallback = ClassFactory.newInstance(requestUrl.fallback());
                return invoker.getMethod().invoke(fallback, args);
            } catch (Exception ex) {
                throw e;
            }
        }
    }

}
