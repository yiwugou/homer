package com.yiwugou.homer.core.interceptor;

import com.yiwugou.homer.core.Request;

public interface RequestInterceptor {
    void apply(Request request);
}
