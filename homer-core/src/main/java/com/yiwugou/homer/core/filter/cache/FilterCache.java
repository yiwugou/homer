package com.yiwugou.homer.core.filter.cache;

import java.io.Serializable;
import java.lang.reflect.Method;

public interface FilterCache {
    Serializable get(Method method, Object[] args);

    boolean set(Method method, Object[] args, Serializable value, long expTime);
}
