package com.yiwugou.homer.core.server;

import java.util.List;

/**
 *
 * ServerHandler
 * 
 * @author zhanxiaoyong@yiwugou.com
 *
 * @since 2017年7月10日 下午3:15:27
 */
public interface ServerHandler {
    List<Server> getUpServers();

    List<Server> getDownServers();

    ServerCheck getServerCheck();

}
