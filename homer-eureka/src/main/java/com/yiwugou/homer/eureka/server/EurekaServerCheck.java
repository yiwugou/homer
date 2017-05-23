package com.yiwugou.homer.eureka.server;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.shared.Application;
import com.yiwugou.homer.core.Server;
import com.yiwugou.homer.core.exception.HomerException;
import com.yiwugou.homer.core.server.AbstractServerCheck;

public class EurekaServerCheck extends AbstractServerCheck {

    private EurekaServerHandler eurekaServerHandler;

    public EurekaServerCheck(EurekaServerHandler eurekaServerHandler) {
        this.eurekaServerHandler = eurekaServerHandler;
    }

    @Override
    public void serverUp(Server upServer) {

    }

    @Override
    protected void up(Server server) {
        InstanceInfo in = this.eurekaServerHandler.getInstanceInfoMap().get(server);
        Application application = this.eurekaServerHandler.getEurekaClient().getApplication(in.getAppName());
        application.addInstance(in);
    }

    @Override
    public void serverDown(Server downServer, Exception e) {
        InstanceInfo in = this.eurekaServerHandler.getInstanceInfoMap().get(downServer);
        Application application = this.eurekaServerHandler.getEurekaClient().getApplication(in.getAppName());
        application.removeInstance(in);
        this.loopIfDown(downServer);
        throw new HomerException(e);
    }

}
