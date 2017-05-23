package com.yiwugou.homer.core.server;

import com.yiwugou.homer.core.Server;

public interface ServerCheck {
    public void serverUp(Server upServer);

    public void serverDown(Server downServer, Exception e);
}
