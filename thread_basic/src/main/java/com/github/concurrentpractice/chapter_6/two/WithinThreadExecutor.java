package com.github.concurrentpractice.chapter_6.two;

import java.util.concurrent.Executor;

/**
 * Created by error on 2017/3/17.
 */
public class WithinThreadExecutor implements Executor {
    public void execute(Runnable command) {
        command.run();
    }
}
