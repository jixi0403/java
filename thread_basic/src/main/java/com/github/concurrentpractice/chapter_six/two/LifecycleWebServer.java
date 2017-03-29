package com.github.concurrentpractice.chapter_six.two;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

/**
 * Created by error on 2017/3/17.
 */
public class LifecycleWebServer {
    private final ExecutorService executor = Executors.newFixedThreadPool(10);

    public void start(String[] args) throws IOException {
        final ServerSocket server = new ServerSocket(80);

        while(true) {
            try {
                final Socket client = server.accept();
                executor.execute(new Runnable() {
                    public void run() {
                        handleRequest(client);
                    }
                });
            } catch (RejectedExecutionException e) {
                if(!executor.isShutdown()) {
                    executor.shutdown();
                }
            }
        }
    }

    private void stop() {
        executor.shutdown();
    }

    private void handleRequest(Socket connection) {
        Request req = readRequest(connection);
        if(isShutdownRequest(req)) {
            stop();
        } else {
            dispatchRequest(req);
        }
    }

    private Request readRequest(Socket connection) {
        return new Request();
    }

    private boolean isShutdownRequest(Request req) {
        return req == null ? true : false;
    }

    private void dispatchRequest(Request req) {

    }

    class Request {

    }
}
