package com.yiwugou.homer.core.server;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.yiwugou.homer.core.client.HttpClient;
import com.yiwugou.homer.core.loadbalance.HttpPing;
import com.yiwugou.homer.core.loadbalance.Ping;

/**
 *
 * AbstractServerCheck
 * 
 * @author zhanxiaoyong@yiwugou.com
 *
 * @since 2017年7月10日 下午3:15:03
 */
public abstract class AbstractServerCheck implements ServerCheck {
    protected final ScheduledExecutorService EXECUTOR = Executors.newScheduledThreadPool(10);
    protected Ping ping = new HttpPing(new HttpClient());

    protected abstract void up(Server server);

    protected void loopPing(final Server downServer) {
        long delay = (long) (Math.pow(1.5, downServer.getRetry()) * 10);
        if (delay > 3600) {
            delay = 3600;
        }
        downServer.addRetry();
        this.EXECUTOR.schedule(new Runnable() {
            @Override
            public void run() {
                boolean isAlive = AbstractServerCheck.this.ping.isAlive(downServer);
                if (isAlive) {
                    AbstractServerCheck.this.up(downServer);
                } else {
                    AbstractServerCheck.this.loopPing(downServer);
                }
            }
        }, delay, TimeUnit.SECONDS);
    }
}
