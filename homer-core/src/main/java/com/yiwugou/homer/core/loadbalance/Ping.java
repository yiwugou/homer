package com.yiwugou.homer.core.loadbalance;

import com.yiwugou.homer.core.server.Server;

public interface Ping {
    boolean isAlive(Server server);
}
