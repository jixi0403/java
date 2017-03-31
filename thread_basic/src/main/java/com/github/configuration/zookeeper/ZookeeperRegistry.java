package com.github.configuration.zookeeper;

import org.apache.zookeeper.WatchedEvent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by error on 2017/3/31.
 */
public class ZookeeperRegistry {
    private final CuratorZookeeperClient zkClient;
    private final ConcurrentHashMap<String, EventListener> zkListeners = new ConcurrentHashMap<String, EventListener>();

    public ZookeeperRegistry(CuratorZookeeperClient zkClient) {
        this.zkClient = zkClient;
        this.zkClient.addStateListener(new StateListener() {
            public void stateChanged(CustomConnectionState state) {
                if(state == CustomConnectionState.RECONNECT) {
                    recover();
                }
            }
        });
    }

    public void recover() {
        for(Map.Entry<String, EventListener> entry : zkListeners.entrySet()) {
            String path = entry.getKey();
            zkClient.addListener(path, entry.getValue());
        }
    }

    public void addListener(String path, EventListener listener) {
        zkListeners.putIfAbsent(path, listener);
        zkClient.addListener(path, listener);
    }

}
