package com.yiwugou.homer.core.server;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class Server {

    @Getter
    @Setter
    private String hostPort;

    @Getter
    @Setter
    private volatile boolean alive = true;

    @Getter
    private int retry = 0;

    @Setter
    private Exception exception;

    public void addRetry() {
        ++this.retry;
    }

    public void initRetry() {
        this.retry = 0;
    }

    public Server(String hostPort) {
        this.hostPort = hostPort;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.hostPort == null) ? 0 : this.hostPort.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Server other = (Server) obj;
        if (this.hostPort == null) {
            if (other.hostPort != null) {
                return false;
            }
        } else if (!this.hostPort.equals(other.hostPort)) {
            return false;
        }
        return true;
    }
}
