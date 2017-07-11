package com.yiwugou.homer.core.interceptor;

import com.yiwugou.homer.core.Request;
import com.yiwugou.homer.core.Response;

/**
 *
 * RequestInterceptor
 *
 * @author zhanxiaoyong@yiwugou.com
 *
 * @since 2017年7月10日 下午3:08:31
 */
public interface Interceptor {
    void requestApply(Request request);

    void responseApply(Response response);
}
