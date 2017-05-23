package com.yiwugou.homer.eureka.server;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.EurekaInstanceConfig;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.EurekaClientConfig;
import com.yiwugou.homer.core.Server;
import com.yiwugou.homer.core.annotation.RequestUrl;
import com.yiwugou.homer.core.config.ConfigLoader;
import com.yiwugou.homer.core.server.ServerCheck;
import com.yiwugou.homer.core.server.ServerHandler;
import com.yiwugou.homer.eureka.HomerEurekaClientConfig;
import com.yiwugou.homer.eureka.HomerEurekaInstanceConfig;

import lombok.Getter;

public class EurekaServerHandler implements ServerHandler {
    @Getter
    private EurekaClient eurekaClient;
    @Getter
    private String serviceId;
    @Getter
    private Map<Server, InstanceInfo> instanceInfoMap = new ConcurrentHashMap<>();
    @Getter
    private ServerCheck serverCheck;

    private List<Server> downServices = new CopyOnWriteArrayList<>();

    public EurekaServerHandler(RequestUrl requestUrl, Class<?> clazz, ConfigLoader configLoader) {
        this.initServers(requestUrl, clazz, configLoader);
        this.serverCheck = new EurekaServerCheck(this);
    }

    @Override
    public List<Server> getUpServers() {
        List<InstanceInfo> ins = this.eurekaClient.getInstancesByVipAddress(this.serviceId, false);
        List<Server> servers = new CopyOnWriteArrayList<>();

        for (InstanceInfo in : ins) {
            Server server = this.instanceInfoToServer(in);
            servers.add(server);
            this.instanceInfoMap.put(server, in);
        }
        servers.removeAll(this.downServices);
        return servers;
    }

    private Server instanceInfoToServer(InstanceInfo instanceInfo) {
        String hostPort = instanceInfo.getHostName() + ":" + instanceInfo.getPort();
        Server server = new Server(hostPort);
        server.setAlive(true);
        return server;
    }

    private void initServers(RequestUrl requestUrl, Class<?> clazz, ConfigLoader configLoader) {
        String[] serviceIds = requestUrl.value();
        this.serviceId = configLoader.loader(clazz.getName() + ConfigLoader.EUREKA_SERVICE_ID, serviceIds[0]);
        this.initEurekaClient();
    }

    @Override
    public List<Server> getDownServers() {
        return this.downServices;
    }

    private void initEurekaClient() {
        ApplicationInfoManager.OptionalArgs options = null;
        EurekaInstanceConfig instanceConfig = new HomerEurekaInstanceConfig();
        ApplicationInfoManager applicationInfoManager = new ApplicationInfoManager(instanceConfig, options);
        EurekaClientConfig clientConfig = new HomerEurekaClientConfig();
        this.eurekaClient = new DiscoveryClient(applicationInfoManager, clientConfig);
    }

}
