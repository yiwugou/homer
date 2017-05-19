
# a rest client like feign

```java

@RequestUrl({ "http://127.0.0.1:8762", "http://127.0.0.1:8763", "http://127.0.0.1:8764" })
@RequestConfig(execute = 10000, active = 10000)
public interface DemoService {

    @RequestMapping("foo")
    @RequestConfig(execute = 10000, active = 10000)
    String foo();

    @RequestMapping(value = "foo1/{username}/{password}", method = MethodEnum.GET)
    String foo1(@RequestParam("username") String username, @RequestParam("password") String password);

    @RequestMapping(value = "foo2", method = MethodEnum.GET)
    String foo2Get(@RequestParam("username") String username, @RequestParam("password") String password);

    @RequestMapping(value = "foo2", method = MethodEnum.POST)
    String foo2Post(@RequestParam("username") String username, @RequestParam("password") String password);
}

DemoService demoService = Homer.instance().build(DemoService.class);

```

