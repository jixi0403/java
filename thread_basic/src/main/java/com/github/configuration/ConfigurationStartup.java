package com.github.configuration;

import com.github.configuration.common.RedisConfigServiceImpl;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by error on 2017/3/31.
 */
public class ConfigurationStartup {

    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] { "spring.xml"});

        context.start();
        System.out.println(">>>>>>>>>>>>>>Start Completed<<<<<<<<<<<<<<");

        /*int i = 0;
        while(true) {
            RedisConfigServiceImpl service = context.getBean(RedisConfigServiceImpl.class);
            service.printRedisConfig();
            if(++i % 1000 == 0) {
                break;
            }
            Thread.sleep(1000);
        }*/
        synchronized (ConfigurationStartup.class) {
            while (true) {
                try {
                    ConfigurationStartup.class.wait();
                } catch (InterruptedException e) {
                    System.err.println("== synchronized error:" + e.getMessage());
                }
            }
        }
    }
}
