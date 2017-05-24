package com.yiwugou.homer.core.loadbalance;

import com.yiwugou.homer.core.Request;
import com.yiwugou.homer.core.Response;
import com.yiwugou.homer.core.client.Client;
import com.yiwugou.homer.core.constant.RequestDefault;
import com.yiwugou.homer.core.enums.MethodEnum;
import com.yiwugou.homer.core.server.Server;

public class HttpPing implements Ping {
    private Client client;

    public HttpPing(Client client) {
        this.client = client;
    }

    @Override
    public boolean isAlive(Server server) {
        Request request = Request.builder().method(MethodEnum.GET).url(server.getHostPort())
                .connectTimeout(RequestDefault.CONNECT_TIMEOUT).readTimeout(RequestDefault.READ_TIMEOUT).build();
        try {
            Response response = this.client.execute(request);
            return response != null;
        } catch (Exception e) {
            // e.printStackTrace();
        }
        return false;
    }

}
