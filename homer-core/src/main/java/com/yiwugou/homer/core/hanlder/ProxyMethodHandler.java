package com.yiwugou.homer.core.hanlder;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yiwugou.homer.core.Homer;
import com.yiwugou.homer.core.ProxyInvocationHandler;
import com.yiwugou.homer.core.Request;
import com.yiwugou.homer.core.Response;
import com.yiwugou.homer.core.annotation.RequestUrl;
import com.yiwugou.homer.core.client.Client;
import com.yiwugou.homer.core.codec.Decoder;
import com.yiwugou.homer.core.config.MethodOptions;
import com.yiwugou.homer.core.enums.MethodModelEnum;
import com.yiwugou.homer.core.exception.ResponseException;
import com.yiwugou.homer.core.exception.ServerException;
import com.yiwugou.homer.core.factory.MethodOptionsFactory;
import com.yiwugou.homer.core.factory.RequestFactory;
import com.yiwugou.homer.core.filter.ActiveFilter;
import com.yiwugou.homer.core.filter.CacheFilter;
import com.yiwugou.homer.core.filter.ExecuteFilter;
import com.yiwugou.homer.core.filter.FallbackFilter;
import com.yiwugou.homer.core.filter.Filter;
import com.yiwugou.homer.core.filter.MockFilter;
import com.yiwugou.homer.core.filter.RetryFilter;
import com.yiwugou.homer.core.interceptor.BasicAuthRequestInterceptor;
import com.yiwugou.homer.core.interceptor.RequestInterceptor;
import com.yiwugou.homer.core.invoker.DefaultInvoker;
import com.yiwugou.homer.core.invoker.Function;
import com.yiwugou.homer.core.invoker.Invoker;
import com.yiwugou.homer.core.server.Server;
import com.yiwugou.homer.core.util.CommonUtils;

/**
 *
 * ProxyMethodHandler
 *
 * @author zhanxiaoyong@yiwugou.com
 *
 * @since 2017年7月10日 下午3:03:22
 */
public class ProxyMethodHandler extends AbstractMethodHandler {
    private static final Logger log = LoggerFactory.getLogger(ProxyInvocationHandler.class);

    private Client client;
    private Method method;
    private MethodOptions methodOptions;
    private List<RequestInterceptor> requestInterceptors;
    private Decoder decoder;
    private Class<?> clazz;
    private List<Filter> filters;
    private Type actualReturnType;
    private Invoker invoker;
    private MethodModelEnum methodModel = MethodModelEnum.NORMAL;

    public ProxyMethodHandler(Homer homer, Method method) {
        super();
        this.client = homer.getClient();
        this.method = method;
        this.requestInterceptors = homer.getRequestInterceptors();
        this.decoder = homer.getDecoder();
        this.clazz = this.method.getDeclaringClass();
        this.filters = homer.getFilters();
        this.methodOptions = new MethodOptionsFactory(method, homer.getConfigLoader(), homer.getInstanceCreater())
                .create();
        log.debug("method options is {}", this.methodOptions);
        this.initMethodModel();
        this.addDefaultFilters(homer);
        this.initDefaultRequestInterceptors();
        this.initInvoke();
    }

    private void initMethodModel() {
        this.actualReturnType = this.method.getGenericReturnType();
        if (this.actualReturnType instanceof ParameterizedType) {
            final ParameterizedType paramType = (ParameterizedType) this.actualReturnType;
            if (paramType.getRawType().equals(Future.class)) {
                this.actualReturnType = paramType.getActualTypeArguments()[0];
                this.methodModel = MethodModelEnum.FUTURE;
            }
        }
    }

    @Override
    public MethodModelEnum getMethodModel() {
        return this.methodModel;
    }

    @Override
    public Object invoke(Object[] args) throws Throwable {
        return this.invoker.invoke(args);
    }

    private void initDefaultRequestInterceptors() {
        RequestUrl requestUrl = this.clazz.getAnnotation(RequestUrl.class);
        if (CommonUtils.hasTest(requestUrl.basicAuth())) {
            String[] us = requestUrl.basicAuth().split(":");
            String username = us[0];
            String password = us[1];
            this.requestInterceptors.add(new BasicAuthRequestInterceptor(username, password));
        }
    }

    private void addDefaultFilters(Homer homer) {
        if (this.methodOptions.getRetry() != null || this.methodOptions.getRetry() > 0) {
            this.filters.add(new RetryFilter());
        }
        this.filters.add(0, new ExecuteFilter());
        this.filters.add(0, new ActiveFilter());
        this.filters.add(0, new FallbackFilter());
        if (homer.getFilterCache() != null && this.methodOptions.getCache() != null
                && this.methodOptions.getCache() > 0) {
            this.filters.add(0, new CacheFilter(homer.getFilterCache()));
        }
        if (this.methodOptions.getMock()) {
            this.filters.add(0, new MockFilter());
        }

    }

    private void initInvoke() {
        this.invoker = this
                .buildFilterChain(new DefaultInvoker(this.methodOptions, this.method, new Function<Object[], Object>() {
                    @Override
                    public Object apply(Object[] args) throws Exception {
                        return ProxyMethodHandler.this.loadBalanceInvoker(args);
                    }
                }));
    }

    private Object loadBalanceInvoker(Object[] args) throws Exception {
        while (true) {
            Server server = this.methodOptions.getLoadBalance()
                    .choose(this.methodOptions.getServerHandler().getUpServers(), this.method, args);
            if (server == null) {
                throw new ServerException("no server is available! down servers is "
                        + this.methodOptions.getServerHandler().getDownServers());
            }
            try {
                Request request = new RequestFactory(this.method, this.methodOptions, args, server).create();
                log.debug("request is {}", request);
                if (this.requestInterceptors != null) {
                    for (RequestInterceptor requestInterceptor : this.requestInterceptors) {
                        requestInterceptor.apply(request);
                    }
                }
                Object obj = this.executeAndDecode(request);
                this.methodOptions.getServerHandler().getServerCheck().serverUp(server);
                return obj;
            } catch (IOException e) {
                server.setException(e);
                this.methodOptions.getServerHandler().getServerCheck().serverDown(server, e);
            }
        }
    }

    private Invoker buildFilterChain(final Invoker invoker) {
        Invoker last = invoker;
        for (int i = this.filters.size() - 1; i >= 0; i--) {
            final Filter filter = this.filters.get(i);
            final Invoker next = last;
            last = new Invoker() {
                @Override
                public Object invoke(Object[] args) throws Throwable {
                    return filter.invoke(next, args);
                }

                @Override
                public Method getMethod() {
                    return invoker.getMethod();
                }

                @Override
                public MethodOptions getMethodOptions() {
                    return invoker.getMethodOptions();
                }
            };
        }
        return last;
    }

    private Object executeAndDecode(Request request) throws Exception {
        Response response = this.client.execute(request);
        log.debug("response is {}", response);
        if (response.getCode() >= 400) {
            throw new ResponseException(response);
        }
        Object obj = this.decoder.decode(response, this.actualReturnType);
        log.debug("response object is {}", obj);
        return obj;
    }

}
