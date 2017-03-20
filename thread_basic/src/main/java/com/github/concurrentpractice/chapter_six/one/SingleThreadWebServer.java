package com.github.concurrentpractice.chapter_six.one;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by error on 2017/3/17.
 */
public class SingleThreadWebServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(80);
        while(true) {
            Socket client = serverSocket.accept();
            handleRequest(client);
            client.close();
        }
    }

    private static void handleRequest(Socket client) {
        InetAddress address = client.getInetAddress();
        System.out.println("ip：" + address.getHostAddress());
    }
}
