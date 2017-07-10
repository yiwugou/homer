package com.yiwugou.homer.core.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * ClassFactory
 * 
 * @author zhanxiaoyong@yiwugou.com
 *
 * @since 2017年7月10日 下午3:12:24
 */
public class ClassFactory {
    private static final Map<Class<?>, Object> CLASS_MAP = new ConcurrentHashMap<>();

    public static final <T> T newInstance(Class<T> clazz) throws Exception {
        T obj = (T) CLASS_MAP.get(clazz);
        if (obj != null) {
            return obj;
        }
        obj = clazz.newInstance();
        CLASS_MAP.put(clazz, obj);
        return obj;
    }

    public static final <T> T newInstance(Class<T> clazz, T defaultObject) {
        try {
            T obj = ClassFactory.newInstance(clazz);
            return obj;
        } catch (Exception e) {
            return defaultObject;
        }
    }

    public static final <T> T newInstance(Class<T> clazz, boolean isSingleton) throws Exception {
        if (isSingleton) {
            return ClassFactory.newInstance(clazz);
        }
        return clazz.newInstance();
    }

    public static final <T> T newInstance(Class<T> clazz, boolean isSingleton, T defaultObject) throws Exception {
        try {
            T obj = ClassFactory.newInstance(clazz, isSingleton);
            return obj;
        } catch (Exception e) {
            return defaultObject;
        }
    }
}
