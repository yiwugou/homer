package com.yiwugou.homer.spring.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yiwugou.homer.spring.test.property.PropertyService;
import com.yiwugou.homer.spring.test.service.DemoService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/*.xml" })
public class HomerSpringTest {

    @Resource
    private DemoService demoService;

    @Resource
    private PropertyService propertyService;

    @Test
    public void test1() {
        for (int i = 1; i <= 10000; i++) {
            String after = this.demoService.foo();
            System.err.println(after);
        }
    }

    @Test
    public void test2() {
        for (int i = 1; i <= 10000; i++) {
            String after = this.propertyService.foo();
            System.err.println(after);
        }
    }
}
