package com.yiwugou.homer.core.util;

/**
 *
 * AssertUtils
 * 
 * @author zhanxiaoyong@yiwugou.com
 *
 * @since 2017年7月10日 下午3:15:32
 */
public class AssertUtils {
    public static <T extends Object> T notNull(T obj, String name) {
        if (obj == null) {
            throw new IllegalArgumentException(name + " must not be null!");
        }
        return obj;
    }

    public static String hasTest(String str, String name) {
        if (str == null || "".equals(str.trim())) {
            throw new IllegalArgumentException(name + " must has text!");
        }
        return str;
    }

    public static <T> T[] notEmpty(T[] ts, String name) {
        if (ts == null || ts.length == 0) {
            throw new IllegalArgumentException(name + " must not be empty!");
        }
        return ts;
    }
}
