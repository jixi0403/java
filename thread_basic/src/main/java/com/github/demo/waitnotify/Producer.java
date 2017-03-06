package com.github.demo.waitnotify;

import java.util.List;

/**
 * Created by Administrator on 2017/3/5.
 */
public class Producer implements Runnable {
    private final List<Integer> taskQueue;
    private final int MAX_CAPACITY;
    public Producer(List<Integer> taskQueue, int capacity) {
        this.taskQueue = taskQueue;
        this.MAX_CAPACITY = capacity;
    }

    public void run() {
        int count = 0;

        while(true) {
            try {
                produce(count++);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void produce(int i) throws InterruptedException{
        synchronized(taskQueue) {
            while(taskQueue.size() == this.MAX_CAPACITY) {
                System.out.println("Queue is full " + Thread.currentThread().getName());
                taskQueue.wait();
            }

            Thread.sleep(1000);
            taskQueue.add(i);
            System.out.println("Produce: " + i);
            taskQueue.notify();
        }
    }
}
