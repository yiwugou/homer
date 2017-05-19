package com.yiwugou.homer.spring;

import java.util.Properties;
import java.util.Set;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

import com.yiwugou.homer.core.Homer;
import com.yiwugou.homer.core.config.ConfigLoader;
import com.yiwugou.homer.core.config.PropertiesConfigLoader;

import lombok.Setter;

/**
 *
 * FeignClientScanner
 *
 * @author zhanxiaoyong
 *
 * @since 2017年5月11日 下午4:54:37
 */
public class HomerBeanScanner extends ClassPathBeanDefinitionScanner {

    @Setter
    private Properties properties;

    public HomerBeanScanner(BeanDefinitionRegistry registry) {
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

                Homer homer = Homer.instance();
                if (this.properties != null) {
                    ConfigLoader configLoader = new PropertiesConfigLoader(this.properties);
                    homer.setConfigLoader(configLoader);
                }

                Object obj = homer.build(clazz);
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