
# a rest client visit eureka

## use default properties.

eureka-client.properties

```properties

eureka.serviceUrl.default=http://127.0.0.1:8761/eureka


```


```java

@RequestUrl("ms-redis-service")
public interface FooService {
    @RequestMapping("foo")
    @RequestConfig(loadBalance = LoadBalanceEnum.ROUND_ROBIN)
    public String foo();
}



FooService fooService = Homer.builder().instanceCreater(new EurekaInstanceCreater()).proxy(FooService.class);

```



## use custom properties file

homer-eureka.properties

```properties

xiaoyong.serviceUrl.default=http://127.0.0.1:8761/eureka

com.yiwugou.homer.eureka.test.BarService.eurekaServiceId=ms-redis-service

```

```java

@RequestUrl
public interface BarService {
    @RequestMapping("foo")
    public String foo();
}

String namespace = "xiaoyong"; // you custom namespace
Properties properties = loader_from_file("homer-eureka.properties");

this.barService = Homer.builder().instanceCreater(new EurekaInstanceCreater(namespace, properties))
        .configLoader(new PropertiesConfigLoader(properties)).proxy(BarService.class);

```