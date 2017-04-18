package com.github.concurrentpractice.chapter_10;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Sunshine_wx on 17/4/18.
 */
public class LeftRightDeadLock {

    private final Object left = new Object();
    private final Object right = new Object();

    public void leftRight() throws InterruptedException {
        synchronized (left) {
            synchronized (right) {
                Thread.sleep(100000);
            }
        }
    }

    public void rightLeft() throws InterruptedException {
        synchronized (right) {
            synchronized (left) {
                Thread.sleep(10000);
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        final LeftRightDeadLock leftRightDeadLock = new LeftRightDeadLock();

        Runnable leftRight = new Runnable() {
            public void run() {
                try {
                    leftRightDeadLock.leftRight();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Runnable rightLeft = new Runnable() {
            public void run() {
                try {
                    leftRightDeadLock.rightLeft();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        for(int i = 0; i < 10; i++) {
            if(i >= 5) {
                executorService.submit(leftRight);
            } else {
                executorService.submit(rightLeft);
            }
        }
    }
}



