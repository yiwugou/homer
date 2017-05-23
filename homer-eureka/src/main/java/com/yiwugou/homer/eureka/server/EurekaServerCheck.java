package com.yiwugou.homer.eureka.server;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.shared.Application;
import com.yiwugou.homer.core.Server;
import com.yiwugou.homer.core.exception.ServerException;
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

        this.eurekaServerHandler.getDownServers().remove(server);
    }

    @Override
    public void serverDown(Server downServer, Exception e) {
        InstanceInfo in = this.eurekaServerHandler.getInstanceInfoMap().get(downServer);
        Application application = this.eurekaServerHandler.getEurekaClient().getApplication(in.getAppName());
        application.removeInstance(in);

        this.eurekaServerHandler.getDownServers().add(downServer);

        super.loopIfDown(downServer);
        throw new ServerException(downServer.toString(), e);
    }

}
