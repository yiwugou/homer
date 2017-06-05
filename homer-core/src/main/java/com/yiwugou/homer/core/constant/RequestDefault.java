package com.yiwugou.homer.core.constant;

import com.yiwugou.homer.core.loadbalance.LoadBalance;
import com.yiwugou.homer.core.loadbalance.RandomLoadBalance;

public class RequestDefault {
    public static final int RETRY = 0;
    public static final int EXECUTE = 1000;
    public static final int ACTIVE = 100;
    public static final long CACHE = 0;
    public static final Boolean MOCK = true;
    public static final int CONNECT_TIMEOUT = 1000;
    public static final int READ_TIMEOUT = 3000;
    public static final Class<? extends LoadBalance> LOAD_BALANCE = RandomLoadBalance.class;
}