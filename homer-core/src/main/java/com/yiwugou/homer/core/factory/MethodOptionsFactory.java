package com.yiwugou.homer.core.factory;

import java.lang.reflect.Method;

import com.yiwugou.homer.core.annotation.RequestConfig;
import com.yiwugou.homer.core.annotation.RequestUrl;
import com.yiwugou.homer.core.config.ConfigLoader;
import com.yiwugou.homer.core.config.MethodOptions;
import com.yiwugou.homer.core.constant.RequestDefault;
import com.yiwugou.homer.core.server.ServerHandler;

public class MethodOptionsFactory {
    private Class<?> clazz;
    private Method method;
    private ConfigLoader configLoader;
    private InstanceCreater instanceCreater;

    private String className;
    private String methodName;
    private String classMethodName;

    public MethodOptionsFactory(Method method, ConfigLoader configLoader, InstanceCreater instanceCreater) {
        this.method = method;
        this.configLoader = configLoader;
        this.instanceCreater = instanceCreater;
        this.clazz = method.getDeclaringClass();
        this.className = this.clazz.getName();
        this.methodName = this.method.getName();
        this.classMethodName = this.className + "." + this.methodName;
    }

    public MethodOptions create() {
        RequestConfig classRequestConfig = this.clazz.getAnnotation(RequestConfig.class);

        MethodOptions methodOptions = new MethodOptions();

        RequestConfig methodRequestConfig = this.method.getAnnotation(RequestConfig.class);

        this.initRetry(classRequestConfig, methodOptions, methodRequestConfig);

        this.initExecute(classRequestConfig, methodOptions, methodRequestConfig);

        this.initActive(classRequestConfig, methodOptions, methodRequestConfig);

        this.initCache(classRequestConfig, methodOptions, methodRequestConfig);

        this.initMock(classRequestConfig, methodOptions, methodRequestConfig);

        this.initLoadBalance(classRequestConfig, methodOptions, methodRequestConfig);

        RequestUrl requestUrl = this.clazz.getAnnotation(RequestUrl.class);
        ServerHandler serverHandler = this.instanceCreater.createServerHandler(requestUrl, this.clazz,
                this.configLoader);
        methodOptions.setServerHandler(serverHandler);
        return methodOptions;
    }

    private void initLoadBalance(RequestConfig classRequestConfig, MethodOptions methodOptions,
            RequestConfig methodRequestConfig) {
        String classLoadBalance = classRequestConfig == null ? RequestDefault.LOAD_BALANCE
                : this.configLoader.loader(this.className + ConfigLoader.LOAD_BALANCE,
                        classRequestConfig.loadBalance().toString().toLowerCase());
        String methodLoadBalance = methodRequestConfig == null ? RequestDefault.LOAD_BALANCE
                : this.configLoader.loader(this.classMethodName + ConfigLoader.LOAD_BALANCE,
                        methodRequestConfig.loadBalance().toString().toLowerCase());
        String loadBalance = notDef(classLoadBalance, RequestDefault.LOAD_BALANCE.toLowerCase(), methodLoadBalance);
        methodOptions.setLoadBalance(LoadBalanceFactory.createInstants(loadBalance));
    }

    private void initMock(RequestConfig classRequestConfig, MethodOptions methodOptions,
            RequestConfig methodRequestConfig) {
        Boolean classMock = classRequestConfig == null ? RequestDefault.MOCK
                : this.configLoader.loader(this.className + ConfigLoader.MOCK, classRequestConfig.mock());
        Boolean methodMock = methodRequestConfig == null ? RequestDefault.MOCK
                : this.configLoader.loader(this.classMethodName + ConfigLoader.MOCK, methodRequestConfig.mock());
        methodOptions.setMock(notDef(methodMock, RequestDefault.MOCK, classMock));
    }

    private void initCache(RequestConfig classRequestConfig, MethodOptions methodOptions,
            RequestConfig methodRequestConfig) {
        Long classCache = classRequestConfig == null ? RequestDefault.CACHE
                : this.configLoader.loader(this.className + ConfigLoader.CACHE, classRequestConfig.cache());
        Long methodCache = methodRequestConfig == null ? RequestDefault.CACHE
                : this.configLoader.loader(this.classMethodName + ConfigLoader.CACHE, methodRequestConfig.cache());
        methodOptions.setCache(notDef(methodCache, RequestDefault.CACHE, classCache));
    }

    private void initActive(RequestConfig classRequestConfig, MethodOptions methodOptions,
            RequestConfig methodRequestConfig) {
        Integer classActive = classRequestConfig == null ? RequestDefault.ACTIVE
                : this.configLoader.loader(this.className + ConfigLoader.ACTIVE, classRequestConfig.active());
        Integer methodActive = methodRequestConfig == null ? RequestDefault.ACTIVE
                : this.configLoader.loader(this.classMethodName + ConfigLoader.ACTIVE, methodRequestConfig.active());
        methodOptions.setActive(notDef(methodActive, RequestDefault.ACTIVE, classActive));
    }

    private void initExecute(RequestConfig classRequestConfig, MethodOptions methodOptions,
            RequestConfig methodRequestConfig) {
        Integer classExecute = classRequestConfig == null ? RequestDefault.EXECUTE
                : this.configLoader.loader(this.className + ConfigLoader.EXECUTE, classRequestConfig.execute());
        Integer methodExecute = methodRequestConfig == null ? RequestDefault.EXECUTE
                : this.configLoader.loader(this.classMethodName + ConfigLoader.EXECUTE, methodRequestConfig.execute());
        methodOptions.setExecute(notDef(methodExecute, RequestDefault.EXECUTE, classExecute));
    }

    private void initRetry(RequestConfig classRequestConfig, MethodOptions methodOptions,
            RequestConfig methodRequestConfig) {
        Integer classRetry = classRequestConfig == null ? RequestDefault.RETRY
                : this.configLoader.loader(this.className + ConfigLoader.RETRY, classRequestConfig.retry());
        Integer methodRetry = methodRequestConfig == null ? RequestDefault.RETRY
                : this.configLoader.loader(this.classMethodName + ConfigLoader.RETRY, methodRequestConfig.retry());
        methodOptions.setRetry(notDef(methodRetry, RequestDefault.RETRY, classRetry));
    }

    public static <T> T notDef(T obj, T def, T desc) {
        return (obj == null || obj.equals(def)) ? desc : obj;
    }
}
