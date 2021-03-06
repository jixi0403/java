package com.github.configuration.zookeeper;

import org.apache.zookeeper.WatchedEvent;

/**
 * Created by error on 2017/3/30.
 */
public interface EventListener {
    /**
     * 节点改变时间处理
     * @param zkClient
     * @param event
     */
    void process(CuratorZookeeperClient zkClient, WatchedEvent event);
}
