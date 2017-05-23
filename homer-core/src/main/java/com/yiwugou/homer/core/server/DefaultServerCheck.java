package com.yiwugou.homer.core.server;

import com.yiwugou.homer.core.Server;
import com.yiwugou.homer.core.exception.HomerException;

public class DefaultServerCheck extends AbstractServerCheck {

    private ServerHandler serverHandler;

    public DefaultServerCheck(ServerHandler serverHandler) {
        this.serverHandler = serverHandler;
    }

    @Override
    public void serverDown(Server downServer, Exception e) {
        this.down(downServer);
        this.loopIfDown(downServer);
        throw new HomerException(e);
    }

    private void down(Server server) {
        if (server != null) {
            server.setAlive(false);
            this.serverHandler.getUpServers().remove(server);
            this.serverHandler.getDownServers().add(server);
        }
    }

    @Override
    protected void up(Server server) {
        if (server != null) {
            server.setAlive(true);
            server.initRetry();
            this.serverHandler.getUpServers().add(server);
            this.serverHandler.getDownServers().remove(server);
        }
    }

    @Override
    public void serverUp(Server upServer) {

    }
}