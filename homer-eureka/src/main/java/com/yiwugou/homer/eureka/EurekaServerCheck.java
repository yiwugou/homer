package com.yiwugou.homer.eureka;

import java.io.IOException;

import com.yiwugou.homer.core.server.AbstractServerCheck;
import com.yiwugou.homer.core.server.Server;

/**
 *
 * EurekaServerCheck
 *
 * @author zhanxiaoyong@yiwugou.com
 *
 * @since 2017年10月16日 下午4:20:08
 */
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
        server.setException(null);
        server.setAlive(true);
        server.initRetry();
        this.eurekaServerHandler.getDownServers().remove(server);
    }

    @Override
    public void serverDown(Server downServer, IOException e) {
        // InstanceInfo in =
        // this.eurekaServerHandler.getInstanceInfoMap().get(downServer);
        // Application application =
        // this.eurekaServerHandler.getEurekaClient().getApplication(in.getAppName());
        // application.removeInstance(in);

        downServer.setAlive(false);
        this.eurekaServerHandler.getDownServers().add(downServer);
        super.loopPing(downServer);
    }

}
