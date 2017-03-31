package com.github.configuration.zookeeper;

/**
 * Created by error on 2017/3/31.
 */
public enum CustomConnectionState {
    INVALID(-1),
    RECONNECT(1),
    CONNECTED(2),
    DISCONNECTED(3);

    private int ordinal;

    CustomConnectionState(int ordinal) {
        this.ordinal = ordinal;
    }

    public int getOrdinal() {
        return ordinal;
    }
}
