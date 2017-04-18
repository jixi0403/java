package com.github.concurrentpractice.chapter_three;

/**
 * Created by Sunshine_wx on 17/4/18.
 */
public class ThreadLocalDemo {

    public static class MyRunnable implements Runnable {
        private ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>();

        public void run() {
            threadLocal.set((int)(Math.random() * 100D));

            try {
                Thread.sleep(2000);
            } catch(InterruptedException e) {

            }

            System.out.println(threadLocal.get());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyRunnable sharedRunnableInstance = new MyRunnable();
        Thread thread1 = new Thread(sharedRunnableInstance);
        Thread thread2 = new Thread(sharedRunnableInstance);
        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }
}
