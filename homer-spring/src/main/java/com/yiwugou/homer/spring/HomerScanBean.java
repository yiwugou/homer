package com.yiwugou.homer.spring;

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

/**
 *
 * HomerScanBean
 *
 * @author zhanxiaoyong
 *
 * @since 2017年5月24日 上午10:29:30
 */
public class HomerScanBean implements ApplicationContextAware, BeanDefinitionRegistryPostProcessor {
    private Properties properties;
    private String[] packages;
    private ApplicationContext applicationContext;

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
        HomerBeanScanner scanner = new HomerBeanScanner(registry);
        scanner.setResourceLoader(this.applicationContext);
        scanner.setProperties(this.properties);
        scanner.addIncludeFilter(new AnnotationTypeFilter(RequestUrl.class));
        scanner.scan(this.packages);

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
