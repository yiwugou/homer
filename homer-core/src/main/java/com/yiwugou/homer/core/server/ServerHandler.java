package com.yiwugou.homer.core.server;

import java.util.List;

public interface ServerHandler {
    List<Server> getUpServers();

    List<Server> getDownServers();

    ServerCheck getServerCheck();

}
