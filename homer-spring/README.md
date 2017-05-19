
# use homer with spring

## homer-spring.xml

```xml

    <!-- this package not use config file -->
    <bean class="com.yiwugou.homer.spring.HomerScanBean">
        <property name="package" value="com.yiwugou.homer.spring.test.service" />
    </bean>


    <!-- this package use config file -->
    <bean class="com.yiwugou.homer.spring.HomerScanBean">
        <property name="package" value="com.yiwugou.homer.spring.test.property" />
        <property name="propertiesLoaderSupport">
            <bean class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
                <property name="ignoreResourceNotFound" value="true" />
                <property name="ignoreUnresolvablePlaceholders" value="true" />
                <property name="locations">
                    <list>
                        <value>classpath*:homer-spring.properties</value>
                        <value>file:/home/yiwu/conf/product-common.properties</value>
                    </list>
                </property>
            </bean>
        </property>
    </bean>

```




## homer-spring.properties

```properties

com.yiwugou.homer.spring.test.property.PropertyService.url=http://127.0.0.1:8762;http://127.0.0.1:8763;http://127.0.0.1:8764;
com.yiwugou.homer.spring.test.property.PropertyService.execute=100
com.yiwugou.homer.spring.test.property.PropertyService.foo.execute=10


```


```java

    @Resource
    private DemoService demoService;

    @Resource
    private PropertyService propertyService;

```