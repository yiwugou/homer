package com.yiwugou.homer.eureka;

import com.yiwugou.homer.core.exception.ServerException;
import com.yiwugou.homer.core.server.AbstractServerCheck;
import com.yiwugou.homer.core.server.Server;

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
        // InstanceInfo in =
        // this.eurekaServerHandler.getInstanceInfoMap().get(server);
        // Application application =
        // this.eurekaServerHandler.getEurekaClient().getApplication(in.getAppName());
        // application.addInstance(in);

        server.setAlive(true);
        this.eurekaServerHandler.getDownServers().remove(server);
    }

    @Override
    public void serverDown(Server downServer, Exception e) {
        // InstanceInfo in =
        // this.eurekaServerHandler.getInstanceInfoMap().get(downServer);
        // Application application =
        // this.eurekaServerHandler.getEurekaClient().getApplication(in.getAppName());
        // application.removeInstance(in);

        downServer.setAlive(false);
        this.eurekaServerHandler.getDownServers().add(downServer);
        super.loopIfDown(downServer);
        throw new ServerException(downServer.toString(), e);
    }

}
