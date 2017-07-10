package com.yiwugou.homer.core.hanlder;

import com.yiwugou.homer.core.enums.MethodModelEnum;

/**
 *
 * AbstractMethodHandler
 *
 * @author zhanxiaoyong@yiwugou.com
 *
 * @since 2017年7月10日 下午3:08:15
 */
public abstract class AbstractMethodHandler implements MethodHandler {

    @Override
    public MethodModelEnum getMethodModel() {
        return MethodModelEnum.NORMAL;
    }

}
