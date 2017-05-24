package com.yiwugou.homer.core.server;

public interface ServerCheck {
    public void serverUp(Server upServer);

    public void serverDown(Server downServer, Exception e);
}
