package com.yiwugou.homer.core.client;

import java.io.IOException;

import com.yiwugou.homer.core.Request;
import com.yiwugou.homer.core.Response;

public interface Client {
    Response execute(Request request) throws IOException;
}
