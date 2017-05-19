package com.yiwugou.homer.core.loadbalance;

import com.yiwugou.homer.core.Server;
import com.yiwugou.homer.core.config.MethodOptions;

public interface ServerCheck {
    public void serverCheck(Server unavailableServer, MethodOptions methodOptions);
}
