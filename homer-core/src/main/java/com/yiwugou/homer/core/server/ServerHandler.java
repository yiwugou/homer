package com.yiwugou.homer.core.server;

import java.util.List;

import com.yiwugou.homer.core.Server;

public interface ServerHandler {
    List<Server> getUpServers();

    List<Server> getDownServers();

    ServerCheck getServerCheck();

}
