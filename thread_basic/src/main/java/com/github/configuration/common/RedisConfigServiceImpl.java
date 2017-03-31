package com.github.configuration.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by error on 2017/3/31.
 */
@Service
public class RedisConfigServiceImpl {

    @Autowired
    private RedisConfig redisConfig;

    public void printRedisConfig() {
        System.out.println(redisConfig);
    }
}
