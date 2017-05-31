package com.yiwugou.homer.core.interceptor;

import com.yiwugou.homer.core.Request;
import com.yiwugou.homer.core.constant.Constants;
import com.yiwugou.homer.core.util.Base64Utils;

public class BasicAuthRequestInterceptor implements RequestInterceptor {

    private final String headerValue;

    public BasicAuthRequestInterceptor(String username, String password) {
        this.headerValue = "Basic " + base64Encode((username + ":" + password).getBytes(Constants.UTF_8));
    }

    private static String base64Encode(byte[] bytes) {
        return Base64Utils.encode(bytes);
    }

    @Override
    public void apply(Request request) {
        request.addHeader("Authorization", this.headerValue);
    }
}
