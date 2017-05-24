package com.yiwugou.homer.eureka.test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.junit.Test;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.EurekaInstanceConfig;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.EurekaClientConfig;
import com.yiwugou.homer.core.Homer;
import com.yiwugou.homer.eureka.Constants;
import com.yiwugou.homer.eureka.DynamicProperty;
import com.yiwugou.homer.eureka.EurekaInstanceCreater;
import com.yiwugou.homer.eureka.HomerEurekaClientConfig;
import com.yiwugou.homer.eureka.HomerEurekaInstanceConfig;
import com.yiwugou.homer.eureka.PropertiesFileDynamicProperty;

public class HomerEurekaTest {
    private FooService fooService = Homer.builder().instanceCreater(new EurekaInstanceCreater())
            .proxy(FooService.class);

    public static void main(String[] args) throws Exception {
        HomerEurekaTest test = new HomerEurekaTest();
        int index = 10000000;
        while (index-- > 0) {
            test.test2();
            System.err.println("-----------------------------------------------------------------");
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void test3() throws Exception {
        String propertiesFile = "eureka-client.properties";
        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        System.err.println(path);
        InputStream is = new FileInputStream(path + propertiesFile);
        Properties prop = new Properties();
        prop.load(is);
        while (true) {
            String value = prop.getProperty("eureka.serviceUrl.default");
            System.err.println(value);
            Thread.sleep(1000L);
        }
    }

    @Test
    public void test2() {
        String back = this.fooService.foo();
        System.err.println(back);
    }

    @Test
    public void test1() {
        String vipAddress = "ms-redis-service";
        DynamicProperty dynamicProperty = new PropertiesFileDynamicProperty(Constants.DEFAULT_CONFIG_FILE);
        ApplicationInfoManager.OptionalArgs options = null;
        EurekaInstanceConfig instanceConfig = new HomerEurekaInstanceConfig(dynamicProperty);
        ApplicationInfoManager applicationInfoManager = new ApplicationInfoManager(instanceConfig, options);
        EurekaClientConfig clientConfig = new HomerEurekaClientConfig(dynamicProperty);
        EurekaClient client = new DiscoveryClient(applicationInfoManager, clientConfig);
        int index = 10000000;
        while (index-- > 0) {
            List<InstanceInfo> ins = client.getInstancesByVipAddress(vipAddress, false);
            for (InstanceInfo in : ins) {
                String host = in.getHostName();
                int port = in.getPort();
                String ipAddr = in.getIPAddr();
                String vipA = in.getVIPAddress();
                String appGroupName = in.getAppGroupName();
                String appName = in.getAppName();
                System.err.println(host + " " + port + " " + ipAddr + " " + vipA + " " + appGroupName + " " + appName);

            }
            System.err.println("-----------------------------------------------------------------");
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        InstanceInfo in = null;
        client.getApplication("").removeInstance(in);
        client.getApplication("").addInstance(in);
    }

}
