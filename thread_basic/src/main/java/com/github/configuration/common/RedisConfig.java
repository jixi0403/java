package com.github.configuration.common;

import com.github.annotation.ConfigRoot;
import com.github.annotation.DisConfigField;

/**
 * Created by error on 2017/3/30.
 */
@ConfigRoot(root = "redis")
public class RedisConfig {
    @DisConfigField(field = "host")
    private String host;
    @DisConfigField(field = "port")
    private int port;

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

    @Override
    public String toString() {
        return "RedisConfig{" +
               "host='" + host + '\'' +
               ", port=" + port +
               '}';
    }
}
