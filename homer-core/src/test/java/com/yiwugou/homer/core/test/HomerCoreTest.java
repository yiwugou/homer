package com.yiwugou.homer.core.test;

import org.junit.Before;
import org.junit.Test;

import com.yiwugou.homer.core.Homer;
import com.yiwugou.homer.core.config.PropertiesFileConfigLoader;

public class HomerCoreTest {
    private DemoService demoService;

    public static void main(String[] args) throws Exception {
        HomerCoreTest test = new HomerCoreTest();
        test.before();

        String str = test.demoService.toString();
        System.err.println(str);
        int hashCode = test.demoService.hashCode();
        System.err.println(hashCode);

        long start = System.currentTimeMillis();
        // for (int i = 1; i <= 100; i++) {
        // new Thread() {
        // @Override
        // public void run() {
        test.test1();
        // }
        // }.start();
        // }
        // test.loadBalanceTest();
        System.err.println("running time:" + (System.currentTimeMillis() - start));
    }

    @Before
    public void before() {
        this.demoService = Homer.builder().build().proxy(DemoService.class);
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

    // @Test
    public void loadBalanceTest() {
        int index = 100000000;
        while (index-- > 0) {
            String after = this.demoService.foo();
            System.err.println(after);
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void propertiesTest() {
        DemoService demoService = Homer.builder().configLoader(new PropertiesFileConfigLoader("homer.properties"))
                .build().proxy(DemoService.class);
    }

}
