package com.yiwugou.homer.core.factory;

import java.lang.reflect.Method;
import java.util.List;

import com.yiwugou.homer.core.client.Client;
import com.yiwugou.homer.core.codec.Decoder;
import com.yiwugou.homer.core.config.MethodOptions;
import com.yiwugou.homer.core.hanlder.DefaultMethodHandler;
import com.yiwugou.homer.core.hanlder.EqualsMethodHandler;
import com.yiwugou.homer.core.hanlder.HashCodeMethodHandler;
import com.yiwugou.homer.core.hanlder.MethodHandler;
import com.yiwugou.homer.core.hanlder.ProxyMethodHandler;
import com.yiwugou.homer.core.hanlder.ToStringMethodHandler;
import com.yiwugou.homer.core.interceptor.RequestInterceptor;
import com.yiwugou.homer.core.util.CommonUtils;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MethodHandlerFactory {
    private Client client;
    private Method method;
    private MethodOptions methodOptions;
    private List<RequestInterceptor> requestInterceptors;
    private Decoder decoder;

    public MethodHandler create() {
        if ("equals".equals(this.method.getName())) {
            return new EqualsMethodHandler(this.method);
        } else if ("hashCode".equals(this.method.getName())) {
            return new HashCodeMethodHandler(this.method);
        } else if ("toString".equals(this.method.getName())) {
            return new ToStringMethodHandler(this.method);
        } else if (CommonUtils.isDefaultMethod(this.method)) {
            return new DefaultMethodHandler(this.method);
        } else {
            return new ProxyMethodHandler(this.client, this.method, this.methodOptions, this.requestInterceptors,
                    this.decoder);
        }
    }
}
