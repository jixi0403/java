package com.github.configuration.zookeeper;

import com.netflix.curator.framework.CuratorFramework;
import com.netflix.curator.framework.CuratorFrameworkFactory;
import com.netflix.curator.framework.CuratorFrameworkFactory.Builder;
import com.netflix.curator.framework.api.CuratorWatcher;
import com.netflix.curator.framework.state.ConnectionState;
import com.netflix.curator.framework.state.ConnectionStateListener;
import com.netflix.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException.NoNodeException;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;

/**
 * Created by error on 2017/3/29.
 */
public class CuratorZookeeperClient {

    private CuratorFramework client;

    public CuratorZookeeperClient(String address) {
        Builder builder = CuratorFrameworkFactory.builder().connectString(address)
                .retryPolicy(new RetryNTimes(Integer.MAX_VALUE, 1000))
                .connectionTimeoutMs(5000);

        try {
            this.client = builder.build();
            this.client.getConnectionStateListenable().addListener(new ConnectionStateListener() {
                public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {
                    if(connectionState == ConnectionState.LOST) {
                        System.out.println("Connection lost");
                    } else if(connectionState == ConnectionState.CONNECTED) {
                        System.out.println("Connection connected");
                    } else if(connectionState == ConnectionState.RECONNECTED) {
                        System.out.println("Connection reconnected");
                    }
                }
            });
            this.client.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建一个持久化节点
     * @param path 节点路径
     */
    public void createPersist(String path) {
        try {
            client.create().forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建一个临时节点
     * @param path 节点路径
     */
    public void createEmphemeral(String path) {
        try {
            client.create().withMode(CreateMode.EPHEMERAL).forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    /**
     * 关闭资源
     */
    public void close() {
        this.client.close();
    }

}
