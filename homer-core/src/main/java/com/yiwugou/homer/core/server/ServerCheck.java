package com.yiwugou.homer.core.server;

import java.io.IOException;

/**
 *
 * ServerCheck
 * 
 * @author zhanxiaoyong@yiwugou.com
 *
 * @since 2017年7月10日 下午3:15:22
 */
public interface ServerCheck {
    public void serverUp(Server upServer);

    public void serverDown(Server downServer, IOException e);
}
