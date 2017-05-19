package com.yiwugou.homer.core.hanlder;

import java.lang.reflect.Method;

import com.yiwugou.homer.core.util.CommonUtils;

public class ToStringMethodHandler implements MethodHandler {
    private Class<?> clazz;

    private Method[] methods;

    public ToStringMethodHandler(Method method) {
        this.clazz = method.getDeclaringClass();
        this.methods = this.clazz.getMethods();
    }

    @Override
    public Object invoke(Object[] args) throws Throwable {
        return "class is " + this.clazz.getName() + "; methods is " + CommonUtils.joinToString(",", this.methods);
    }

}
