package com.yiwugou.homer.core.loadbalance;

import com.yiwugou.homer.core.server.Server;

/**
 *
 * Ping
 * 
 * @author zhanxiaoyong@yiwugou.com
 *
 * @since 2017年7月10日 下午3:14:50
 */
public interface Ping {
    boolean isAlive(Server server);
}
