package com.yiwugou.homer.core.server;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.yiwugou.homer.core.annotation.RequestUrl;
import com.yiwugou.homer.core.config.ConfigLoader;
import com.yiwugou.homer.core.constant.Constants;
import com.yiwugou.homer.core.util.CommonUtils;

import lombok.Getter;

/**
 *
 * DefaultServerHandler
 * 
 * @author zhanxiaoyong@yiwugou.com
 *
 * @since 2017年7月10日 下午3:15:12
 */
public class DefaultServerHandler implements ServerHandler {
    @Getter
    private List<Server> upServers = new CopyOnWriteArrayList<>();
    @Getter
    private List<Server> downServers = new CopyOnWriteArrayList<>();
    @Getter
    private ServerCheck serverCheck;

    public DefaultServerHandler(RequestUrl requestUrl, Class<?> clazz, ConfigLoader configLoader) {
        String[] requestUrls = requestUrl.value();
        String allUrl = configLoader.loader(clazz.getName() + ConfigLoader.URL,
                CommonUtils.joinToString(Constants.URL_SEPARATOR, requestUrls));

        String allUrls[] = allUrl.split(Constants.URL_SEPARATOR);
        Server server = null;

        for (String oneAllUrl : allUrls) {
            if (CommonUtils.hasTest(oneAllUrl)) {
                String[] us = oneAllUrl.split(",");
                String url = us[0].trim();
                if (url.endsWith("/")) {
                    url = url.substring(0, url.length() - 1);
                }

                int weight = 1;
                if (us.length > 1) {
                    for (int i = 1; i < us.length; i++) {
                        String[] keyValue = us[i].split("=");
                        String key = keyValue[0].trim();
                        String value = keyValue[1].trim();
                        if ("weight".equalsIgnoreCase(key)) {
                            weight = Integer.parseInt(value);
                        }
                    }
                }

                server = new Server(url, weight);
                this.upServers.add(server);
            }
        }
        this.serverCheck = new DefaultServerCheck(this);

    }

}
