package com.github.concurrentpractice.chapter_6.one;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by error on 2017/3/17.
 */
public class TaskExecutionWebServer {
    private static final int NTHREADS = 100;
    private static final Executor exec =
            Executors.newFixedThreadPool(NTHREADS);

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(80);
        while(true) {
            final Socket client = server.accept();

            Runnable task = new Runnable() {
                public void run() {
                    handleRequest(client);
                }
            };

            exec.execute(task);
        }
    }

    private static void handleRequest(Socket client) {
        InetAddress address = client.getInetAddress();
        System.out.println("ip：" + address.getHostAddress());
    }
}
