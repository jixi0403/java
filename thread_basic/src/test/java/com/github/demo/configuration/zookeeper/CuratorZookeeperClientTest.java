package com.github.demo.configuration.zookeeper;

import com.github.configuration.zookeeper.CuratorZookeeperClient;
import com.github.configuration.zookeeper.EventListener;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.zookeeper.WatchedEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

/**
 * Created by error on 2017/3/29.
 */
public class CuratorZookeeperClientTest {
    private CuratorZookeeperClient client;
    private String nodeNotExisting = "/test1";
    private String nodeExisting = "/test";
    private String subNodeExisting = "/test/test1";
    @Before
    public void setup() {
        client = new CuratorZookeeperClient("127.0.0.1:2181");
    }

    @After
    public void teardown() {
        client.close();
    }
    @Test
    public void testCreatePersist() throws InterruptedException {
        if (!client.checkNodeExisting(nodeExisting)) {
            client.createPersist(nodeExisting);
        }

    }

    @Test
    public void testCreateEphemeral() {
        String root = "/";
        client.createPersist(root);

        String testMultiLevelHost = "/root/redis/host";
        client.createPersist(testMultiLevelHost);

        String testMultiLevelPort = "/root/redis/port";
        client.createPersist(testMultiLevelPort);
    }

    @Test
    public void testGetNode() {
        assertTrue(client.checkNodeExisting(nodeExisting));
        assertFalse(client.checkNodeExisting(nodeNotExisting));
    }

    @Test
    public void testSetAndGetNodeValue() {
        if(client.checkNodeExisting(nodeExisting)) {
            String expect = "123";
            client.setData(nodeExisting, expect.getBytes());

            byte[] nodeExistingBytes = client.getData(nodeExisting);

            assertEquals(expect, new String(nodeExistingBytes));
        }
    }

    @Test
    public void testAddTargetChildListener() throws InterruptedException {
        if(!client.checkNodeExisting(subNodeExisting)) {
            client.createPersist(subNodeExisting);
        }

        List<String> nodeList = client.addTargetChildListener(subNodeExisting, new NodeStateChangeWatcher(client));
        for(String node : nodeList) {
            System.out.println(node);
        }

        byte[] data = client.addWatcher(subNodeExisting, new NodeStateChangeWatcher(client));

        client.setData(subNodeExisting, "123".getBytes());
        Thread.sleep(1000);
        client.setData(subNodeExisting, "456".getBytes());
        Thread.sleep(1000);
        client.setData(subNodeExisting, "789".getBytes());
        Thread.sleep(1000);
        client.setData(subNodeExisting, "abc".getBytes());
        Thread.sleep(1000);
    }

    @Test
    public void testReverse() {
        int num = 1000;
        assertEquals(1, reverse(num));
        assertEquals(-1, reverse(-1));
    }

    public int reverse(int x) {
        char sign = '+';
        if(x < 0) {
            x = Math.abs(x);
            sign = '-';
        }

        String number = String.valueOf(x);
        StringBuilder sb = new StringBuilder(number.length() + 1);
        sb.append(number).append(sign).reverse();

        int result = 0;
        try{
            result = Integer.valueOf(sb.toString());
        }catch(Exception e){
            //TODO you can do nothing ^_^
        }

        return result;
    }
}

class NodeStateChangeWatcher implements CuratorWatcher {
    private final CuratorZookeeperClient client;

    public NodeStateChangeWatcher(CuratorZookeeperClient client) {
        this.client = client;
    }

    public void process(WatchedEvent watchedEvent) throws Exception {
        client.addWatcher(watchedEvent.getPath(), this);

        System.out.println(watchedEvent.getPath() + ',' + watchedEvent.getType());
        switch(watchedEvent.getType()) {
        case NodeDataChanged:
            System.out.println("^_^, " + new String(client.getData(watchedEvent.getPath())));
            break;
            default:
                System.out.println("I don't care about this event type." + watchedEvent.getType());
        }
    }
}

class PropertiesEventListener implements EventListener {

    public void process(CuratorZookeeperClient client, WatchedEvent event) {
        System.out.println(event.getPath() + ',' + event.getType());
        switch(event.getType()) {
        case NodeDataChanged:
            System.out.println("^_^, " + new String());
            break;
        default:
            System.out.println("I don't care about this event type." + event.getType());
        }
    }
}