package com.yiwugou.homer.core.invoker;

import java.lang.reflect.Method;

import com.yiwugou.homer.core.config.MethodOptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class DefaultInvoker implements Invoker {
    @Getter
    private MethodOptions methodOptions;
    @Getter
    private Method method;

    private Function<Object[], Object> function;

    @Override
    public Object invoke(Object[] args) throws Throwable {
        return this.function.apply(args);
    }

}
