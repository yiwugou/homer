package com.yiwugou.homer.core.filter.cache;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 *
 * FilterCache
 * 
 * @author zhanxiaoyong@yiwugou.com
 *
 * @since 2017年7月10日 下午3:13:19
 */
public interface FilterCache {
    Serializable get(Method method, Object[] args);

    boolean set(Method method, Object[] args, Serializable value, long expTime);
}
