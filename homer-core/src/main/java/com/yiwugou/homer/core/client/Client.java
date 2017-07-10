package com.yiwugou.homer.core.client;

import java.io.IOException;

import com.yiwugou.homer.core.Request;
import com.yiwugou.homer.core.Response;

/**
 *
 * Client
 *
 * @author zhanxiaoyong@yiwugou.com
 *
 * @since 2017年7月10日 下午3:09:34
 */
public interface Client {
    Response execute(Request request) throws IOException;
}
