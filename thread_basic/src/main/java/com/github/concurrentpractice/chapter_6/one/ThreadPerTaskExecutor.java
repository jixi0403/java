package com.github.concurrentpractice.chapter_6.one;

import java.util.concurrent.Executor;

/**
 * Created by error on 2017/3/17.
 */
public class ThreadPerTaskExecutor implements Executor {
    public void execute(Runnable command) {
        new Thread(command).start();
    }
}
