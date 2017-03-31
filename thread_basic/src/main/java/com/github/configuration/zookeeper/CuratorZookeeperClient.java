package com.github.configuration.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.CuratorFrameworkFactory.Builder;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException.NoNodeException;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by error on 2017/3/29.
 */
public class CuratorZookeeperClient {
    public static final String ZK_PATH_SEPARATOR = "/";
    private CuratorFramework client;

    private final Set<StateListener> stateListeners = new CopyOnWriteArraySet<StateListener>();

    public CuratorZookeeperClient(String address) {
        Builder builder = CuratorFrameworkFactory.builder().connectString(address)
                                                 .retryPolicy(new RetryNTimes(Integer.MAX_VALUE, 3000))
                                                 .connectionTimeoutMs(5000);
        this.client = builder.build();
        this.client.getConnectionStateListenable().addListener(new ConnectionStateListener() {
            public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {
                CustomConnectionState state = CustomConnectionState.INVALID;
                if(connectionState == ConnectionState.LOST) {
                    System.out.println("Connection lost");
                    state = CustomConnectionState.DISCONNECTED;
                } else if(connectionState == ConnectionState.CONNECTED) {
                    System.out.println("Connection connected");
                    state = CustomConnectionState.CONNECTED;
                } else if(connectionState == ConnectionState.RECONNECTED) {
                    System.out.println("Connection reconnected");
                    state = CustomConnectionState.RECONNECT;
                }

                CuratorZookeeperClient.this.stateChanged(state);
            }
        });
        this.client.start();
    }

    public void stateChanged(CustomConnectionState state) {
        for(StateListener stateListener : stateListeners) {
            stateListener.stateChanged(state);
        }
    }

    public void addStateListener(StateListener listener) {
        stateListeners.add(listener);
    }

    /**
     * 创建一个持久化节点
     * @param path 节点路径
     */
    public void createPersist(String path) {
        doCreateNode(path, false);
    }

    private void doCreateNode(String path, boolean isEphemeral) {
        if(path == null) {
            return;
        }

        String[] paths = path.split(CuratorZookeeperClient.ZK_PATH_SEPARATOR);
        String fullPath = "";
        for(String nodePath : paths) {
            if(nodePath.equals("")) {
                continue;
            }

            fullPath += CuratorZookeeperClient.ZK_PATH_SEPARATOR;
            fullPath += nodePath;
            if(!checkNodeExisting(fullPath)) {
                try {
                    client.create().withMode(isEphemeral ? CreateMode.EPHEMERAL : CreateMode.PERSISTENT).forPath(fullPath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 创建一个临时节点
     * @param path 节点路径
     */
    public void createEphemeral(String path) {
        doCreateNode(path, true);
    }

    /**
     * 检查节点是否存在
     * @param path 节点路径
     * @return true：存在 false:不存在
     */
    public boolean checkNodeExisting(String path) {
        try {
            Stat stat = client.checkExists().forPath(path);
            return stat != null;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 给节点设置数据
     * @param path 节点路径
     * @param payload 数据
     */
    public void setData(String path, byte[] payload) {
        try {
            client.setData().forPath(path, payload);
        } catch(NoNodeException e) {
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取阶段数据
     * @param path 节点路径
     * @return 节点数据
     */
    public byte[] getData(String path) {
        try {
            return client.getData().forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 为节点增加事件监听
     * @param path 节点路径
     * @param watcher 监听器
     * @return 子节点列表
     */
    public List<String> addTargetChildListener(String path, CuratorWatcher watcher) {
        try {
            return client.getChildren().usingWatcher(watcher).forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] addWatcher(String path, CuratorWatcher watcher) {
        try {
            return client.getData().usingWatcher(watcher).forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void addListener(String path, EventListener listener) {
        CuratorWatcher watcher = new CuratorWatcherImpl(this, listener);
        addWatcher(path, watcher);
    }

    /**
     * 关闭资源
     */
    public void close() {
        this.client.close();
    }

}
