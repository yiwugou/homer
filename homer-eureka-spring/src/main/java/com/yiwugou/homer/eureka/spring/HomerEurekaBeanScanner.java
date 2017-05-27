package com.yiwugou.homer.eureka.spring;

import java.util.Properties;
import java.util.Set;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

import com.yiwugou.homer.core.Homer;
import com.yiwugou.homer.core.config.PropertiesConfigLoader;
import com.yiwugou.homer.eureka.EurekaInstanceCreater;
import com.yiwugou.homer.fastjson.FastjsonDecoder;
import com.yiwugou.homer.spring.HomerFactoryBean;

import lombok.Setter;

/**
 *
 * HomerEurekaBeanScanner
 *
 * @author zhanxiaoyong@yiwugou.com
 *
 * @since 2017年5月27日 上午10:04:43
 */
public class HomerEurekaBeanScanner extends ClassPathBeanDefinitionScanner {

    @Setter
    private Properties properties;

    @Setter
    private String namespace;

    public HomerEurekaBeanScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
    }

    @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> holders = super.doScan(basePackages);
        try {
            for (BeanDefinitionHolder holder : holders) {
                GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();
                String beanClassName = definition.getBeanClassName();
                Class<?> clazz = Class.forName(beanClassName);

                Object obj = Homer.builder().decoder(new FastjsonDecoder())
                        .configLoader(new PropertiesConfigLoader(this.properties))
                        .instanceCreater(new EurekaInstanceCreater(this.namespace, this.properties))
                        .configLoader(new PropertiesConfigLoader(this.properties)).proxy(clazz);
                definition.setBeanClass(HomerFactoryBean.class);
                definition.getPropertyValues().addPropertyValue("mapperInterface", clazz);
                definition.getPropertyValues().addPropertyValue("object", obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return holders;
    }

}