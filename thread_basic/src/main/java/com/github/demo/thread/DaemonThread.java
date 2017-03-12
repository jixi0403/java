package com.github.demo.thread;

public class DaemonThread {
    public static void main(String[] args) {
        Thread daemon = new Thread(new TestDeamonThread(), "daemon");
        daemon.setDaemon(true);
        Thread user = new Thread(new TestDeamonThread(), "user");

        daemon.start();
        user.start();
    }
}

class TestDeamonThread implements Runnable {

    public void run() {
        if(Thread.currentThread().isDaemon()) {
            while(true) {
                System.out.println("Daemon thread is running");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                Thread.sleep(5000);
                System.out.println("User thread exited.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}