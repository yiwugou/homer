package com.yiwugou.homer.core.util;

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
}
