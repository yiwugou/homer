package com.yiwugou.homer.eureka.test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.EurekaInstanceConfig;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.EurekaClientConfig;
import com.yiwugou.homer.core.Homer;
import com.yiwugou.homer.core.config.PropertiesConfigLoader;
import com.yiwugou.homer.core.util.CommonUtils;
import com.yiwugou.homer.eureka.DynamicProperty;
import com.yiwugou.homer.eureka.EurekaConstants;
import com.yiwugou.homer.eureka.EurekaInstanceCreater;
import com.yiwugou.homer.eureka.HomerEurekaClientConfig;
import com.yiwugou.homer.eureka.HomerEurekaInstanceConfig;
import com.yiwugou.homer.eureka.PropertiesFileDynamicProperty;

/**
 *
 * HomerEurekaTest
 *
 * @author zhanxiaoyong@yiwugou.com
 *
 * @since 2017年10月16日 下午4:21:20
 */
public class HomerEurekaTest {
    private FooService fooService;
    private BarService barService;

    @Before
    public void before() throws Exception {
        this.fooService = Homer.builder().instanceCreater(new EurekaInstanceCreater()).proxy(FooService.class);

        InputStream in = this.getClass().getClassLoader().getResourceAsStream("homer-eureka.properties");
        Properties properties = new Properties();
        properties.load(in);
        CommonUtils.close(in);
        this.barService = Homer.builder().instanceCreater(new EurekaInstanceCreater("xiaoyong", properties))
                .configLoader(new PropertiesConfigLoader(properties)).proxy(BarService.class);
    }

    public static void main(String[] args) throws Exception {
        HomerEurekaTest test = new HomerEurekaTest();
        test.before();
        int index = 10000000;
        while (index-- > 0) {
            test.test4();
            System.err.println("-----------------------------------------------------------------");
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void test4() {
        String back = this.barService.foo();
        System.err.println(back);
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
            Thread.sleep(500L);
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
        DynamicProperty dynamicProperty = new PropertiesFileDynamicProperty(EurekaConstants.DEFAULT_CONFIG_FILE);
        ApplicationInfoManager.OptionalArgs options = null;
        EurekaInstanceConfig instanceConfig = new HomerEurekaInstanceConfig("xiaoyong", dynamicProperty);
        ApplicationInfoManager applicationInfoManager = new ApplicationInfoManager(instanceConfig, options);
        EurekaClientConfig clientConfig = new HomerEurekaClientConfig("xiaoyong", dynamicProperty);
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
