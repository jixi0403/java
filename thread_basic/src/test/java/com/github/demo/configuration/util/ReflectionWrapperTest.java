package com.github.demo.configuration.util;

import com.github.annotation.ConfigRoot;
import com.github.annotation.DisConfigField;
import com.github.configuration.util.ReflectionWrapper;
import com.github.configuration.zookeeper.CuratorZookeeperClient;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * Created by error on 2017/3/30.
 */
public class ReflectionWrapperTest {
    public static final String stringCanonicalName = String.class.getCanonicalName();
    public static final String IntegerCanonicalName1 = Integer.class.getCanonicalName();
    public static final String IntegerCanonicalName2 = int.class.getCanonicalName();
    public static final String LongCanonicalName1 = Long.class.getCanonicalName();
    public static final String LongCanonicalName2 = long.class.getCanonicalName();
    public static final String DoubleCanonicalName1 = Double.class.getCanonicalName();
    public static final String DoubleCanonicalName2 = double.class.getCanonicalName();
    public static final String FloatCanonicalName1 = Float.class.getCanonicalName();
    public static final String FloatCanonicalName2 = float.class.getCanonicalName();

    private ReflectionWrapper wrapper = new ReflectionWrapper("com.github.configuration.common");
    private CuratorZookeeperClient zkClient = new CuratorZookeeperClient("127.0.0.1:2181");
    private String hostPath = "/dianfuketang/dianfu-web/redis/host";
    private String portPath = "/dianfuketang/dianfu-web/redis/port";
    @Before
    public void setup() {
        //zk
        zkClient.createPersist(hostPath);
        zkClient.createPersist(portPath);
        zkClient.setData(hostPath, "192.168.2.121".getBytes());
        zkClient.setData(portPath, "212121".getBytes());
    }

    @Test
    public void testLoadClasses() throws IllegalAccessException, InstantiationException {
        String root = "/root";
        Set<Class<?>> classSet = wrapper.getTypesAnnotateWith(ConfigRoot.class);
        for(Class<?> clazz : classSet) {
            ConfigRoot configRoot = clazz.getAnnotation(ConfigRoot.class);
            String rootPath = configRoot.root();
            String redisPath = root + CuratorZookeeperClient.ZK_PATH_SEPARATOR + rootPath;
            Object obj = clazz.newInstance();
            Set<Field> fieldSet = wrapper.getFieldsAnnotatedWith(clazz, DisConfigField.class);
            for(Field f : fieldSet) {
                DisConfigField fieldConfig = f.getAnnotation(DisConfigField.class);
                String remoteFieldName = fieldConfig.field();
                f.setAccessible(true);
                String type = getCanonicalName(f.getType());
                String finalPath = redisPath + CuratorZookeeperClient.ZK_PATH_SEPARATOR + remoteFieldName;
                byte[] dataBytes = zkClient.getData(finalPath);

                if(type.equals(stringCanonicalName)) {
                    f.set(obj, new String(dataBytes));
                } else if(type.equals(IntegerCanonicalName1) || type.equals(IntegerCanonicalName2)) {
                    f.set(obj, Integer.parseInt(new String(dataBytes)));
                }
            }

            System.out.println(obj);
        }
    }

    public static String getCanonicalName(Class clazz) {
        return clazz.getCanonicalName();
    }
}
