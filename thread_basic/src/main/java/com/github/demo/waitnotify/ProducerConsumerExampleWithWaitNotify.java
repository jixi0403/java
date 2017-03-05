package com.github.demo.waitnotify;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/5.
 */
public class ProducerConsumerExampleWithWaitNotify {
    public static void main(String[] args) {
        List<Integer> taskQueue = new LinkedList<Integer>();
        int MAX_CAPACITY = 5;
        Thread tProducer = new Thread(new Producer(taskQueue, MAX_CAPACITY), "producer");
        Thread tConsumer = new Thread(new Consumer(taskQueue), "consumer");
        tConsumer.start();
        tProducer.start();
    }
}
