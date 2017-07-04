
# a rest client like [feign](https://github.com/OpenFeign/feign)

```java

@RequestUrl({ "http://127.0.0.1:8762", "http://127.0.0.1:8763", "http://127.0.0.1:8764" })
@RequestConfig(execute = 10000, active = 10000)
public interface DemoService {

    @RequestMapping("foo")
    @RequestConfig(execute = 10000, active = 10000, loadBalance = RoundRobinLoadBalance.class)
    String foo();

    @RequestMapping(value = "foo1/{username}/{password}", method = MethodEnum.GET)
    String foo1(@RequestParam("username") String username, @RequestParam("password") String password);

    @RequestMapping(value = "foo2", method = MethodEnum.GET)
    String foo2Get(@RequestParam("username") String username, @RequestParam("password") String password);

    @RequestMapping(value = "foo2", method = MethodEnum.POST)
    String foo2Post(@RequestParam("username") String username, @RequestParam("password") String password);
}

DemoService demoService = Homer.builder().proxy(DemoService.class);

```



## properties file
homer.properties

```properties

com.yiwugou.homer.core.test.DemoService.url=http://127.0.0.1:8762;http://127.0.0.1:8763;http://127.0.0.1:8764;
com.yiwugou.homer.core.test.DemoService.execute=100
com.yiwugou.homer.core.test.DemoService.foo.execute=10

```

```java

DemoService demoService = Homer.configLoader(new PropertiesFileConfigLoader("homer.properties")).builder().proxy(DemoService.class);

```


## Future

```java

@RequestUrl({ "http://127.0.0.1:8762" })
public interface DemoService {
    @RequestMapping("foo")
    Future<List<Foo>> foo();
}

Future<List<Foo>> future = demoService.foo();
List<Foo> foos = future.get();

```


## Fallback

```java

@RequestUrl( value = { "http://127.0.0.1:8762" }, fallback = <? extends DemoService.class> )
public interface DemoService {
    @RequestMapping("foo")
    String foo();
}

```

## Header

```java

@RequestUrl( value = { "http://127.0.0.1:8762" } )
public interface DemoService {
    @RequestMapping("foo")
    @RequestHeaders({
	  @RequestHeader(name = "Accept", value = "application/json;charset=utf-8"),
	  @RequestHeader(name = "User-Agent", value = "Mozilla/5.0")
	})
    String foo();
}

```

## Body

```java

@RequestUrl( value = { "http://127.0.0.1:8762" } )
public interface DemoService {
    @RequestMapping("foo")
    @RequestBody("{username:{username}, password:{password}}")
    String foo(@RequestParam("username") username, @RequestParam("password") password);
}

```







