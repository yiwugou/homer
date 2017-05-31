package com.yiwugou.homer.core.constant;

import com.yiwugou.homer.core.enums.LoadBalanceEnum;

public class RequestDefault {
    public static final int RETRY = 0;
    public static final int EXECUTE = 1000;
    public static final int ACTIVE = 100;
    public static final long CACHE = 0;
    public static final Boolean MOCK = true;
    public static final int CONNECT_TIMEOUT = 1000;
    public static final int READ_TIMEOUT = 3000;
    public static final String LOAD_BALANCE = LoadBalanceEnum.RANDOM.toString().toLowerCase();
}