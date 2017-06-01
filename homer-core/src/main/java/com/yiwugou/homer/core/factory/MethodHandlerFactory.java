package com.yiwugou.homer.core.factory;

import java.lang.reflect.Method;

import com.yiwugou.homer.core.Homer;
import com.yiwugou.homer.core.hanlder.DefaultMethodHandler;
import com.yiwugou.homer.core.hanlder.EqualsMethodHandler;
import com.yiwugou.homer.core.hanlder.HashCodeMethodHandler;
import com.yiwugou.homer.core.hanlder.MethodHandler;
import com.yiwugou.homer.core.hanlder.ProxyMethodHandler;
import com.yiwugou.homer.core.hanlder.ToStringMethodHandler;
import com.yiwugou.homer.core.util.CommonUtils;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MethodHandlerFactory {
    private Class<?> clazz;
    private Homer homer;
    private Method method;

    public MethodHandler create() {
        if ("equals".equals(this.method.getName())) {
            return new EqualsMethodHandler(this.clazz);
        } else if ("hashCode".equals(this.method.getName())) {
            return new HashCodeMethodHandler(this.clazz);
        } else if ("toString".equals(this.method.getName())) {
            return new ToStringMethodHandler(this.clazz);
        } else if (CommonUtils.isDefaultMethod(this.method)) {
            return new DefaultMethodHandler(this.method);
        } else {
            return new ProxyMethodHandler(this.homer, this.method);
        }
    }

}
