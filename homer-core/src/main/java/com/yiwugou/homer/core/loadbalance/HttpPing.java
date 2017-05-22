package com.yiwugou.homer.core.loadbalance;

import java.io.IOException;

import com.yiwugou.homer.core.Request;
import com.yiwugou.homer.core.Response;
import com.yiwugou.homer.core.Server;
import com.yiwugou.homer.core.annotation.RequestConfig;
import com.yiwugou.homer.core.client.Client;
import com.yiwugou.homer.core.enums.MethodEnum;

public class HttpPing implements Ping {
    private Client client;

    public HttpPing(Client client) {
        this.client = client;
    }

    @Override
    public boolean isAlive(Server server) {
        Request request = new Request(MethodEnum.GET, server.getHostPort(), null, null,
                RequestConfig.Default.CONNECT_TIMEOUT, RequestConfig.Default.READ_TIMEOUT);
        try {
            Response response = this.client.execute(request);
            return response.getCode() == 200;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
