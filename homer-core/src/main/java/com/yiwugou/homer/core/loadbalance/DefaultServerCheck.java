package com.yiwugou.homer.core.loadbalance;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.yiwugou.homer.core.Server;
import com.yiwugou.homer.core.config.MethodOptions;

public class DefaultServerCheck implements ServerCheck {

    private final ScheduledExecutorService EXECUTOR = Executors.newScheduledThreadPool(10);

    @Override
    public void serverCheck(Server unavailableServer, MethodOptions methodOptions) {
        this.shutdown(unavailableServer, methodOptions);

        long delay = (long) (Math.pow(1.5, unavailableServer.getRetry()) * 10);
        if (delay > 3600) {
            delay = 3600;
        } else {
            unavailableServer.addRetry();
        }
        this.EXECUTOR.schedule(new Runnable() {
            @Override
            public void run() {
                unavailableServer.setAvailable(true);
                methodOptions.getAvailableServers().add(unavailableServer);
                methodOptions.getUnAvailableServers().remove(unavailableServer);
            }
        }, delay, TimeUnit.SECONDS);
    }

    private void shutdown(Server server, MethodOptions methodOptions) {
        if (server != null) {
            server.setAvailable(false);
            methodOptions.getAvailableServers().remove(server);
            methodOptions.getUnAvailableServers().add(server);
        }
    }
}
