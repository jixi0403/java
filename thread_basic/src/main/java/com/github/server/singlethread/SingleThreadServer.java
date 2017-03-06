package com.github.server.singlethread;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Administrator on 2017/3/6.
 */
public class SingleThreadServer implements Runnable {
    protected int serverPort = 8080;
    protected ServerSocket serverSocket = null;
    protected volatile boolean isStopped = false;
    protected Thread runningThread = null;

    public SingleThreadServer(int port) {
        this.serverPort = port;
    }

    public void run() {
        synchronized(this) {
            this.runningThread = Thread.currentThread();
        }

        openServerSocket();

        while(!isStopped()) {
            Socket clientSocket = null;
            try {
                clientSocket = this.serverSocket.accept();
                processClientRequest(clientSocket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            e.printStackTrace();
            if(!isStopped()) {
                stop();
            }
        }
    }

    private void processClientRequest(Socket clientSocket) throws IOException {
        InputStream input = clientSocket.getInputStream();
        OutputStream output = clientSocket.getOutputStream();

        long time = System.currentTimeMillis();
        output.write(("HTTP/1.1 200 OK\n\n<html><body>" + "Singlethreaded Server: " +
                time + "</body></html>").getBytes());
        input.close();
        output.close();
        System.out.println("Request processed: " + time);
    }

    private synchronized void stop() {
        this.isStopped = true;

        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

    public static void main(String[] args) {
        SingleThreadServer server = new SingleThreadServer(8080);
        Thread thread = new Thread(server, "socket server");
        thread.start();
        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Stopping Server");
        server.stop();
    }
}
