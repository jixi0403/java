package com.github.demo.nio.mapped;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by error on 2017/3/8.
 */
public class MappedFileTest {
    private MappedFile mappedFile;

    @Before
    public void setUp() {
        mappedFile = new MappedFile("db_mapped_file");
        mappedFile.initialize();
    }

    @Test
    public void testWriteString() {
        final String str = "Hello world ____";
        mappedFile.writeString(str);
    }

    @After
    public void tearDown() {
        mappedFile.destroy();
    }
}
