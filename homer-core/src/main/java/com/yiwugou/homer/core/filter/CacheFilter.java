package com.yiwugou.homer.core.filter;

import java.io.Serializable;

import com.yiwugou.homer.core.filter.cache.FilterCache;
import com.yiwugou.homer.core.invoker.Invoker;

/**
 *
 * CacheFilter
 *
 * @author zhanxiaoyong
 *
 * @since 2017年5月12日 下午4:01:37
 */
public class CacheFilter implements Filter {

    private FilterCache filterCache;

    public CacheFilter(FilterCache filterCache) {
        this.filterCache = filterCache;
    }

    @Override
    public Object invoke(Invoker invoker) throws Throwable {
        Object obj = null;
        if (invoker.getMethodOptions().getCache() > 0) {
            obj = this.filterCache.get(invoker.getMethod(), invoker.getArgs());
            if (obj != null) {
                return obj;
            }
        }

        obj = invoker.invoke();

        if (invoker.getMethodOptions().getCache() > 0) {
            if (obj != null && obj instanceof Serializable) {
                this.filterCache.set(invoker.getMethod(), invoker.getArgs(), (Serializable) obj,
                        invoker.getMethodOptions().getCache());
            }
        }
        return obj;
    }

}
