package com.github.demo.waitnotify;

import java.util.List;

/**
 * Created by Administrator on 2017/3/5.
 */
public class Consumer implements Runnable {
    private final List<Integer> taskQueue;

    public Consumer(List<Integer> taskQueue) {
        this.taskQueue = taskQueue;
    }

    public void run() {
        try {
            while(true) {
                this.consume();
            }
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Consumer ran out");
    }

    private void consume() throws InterruptedException {
        synchronized (this.taskQueue) {
            while(this.taskQueue.isEmpty()) {
                System.out.println("Queue is empty " + Thread.currentThread().getName());
                this.taskQueue.wait();
            }

            Thread.sleep(1000);
            int i = taskQueue.remove(0);
            System.out.println(Thread.currentThread().getName() + " consumed: " + i);
            taskQueue.notify();
        }
    }
}
