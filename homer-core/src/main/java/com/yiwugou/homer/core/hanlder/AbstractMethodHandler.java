package com.yiwugou.homer.core.hanlder;

import com.yiwugou.homer.core.enums.MethodModelEnum;

public abstract class AbstractMethodHandler implements MethodHandler {

    @Override
    public MethodModelEnum getMethodModel() {
        return MethodModelEnum.NORMAL;
    }

}
