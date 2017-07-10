package com.yiwugou.homer.core.filter.cache;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.yiwugou.homer.core.util.CommonUtils;

/**
 *
 * JvmFilterCache
 * 
 * @author zhanxiaoyong@yiwugou.com
 *
 * @since 2017年7月10日 下午3:13:24
 */
@Deprecated
public class JvmFilterCache implements FilterCache {

    private Map<Method, Cache<String, Serializable>> data = new ConcurrentHashMap<>();

    @Override
    public Serializable get(Method method, Object[] args) {
        Cache<String, Serializable> cache = this.data.get(method);
        if (cache != null) {
            return cache.getIfPresent(CommonUtils.joinToString(",", args));
        }
        return null;
    }

    @Override
    public boolean set(Method method, Object[] args, Serializable value, long expTime) {
        Cache<String, Serializable> cache = this.data.get(method);
        if (cache == null) {
            cache = CacheBuilder.newBuilder().expireAfterWrite(expTime, TimeUnit.MILLISECONDS).build();
            this.data.put(method, cache);
        }
        cache.put(args == null ? "" : CommonUtils.joinToString(",", args), value);
        return true;
    }

}
