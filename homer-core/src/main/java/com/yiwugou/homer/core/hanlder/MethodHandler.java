package com.yiwugou.homer.core.hanlder;

import com.yiwugou.homer.core.enums.MethodModelEnum;

/**
 *
 * MethodHandler
 *
 * @author zhanxiaoyong@yiwugou.com
 *
 * @since 2017年7月10日 下午3:08:20
 */
public interface MethodHandler {

    Object invoke(Object[] args) throws Throwable;

    MethodModelEnum getMethodModel();

}
