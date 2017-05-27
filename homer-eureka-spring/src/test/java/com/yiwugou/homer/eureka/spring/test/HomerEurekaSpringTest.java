package com.yiwugou.homer.eureka.spring.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/*.xml" })
public class HomerEurekaSpringTest {

    @Resource
    private DemoService demoService;

    @Test
    public void test1() {
        for (int i = 1; i <= 10000; i++) {
            String after = this.demoService.foo();
            System.err.println(after);
        }
    }

}
