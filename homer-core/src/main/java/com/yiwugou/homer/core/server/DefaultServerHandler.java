package com.yiwugou.homer.core.server;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.yiwugou.homer.core.Server;
import com.yiwugou.homer.core.annotation.RequestUrl;
import com.yiwugou.homer.core.config.ConfigLoader;
import com.yiwugou.homer.core.constant.Constants;
import com.yiwugou.homer.core.util.CommonUtils;

import lombok.Getter;

public class DefaultServerHandler implements ServerHandler {
    @Getter
    private List<Server> upServers = new CopyOnWriteArrayList<>();
    @Getter
    private List<Server> downServers = new CopyOnWriteArrayList<>();
    @Getter
    private ServerCheck serverCheck;

    public DefaultServerHandler(RequestUrl requestUrl, Class<?> clazz, ConfigLoader configLoader) {
        String[] requestUrls = requestUrl.value();
        String url = configLoader.loader(clazz.getName() + ConfigLoader.URL,
                CommonUtils.joinToString(Constants.URL_SEPARATOR, requestUrls));

        String urls[] = url.split(Constants.URL_SEPARATOR);
        Server server = null;

        for (String u : urls) {
            if (CommonUtils.hasTest(u)) {
                if (u.endsWith("/")) {
                    u = u.substring(0, u.length() - 1);
                }
                server = new Server(u);
                this.upServers.add(server);
            }
        }
        this.serverCheck = new DefaultServerCheck(this);

    }

}
