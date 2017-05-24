package com.yiwugou.homer.core.server;

import java.io.IOException;

public interface ServerCheck {
    public void serverUp(Server upServer);

    public void serverDown(Server downServer, IOException e);
}
