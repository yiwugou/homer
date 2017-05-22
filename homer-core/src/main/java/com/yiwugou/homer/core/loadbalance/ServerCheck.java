package com.yiwugou.homer.core.loadbalance;

import com.yiwugou.homer.core.Server;
import com.yiwugou.homer.core.config.MethodOptions;

public interface ServerCheck {
    public void serverUp(Server upServer, MethodOptions methodOptions);

    public void serverDown(Server downServer, MethodOptions methodOptions, Exception e);
}
