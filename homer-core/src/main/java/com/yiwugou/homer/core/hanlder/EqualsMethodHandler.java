package com.yiwugou.homer.core.hanlder;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class EqualsMethodHandler extends AbstractMethodHandler {
    private Class<?> clazz;

    private Method[] methods;

    public EqualsMethodHandler(Class<?> clazz) {
        this.clazz = clazz;
        this.methods = clazz.getMethods();
    }

    @Override
    public Object invoke(Object[] args) throws Throwable {
        try {
            Object otherHandler = args.length > 0 && args[0] != null ? Proxy.getInvocationHandler(args[0]) : null;

            return this.equalss(otherHandler);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private boolean equalss(Object obj) {
        Class<?>[] interfaces = obj.getClass().getInterfaces();
        for (Class<?> inter : interfaces) {
            if (inter.equals(this.clazz)) {
                return Arrays.equals(this.methods, inter.getMethods());
            }
        }
        return false;
    }

}
