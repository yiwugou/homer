package com.yiwugou.homer.core.hanlder;

import java.lang.reflect.Method;
import java.util.List;

import com.yiwugou.homer.core.Request;
import com.yiwugou.homer.core.Response;
import com.yiwugou.homer.core.Server;
import com.yiwugou.homer.core.client.Client;
import com.yiwugou.homer.core.codec.Decoder;
import com.yiwugou.homer.core.config.MethodOptions;
import com.yiwugou.homer.core.exception.ResponseException;
import com.yiwugou.homer.core.exception.ServerException;
import com.yiwugou.homer.core.factory.RequestFactory;
import com.yiwugou.homer.core.interceptor.RequestInterceptor;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProxyMethodHandler implements MethodHandler {

    private Client client;
    private Method method;
    private MethodOptions methodOptions;
    private List<RequestInterceptor> requestInterceptors;
    private Decoder decoder;

    @Override
    public Object invoke(Object[] args) throws Throwable {
        while (true) {
            Server server = this.methodOptions.getLoadBalance()
                    .choose(this.methodOptions.getServerHandler().getUpServers(), this.method, args);
            if (server == null) {
                throw new ServerException("no server is available");
            }
            try {
                Request request = new RequestFactory(this.method, this.methodOptions, args, server).create();
                if (this.requestInterceptors != null) {
                    for (RequestInterceptor requestInterceptor : this.requestInterceptors) {
                        requestInterceptor.apply(request);
                    }
                }
                Object obj = this.executeAndDecode(request);
                this.methodOptions.getServerHandler().getServerCheck().serverUp(server);
                return obj;
            } catch (Exception e) {
                this.methodOptions.getServerHandler().getServerCheck().serverDown(server, e);
            }
        }

    }

    private Object executeAndDecode(Request request) throws Throwable {
        int retry = this.methodOptions.getRetry();
        while (true) {
            try {
                Response response = this.client.execute(request);
                if (response.getCode() > 400) {
                    throw new ResponseException(response);
                }
                return this.decoder.decode(response, this.method.getReturnType());
            } catch (Exception e) {
                if (--retry < 0) {
                    throw e;
                }
            }
        }
    }
}
