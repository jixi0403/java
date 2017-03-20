package com.github.demo.nio.mapped;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by error on 2017/3/16.
 */
public class MappedFileQueueTest {
    private MappedFileQueue mappedFileQueue;
    @Before
    public void setup() {
        mappedFileQueue = new MappedFileQueue("mapped_file");
    }

    @Test
    public void testLoad() {
        mappedFileQueue.load();
    }

}
