package com.yiwugou.homer.core.test;

public class DemoServiceFallback implements DemoService {
    @Override
    public String foo() {
        return "fallback foo";
    }

    @Override
    public String foo1(String username, String password) {
        return null;
    }

    @Override
    public String foo2Get(String username, String password) {
        return null;
    }

    @Override
    public String foo2Post(String username, String password) {
        return null;
    }

    @Override
    public String randomLoadBalance() {
        // TODO Auto-generated method stub
        return null;
    }
}
