package com.yiwugou.homer.core.hanlder;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yiwugou.homer.core.Homer;
import com.yiwugou.homer.core.ProxyInvocationHandler;
import com.yiwugou.homer.core.Request;
import com.yiwugou.homer.core.Response;
import com.yiwugou.homer.core.annotation.RequestConfig;
import com.yiwugou.homer.core.annotation.RequestUrl;
import com.yiwugou.homer.core.client.Client;
import com.yiwugou.homer.core.codec.Decoder;
import com.yiwugou.homer.core.config.MethodMetadata;
import com.yiwugou.homer.core.enums.MethodModelEnum;
import com.yiwugou.homer.core.exception.ResponseException;
import com.yiwugou.homer.core.exception.ServerException;
import com.yiwugou.homer.core.factory.ClassFactory;
import com.yiwugou.homer.core.factory.MethodMetadataFactory;
import com.yiwugou.homer.core.factory.RequestFactory;
import com.yiwugou.homer.core.filter.ActiveFilter;
import com.yiwugou.homer.core.filter.CacheFilter;
import com.yiwugou.homer.core.filter.ExecuteFilter;
import com.yiwugou.homer.core.filter.FallbackFilter;
import com.yiwugou.homer.core.filter.Filter;
import com.yiwugou.homer.core.filter.FutureFilter;
import com.yiwugou.homer.core.filter.MockFilter;
import com.yiwugou.homer.core.filter.RetryFilter;
import com.yiwugou.homer.core.interceptor.BasicAuthRequestInterceptor;
import com.yiwugou.homer.core.interceptor.Interceptor;
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
    private MethodMetadata methodMetadata;
    private List<Interceptor> interceptors = new LinkedList<>();
    private Decoder decoder;
    private Class<?> clazz;
    private List<Filter> filters = new LinkedList<>();
    private Type actualReturnType;
    private Invoker invoker;
    private int threadPoolSize;
    private MethodModelEnum methodModel = MethodModelEnum.NORMAL;

    public ProxyMethodHandler(Homer homer, Method method) {
        super();
        this.client = homer.getClient();
        this.method = method;
        this.decoder = homer.getDecoder();
        this.clazz = this.method.getDeclaringClass();

        this.threadPoolSize = homer.getThreadPoolSize();
        this.methodMetadata = new MethodMetadataFactory(method, homer.getConfigLoader(), homer.getInstanceCreater())
                .create();
        log.debug("MethodMetadata is {}", this.methodMetadata);
        this.initMethodModel();
        this.addFilters(homer);
        this.addInterceptors();
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
    public Object apply(Object[] args) throws Throwable {
        return this.invoker.invoke(args);
    }

    private void addInterceptors() {
        RequestUrl requestUrl = this.clazz.getAnnotation(RequestUrl.class);
        if (CommonUtils.hasTest(requestUrl.basicAuth())) {
            String[] us = requestUrl.basicAuth().split(":");
            String username = us[0];
            String password = us[1];
            this.interceptors.add(new BasicAuthRequestInterceptor(username, password));
        }

        this.addRequestConfigInterceptors(this.clazz.getAnnotation(RequestConfig.class));
        this.addRequestConfigInterceptors(this.method.getAnnotation(RequestConfig.class));
        log.debug("{} interceptors is {}", this.method, this.interceptors);
    }

    private void addRequestConfigInterceptors(RequestConfig requestConfig) {
        if (requestConfig != null) {
            Class<? extends Interceptor>[] interceptorClasses = requestConfig.interceptors();
            if (interceptorClasses != null) {
                for (Class<? extends Interceptor> interceptorClass : interceptorClasses) {
                    Interceptor interceptor = ClassFactory.newInstance(interceptorClass, true, null);
                    if (interceptor != null) {
                        this.interceptors.add(interceptor);
                    }
                }
            }
        }
    }

    private void addFilters(Homer homer) {
        if (this.methodMetadata.getRetry() != null || this.methodMetadata.getRetry() > 0) {
            this.filters.add(new RetryFilter());
        }
        this.filters.add(0, new ExecuteFilter());
        this.filters.add(0, new ActiveFilter());
        this.filters.add(0, new FallbackFilter());
        if (homer.getFilterCache() != null && this.methodMetadata.getCache() != null
                && this.methodMetadata.getCache() > 0) {
            this.filters.add(0, new CacheFilter(homer.getFilterCache()));
        }
        if (this.methodMetadata.getMock()) {
            this.filters.add(0, new MockFilter());
        }

        if (this.methodModel == MethodModelEnum.FUTURE) {
            this.filters.add(0, new FutureFilter(this.threadPoolSize));
        }

        this.addRequestConfigFilters(this.clazz.getAnnotation(RequestConfig.class));
        this.addRequestConfigFilters(this.method.getAnnotation(RequestConfig.class));
        log.debug("{} filters is {}", this.method, this.filters);
    }

    private void addRequestConfigFilters(RequestConfig requestConfig) {
        if (requestConfig != null) {
            Class<? extends Filter>[] filterClasses = requestConfig.filters();
            if (filterClasses != null) {
                for (Class<? extends Filter> filterClass : filterClasses) {
                    Filter filter = ClassFactory.newInstance(filterClass, true, null);
                    if (filter != null) {
                        this.filters.add(filter);
                    }
                }
            }
        }
    }

    private void initInvoke() {
        Invoker defaultInvoker = new DefaultInvoker(this.methodMetadata, this.method, new Function<Object[], Object>() {
            @Override
            public Object apply(Object[] args) throws Exception {
                return ProxyMethodHandler.this.loadBalanceInvoker(args);
            }
        });
        this.invoker = this.buildFilterChain(defaultInvoker);
    }

    private Object loadBalanceInvoker(Object[] args) throws Exception {
        while (true) {
            Server server = this.methodMetadata.getLoadBalance()
                    .choose(this.methodMetadata.getServerHandler().getUpServers(), this.method, args);
            if (server == null) {
                throw new ServerException("no server is available! down servers is "
                        + this.methodMetadata.getServerHandler().getDownServers());
            }
            try {
                Request request = new RequestFactory(this.method, this.methodMetadata, args, server).create();
                log.debug("request is {}", request);

                for (Interceptor interceptor : this.interceptors) {
                    interceptor.requestApply(request);
                }

                Object obj = this.executeAndDecode(request);
                this.methodMetadata.getServerHandler().getServerCheck().serverUp(server);
                return obj;
            } catch (IOException e) {
                server.setException(e);
                this.methodMetadata.getServerHandler().getServerCheck().serverDown(server, e);
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
                public MethodMetadata getMethodMetadata() {
                    return invoker.getMethodMetadata();
                }
            };
        }
        return last;
    }

    private Object executeAndDecode(Request request) throws Exception {
        Response response = this.client.execute(request);
        for (Interceptor interceptor : this.interceptors) {
            interceptor.responseApply(response);
        }
        log.debug("response is {}", response);
        if (response.getCode() >= 400) {
            throw new ResponseException(response);
        }
        Object obj = this.decoder.decode(response, this.actualReturnType);
        log.debug("response object is {}", obj);
        return obj;
    }

}
