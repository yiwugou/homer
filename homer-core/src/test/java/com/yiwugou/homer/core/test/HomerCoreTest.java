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
        test.propertiesBefore();

        String str = test.demoService.toString();
        System.err.println(str);
        int hashCode = test.demoService.hashCode();
        System.err.println(hashCode);

        long start = System.currentTimeMillis();
        // for (int i = 1; i <= 100; i++) {
        // new Thread() {
        // @Override
        // public void run() {
        // test.test1();
        // }
        // }.start();
        // }
        // String foo = test.demoService.foo();
        // System.err.println(foo);
        test.randomloadBalanceTest();
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

    // @Test
    public void randomloadBalanceTest() {
        int index = 100000000;
        while (index-- > 0) {
            String after = this.demoService.randomLoadBalance();
            System.err.println(after);
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // @Before
    public void propertiesBefore() {
        PropertyDemoService propertyDemoService = Homer.builder()
                .configLoader(new PropertiesFileConfigLoader("homer.properties")).build()
                .proxy(PropertyDemoService.class);
        int index = 100000000;
        while (index-- > 0) {
            String after = propertyDemoService.foo();
            System.err.println(after);
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
