package com.yiwugou.homer.core.loadbalance;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.yiwugou.homer.core.Server;
import com.yiwugou.homer.core.client.HttpClient;
import com.yiwugou.homer.core.config.MethodOptions;
import com.yiwugou.homer.core.exception.HomerException;

public class DefaultServerCheck implements ServerCheck {
    private Ping ping = new HttpPing(new HttpClient());

    private final ScheduledExecutorService EXECUTOR = Executors.newScheduledThreadPool(10);

    @Override
    public void serverDown(Server downServer, MethodOptions methodOptions, Exception e) {
        this.down(downServer, methodOptions);
        this.loopIfDown(downServer, methodOptions);
        throw new HomerException(e);
    }

    private void loopIfDown(Server downServer, MethodOptions methodOptions) {
        long delay = (long) (Math.pow(1.5, downServer.getRetry()) * 10);
        if (delay > 3600) {
            delay = 3600;
        } else {
            downServer.addRetry();
        }
        this.EXECUTOR.schedule(new Runnable() {
            @Override
            public void run() {
                boolean isAlive = DefaultServerCheck.this.ping.isAlive(downServer);
                if (isAlive) {
                    DefaultServerCheck.this.up(downServer, methodOptions);
                } else {
                    DefaultServerCheck.this.loopIfDown(downServer, methodOptions);
                }
            }
        }, delay, TimeUnit.SECONDS);
    }

    private void down(Server server, MethodOptions methodOptions) {
        if (server != null) {
            server.setAlive(false);
            methodOptions.getUpServers().remove(server);
            methodOptions.getDownServers().add(server);
        }
    }

    private void up(Server server, MethodOptions methodOptions) {
        if (server != null) {
            server.setAlive(true);
            server.initRetry();
            methodOptions.getUpServers().add(server);
            methodOptions.getDownServers().remove(server);
        }
    }

    @Override
    public void serverUp(Server upServer, MethodOptions methodOptions) {

    }
}
