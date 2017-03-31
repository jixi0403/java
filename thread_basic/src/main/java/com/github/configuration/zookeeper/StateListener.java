package com.github.configuration.zookeeper;

/**
 * Created by error on 2017/3/31.
 */
public interface StateListener {
    void stateChanged(CustomConnectionState state);
}
