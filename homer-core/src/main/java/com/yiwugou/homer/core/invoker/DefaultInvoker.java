package com.yiwugou.homer.core.invoker;

import java.lang.reflect.Method;

import com.yiwugou.homer.core.config.MethodOptions;
import com.yiwugou.homer.core.hanlder.MethodHandler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class DefaultInvoker implements Invoker {

    @Getter
    private MethodHandler methodHandler;
    @Getter
    private MethodOptions methodOptions;
    @Getter
    private Method method;
    @Getter
    private Object[] args;

    @Override
    public Object invoke() throws Throwable {
        return this.methodHandler.invoke(this.args);
    }

}
