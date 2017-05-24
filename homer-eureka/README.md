
# a rest client like [feign](https://github.com/OpenFeign/feign)

```java

@RequestUrl("ms-redis-service")
public interface FooService {
    @RequestMapping("foo")
    @RequestConfig(loadBalance = LoadBalanceEnum.ROUND_ROBIN)
    public String foo();
}



FooService fooService = Homer.builder().instanceCreater(new EurekaInstanceCreater()).proxy(FooService.class);

```



## use properties file
homer-eureka.properties

```properties

eureka.serviceUrl.default=http://127.0.0.1:8761/eureka

com.yiwugou.homer.eureka.test.BarService.eurekaServiceId=ms-redis-service

```

```java

@RequestUrl
public interface BarService {
    @RequestMapping("foo")
    public String foo();
}


InputStream in = this.getClass().getClassLoader().getResourceAsStream("homer-eureka.properties");
Properties properties = new Properties();
properties.load(in);
CommonUtils.close(in);
this.barService = Homer.builder().instanceCreater(new EurekaInstanceCreater(properties))
        .configLoader(new PropertiesConfigLoader(properties)).proxy(BarService.class);

```