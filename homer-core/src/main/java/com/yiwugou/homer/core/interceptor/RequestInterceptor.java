package com.yiwugou.homer.core.interceptor;

import com.yiwugou.homer.core.Request;

/**
 *
 * RequestInterceptor
 *
 * @author zhanxiaoyong@yiwugou.com
 *
 * @since 2017年7月10日 下午3:08:31
 */
public interface RequestInterceptor {
    void apply(Request request);
}
