package com.yiwugou.homer.core.invoker;

/**
 *
 * Function like jdk8 function
 *
 * @author zhanxiaoyong@yiwugou.com
 *
 * @since 2017年7月10日 下午3:14:07
 */
public interface Function<ARG, RETURN> {
    RETURN apply(ARG arg) throws Throwable;
}
