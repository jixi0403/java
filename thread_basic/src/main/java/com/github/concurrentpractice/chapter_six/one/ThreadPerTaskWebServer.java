package com.github.concurrentpractice.chapter_six.one;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by error on 2017/3/17.
 */
public class ThreadPerTaskWebServer {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(80);

        while(true) {
            final Socket client = server.accept();
            Runnable task = new Runnable() {
                public void run() {
                    handleRequest(client);
                }
            };

            new Thread(task).start();
        }
    }

    private static void handleRequest(Socket client) {
        InetAddress address = client.getInetAddress();
        System.out.println(System.currentTimeMillis() + ", ip：" + address.getHostAddress());
    }
}
