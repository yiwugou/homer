package com.yiwugou.homer.core.hanlder;

import java.lang.reflect.Method;
import java.util.Arrays;

public class HashCodeMethodHandler extends AbstractMethodHandler {
    private Class<?> clazz;
    private Method[] methods;

    public HashCodeMethodHandler(Class<?> clazz) {
        this.clazz = clazz;
        this.methods = clazz.getMethods();
    }

    @Override
    public Object invoke(Object[] args) throws Throwable {
        return this.hash();
    }

    private int hash() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.clazz == null) ? 0 : this.clazz.hashCode());
        result = prime * result + Arrays.hashCode(this.methods);
        return result;
    }

}
