package com.github.configuration.spring;

import com.github.annotation.ConfigRoot;
import com.github.annotation.DisConfigField;
import com.github.configuration.exception.ConfigurationException;
import com.github.configuration.util.ReflectionWrapper;
import com.github.configuration.zookeeper.CuratorWatcherImpl;
import com.github.configuration.zookeeper.CuratorZookeeperClient;
import com.github.configuration.zookeeper.EventListener;
import com.github.configuration.zookeeper.ZookeeperRegistry;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * Created by error on 2017/3/31.
 */
public class ConfigurationBean implements InitializingBean, BeanDefinitionRegistryPostProcessor,
                                          ApplicationContextAware {
    public static final String stringCanonicalName = String.class.getCanonicalName();
    public static final String IntegerCanonicalName1 = Integer.class.getCanonicalName();
    public static final String IntegerCanonicalName2 = int.class.getCanonicalName();
    public static final String LongCanonicalName1 = Long.class.getCanonicalName();
    public static final String LongCanonicalName2 = long.class.getCanonicalName();
    public static final String DoubleCanonicalName1 = Double.class.getCanonicalName();
    public static final String DoubleCanonicalName2 = double.class.getCanonicalName();
    public static final String FloatCanonicalName1 = Float.class.getCanonicalName();
    public static final String FloatCanonicalName2 = float.class.getCanonicalName();

    private String host;
    private int port;
    private String scanPackage;
    private String production;
    private String app;

    private ApplicationContext applicationContext;
    private ZookeeperRegistry zkRegistry;

    public void afterPropertiesSet() throws Exception {
        String root = buildPath(production, app);
        CuratorZookeeperClient zkClient = new CuratorZookeeperClient(host + ":" + port);
        this.zkRegistry = new ZookeeperRegistry(zkClient);
        ReflectionWrapper wrapper = new ReflectionWrapper(this.scanPackage);
        Set<Class<?>> classSet = wrapper.getTypesAnnotateWith(ConfigRoot.class);
        for (Class<?> clazz : classSet) {
            ConfigRoot configRoot = clazz.getAnnotation(ConfigRoot.class);
            String dataPath = buildPath(root, configRoot.root());

            final Object obj = clazz.newInstance();
            Set<Field> fieldSet = wrapper.getFieldsAnnotatedWith(clazz, DisConfigField.class);
            for (final Field f : fieldSet) {
                DisConfigField fieldConfig = f.getAnnotation(DisConfigField.class);
                String fieldName = fieldConfig.field();
                f.setAccessible(true);
                String finalPath = buildPath(dataPath, fieldName);

                zkRegistry.addListener(finalPath, new EventListener() {
                    public void process(CuratorZookeeperClient zkClient, WatchedEvent event) {
                        if(event.getType().equals(EventType.NodeDataChanged)) {
                            byte[] dataBytes = zkClient.getData(event.getPath());
                            try {
                                setFieldData(obj, f, new String(dataBytes));
                                System.out.println(obj);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (ConfigurationException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

                byte[] dataBytes = zkClient.getData(finalPath);

                String data = new String(dataBytes);
                setFieldData(obj, f, data);
            }

            ConfigurableListableBeanFactory beanFactory = ((ConfigurableApplicationContext) applicationContext).getBeanFactory();
            beanFactory.registerSingleton(clazz.getName(), obj);
        }
    }

    private void setFieldData(Object obj, Field f, String data) throws IllegalAccessException, ConfigurationException {
        String type = getCanonicalName(f.getType());
        if (type.equals(stringCanonicalName)) {
            f.set(obj, data);
        } else if (type.equals(IntegerCanonicalName1) || type.equals(IntegerCanonicalName2)) {
            f.set(obj, Integer.parseInt(data));
        } else if (type.equals(FloatCanonicalName1) || type.equals(FloatCanonicalName2)) {
            f.set(obj, Float.parseFloat(data));
        } else if (type.equals(DoubleCanonicalName1) || type.equals(DoubleCanonicalName2)) {
            f.set(obj, Double.parseDouble(data));
        } else if (type.equals(LongCanonicalName1) || type.equals(LongCanonicalName2)) {
            f.set(obj, Long.parseLong(data));
        } else {
            throw new ConfigurationException("Filed<" + f.getName() + "> type is not supported");
        }
    }

    private String buildPath(String suffix, String ... paths) {
        StringBuilder sb = new StringBuilder(suffix);
        for(String path : paths) {
            sb.append(CuratorZookeeperClient.ZK_PATH_SEPARATOR).append(path);
        }

        return sb.toString();
    }

    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
    }

    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory)
            throws BeansException {

    }

    private String getCanonicalName(Class clazz) {
        return clazz.getCanonicalName();
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getScanPackage() {
        return scanPackage;
    }

    public void setScanPackage(String scanPackage) {
        this.scanPackage = scanPackage;
    }

    public String getProduction() {
        return production;
    }

    public void setProduction(String production) {
        this.production = production;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
