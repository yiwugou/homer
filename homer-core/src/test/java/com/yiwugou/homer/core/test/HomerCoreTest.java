package com.yiwugou.homer.core.test;

import org.junit.Before;
import org.junit.Test;

import com.yiwugou.homer.core.Homer;

public class HomerCoreTest {
    private DemoService demoService;

    @Before
    public void init() {
        this.demoService = Homer.instance().build(DemoService.class);
    }

    @Test
    public void test1() {
        String after = this.demoService.foo();
        System.err.println(after);

        String after2get = this.demoService.foo2Get("getusername", "getpassword");
        System.err.println(after2get);

        String after2post = this.demoService.foo2Post("postusername", "postpassword");
        System.err.println(after2post);

        System.err.println("test1 success!");
    }

    @Test
    public void loadBalanceTest() {
        int index = 10000;
        while (index-- > 0) {
            String after = this.demoService.foo();
            // System.err.println(after);
        }
    }

    public static void main(String[] args) throws Exception {
        HomerCoreTest test = new HomerCoreTest();
        test.init();
        long start = System.currentTimeMillis();
        for (int i = 1; i <= 100; i++) {
            new Thread() {
                @Override
                public void run() {
                    test.loadBalanceTest();
                }
            }.start();
        }
        test.loadBalanceTest();
        System.err.println("running time:" + (System.currentTimeMillis() - start));
    }

}
