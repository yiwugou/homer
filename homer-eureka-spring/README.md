
# use homer-eureka with spring

## homer-eureka-spring.xml

```xml

    <bean class="com.yiwugou.homer.eureka.spring.HomerEurekaScanBean">
        <property name="package" value="com.yiwugou.homer.eureka.spring.test" />
        <property name="namespace" value="yournamespace" />
        <property name="propertiesLoaderSupport">
            <bean class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
                <property name="ignoreResourceNotFound" value="true" />
                <property name="ignoreUnresolvablePlaceholders" value="true" />
                <property name="locations">
                    <list>
                        <value>classpath*:homer-eureka-spring.properties</value>
                    </list>
                </property>
            </bean>
        </property>
    </bean>

```




## homer-eureka-spring.properties

```properties

yournamespace.serviceUrl.default=http://127.0.0.1:8761/eureka

com.yiwugou.homer.eureka.spring.test.DemoService.eurekaServiceId=ms-redis-service
com.yiwugou.homer.eureka.spring.test.property.DemoService.execute=100
com.yiwugou.homer.eureka.spring.test.property.DemoService.foo.execute=10


```


```java

    @Resource
    private DemoService demoService;

```