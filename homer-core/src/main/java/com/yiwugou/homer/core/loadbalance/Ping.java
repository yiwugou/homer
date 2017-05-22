package com.yiwugou.homer.core.loadbalance;

import com.yiwugou.homer.core.Server;

public interface Ping {
    boolean isAlive(Server server);
}
