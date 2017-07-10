package com.yiwugou.homer.core.loadbalance;

import java.lang.reflect.Method;
import java.util.List;

import com.yiwugou.homer.core.server.Server;

/**
 *
 * LoadBalance
 * 
 * @author zhanxiaoyong@yiwugou.com
 *
 * @since 2017年7月10日 下午3:14:46
 */
public interface LoadBalance {
    Server choose(List<Server> servers, Method method, Object[] args);
}
