package com.yiwugou.homer.core.hanlder;

import com.yiwugou.homer.core.enums.MethodModelEnum;

public interface MethodHandler {

    Object invoke(Object[] args) throws Throwable;

    MethodModelEnum getMethodModel();

}
