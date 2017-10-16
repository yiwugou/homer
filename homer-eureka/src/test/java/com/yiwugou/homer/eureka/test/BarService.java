package com.yiwugou.homer.eureka.test;

import com.yiwugou.homer.core.annotation.RequestMapping;
import com.yiwugou.homer.core.annotation.RequestUrl;

/**
 *
 * BarService
 *
 * @author zhanxiaoyong@yiwugou.com
 *
 * @since 2017年10月16日 下午4:21:08
 */
@RequestUrl
public interface BarService {
    @RequestMapping("foo")
    public String foo();
}
