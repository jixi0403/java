package com.github.demo.nio.mapped;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.statements.RunAfters;

/**
 * Created by error on 2017/3/8.
 */
public class MappedFileTest {
    private MappedFile mappedFile;

    @Before
    public void setUp() {
        mappedFile = new MappedFile("db_mapped_file", 100000);
        mappedFile.initialize();
    }

    @Test
    public void testWriteString() {
        final String str = "Hello world ____";
        mappedFile.writeString(str);
    }

    @Test
    public void testMultiThreadsAppend() throws InterruptedException {
        Thread[] threads = new Thread[10000];
        for(int i = 0; i < 10000; i++) {
            threads[i] = new Thread(new Runnable() {
                public void run() {
                    //synchronized (mappedFile) {
                    mappedFile.append("helloiiiii".getBytes());
                    //}
                }
            });

            threads[i].start();
        }

        for(int i = 0; i < 10000; i++) {
            threads[i].join();
        }
    }

    @After
    public void tearDown() {
        mappedFile.destroy();
    }
}
