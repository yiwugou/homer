package com.yiwugou.homer.spring;

import org.springframework.beans.factory.FactoryBean;

import lombok.Setter;

/**
 *
 * HomerFactoryBean
 *
 * @author zhanxiaoyong
 *
 * @since 2017年5月24日 上午10:29:25
 */
public class HomerFactoryBean<T> implements FactoryBean<T> {

    @Setter
    private Class<T> mapperInterface;

    @Setter
    private T object;

    @Override
    public T getObject() throws Exception {
        return this.object;
    }

    @Override
    public Class<T> getObjectType() {
        return this.mapperInterface;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
