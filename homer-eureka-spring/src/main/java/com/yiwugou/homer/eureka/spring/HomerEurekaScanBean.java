package com.yiwugou.homer.eureka.spring;

import java.lang.reflect.Method;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.support.PropertiesLoaderSupport;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import com.yiwugou.homer.core.annotation.RequestUrl;
import com.yiwugou.homer.core.constant.Constants;
import com.yiwugou.homer.core.util.AssertUtils;
import com.yiwugou.homer.eureka.EurekaConstants;

/**
 *
 * HomerEurekaScanBean
 *
 * @author zhanxiaoyong@yiwugou.com
 *
 * @since 2017年5月27日 上午10:04:36
 */
public class HomerEurekaScanBean implements ApplicationContextAware, BeanDefinitionRegistryPostProcessor {
    private ApplicationContext applicationContext;
    private Properties properties;
    private String[] packages;
    private String namespace = EurekaConstants.DEFAULT_CONFIG_NAMESPACE;

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public void setPropertiesLoaderSupport(PropertiesLoaderSupport propertiesLoaderSupport) {
        Method method = ReflectionUtils.findMethod(PropertyPlaceholderConfigurer.class, "mergeProperties");
        ReflectionUtils.makeAccessible(method);
        this.properties = (Properties) ReflectionUtils.invokeMethod(method, propertiesLoaderSupport);
    }

    public void setPackage(String annotationPackage) {
        this.packages = StringUtils.hasText(annotationPackage) ? Constants.COMMA_SPLIT_PATTERN.split(annotationPackage)
                : null;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        AssertUtils.notNull(this.properties, "homer eureka spring properties");
        AssertUtils.notEmpty(this.packages, "homer eureka spring package");
        AssertUtils.hasTest(this.namespace, "homer eureka spring namespace");
        HomerEurekaBeanScanner scanner = new HomerEurekaBeanScanner(registry);
        scanner.setResourceLoader(this.applicationContext);
        scanner.setProperties(this.properties);
        scanner.setNamespace(this.namespace);
        scanner.addIncludeFilter(new AnnotationTypeFilter(RequestUrl.class));
        scanner.scan(this.packages);

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
