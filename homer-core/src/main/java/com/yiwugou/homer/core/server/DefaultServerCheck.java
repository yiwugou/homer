package com.yiwugou.homer.core.server;

import java.io.IOException;

public class DefaultServerCheck extends AbstractServerCheck {

    private ServerHandler serverHandler;

    public DefaultServerCheck(ServerHandler serverHandler) {
        this.serverHandler = serverHandler;
    }

    @Override
    public void serverDown(Server downServer, IOException e) {
        this.down(downServer);
        this.loopIfDown(downServer);
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
