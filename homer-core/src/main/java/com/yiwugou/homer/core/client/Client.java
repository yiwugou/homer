package com.yiwugou.homer.core.client;

import java.io.IOException;

import com.yiwugou.homer.core.Request;
import com.yiwugou.homer.core.Response;
import com.yiwugou.homer.core.config.MethodOptions;

public interface Client {
    Response execute(Request request, MethodOptions methodOptions) throws IOException;
}
