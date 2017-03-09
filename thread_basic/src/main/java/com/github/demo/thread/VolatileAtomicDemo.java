package com.github.demo.thread;

/**
 * Created by Sunshine_wx on 17/3/9.
 */
public class VolatileAtomicDemo {

    private static volatile int race = 0;

    public static void increase() {
        race ++;
    }

    public static final int THREAD_COUNT = 20;

    public static void main(String[] args) {
        Thread[] threads = new Thread[THREAD_COUNT];

        for(int i = 0; i < THREAD_COUNT; i++) {
            threads[i] = new Thread(new Runnable() {
                public void run() {
                    for(int i = 0; i < 1000; i++) {
                        increase();
                    }
                }
            });
            threads[i].start();
        }

        while(Thread.activeCount() > 1) {
            Thread.yield();
        }

        System.out.println(race);
    }
}
