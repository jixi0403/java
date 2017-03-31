package com.github.configuration.zookeeper;

import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.zookeeper.WatchedEvent;

/**
 * Created by error on 2017/3/30.
 */
public class CuratorWatcherImpl implements CuratorWatcher {
    private final CuratorZookeeperClient client;
    private final EventListener eventListener;

    public CuratorWatcherImpl(CuratorZookeeperClient client, EventListener listener) {
        this.client = client;
        this.eventListener = listener;
    }

    public void process(WatchedEvent watchedEvent) throws Exception {
        client.addWatcher(watchedEvent.getPath(), this);
        eventListener.process(client, watchedEvent);
    }
}
