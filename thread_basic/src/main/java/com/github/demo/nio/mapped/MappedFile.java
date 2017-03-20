package com.github.demo.nio.mapped;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by error on 2017/3/8.
 */
public class MappedFile {

    private final String filePath;
    private final int fileSize;
    private FileChannel fileChannel;
    private MappedByteBuffer mappedByteBuffer;
    private final AtomicInteger writePosition = new AtomicInteger(0);

    public MappedFile(final String filePath, final int fileSize) {
        this.filePath = filePath;
        this.fileSize = fileSize;
    }

    public boolean initialize() {
        try {
            RandomAccessFile file = new RandomAccessFile(this.filePath, "rw");
            this.fileChannel = file.getChannel();
            this.mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, fileSize);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public void writeString(String content) {
        ByteBuffer byteBuffer = this.mappedByteBuffer.slice();
        byteBuffer.put(content.getBytes());
    }

    public void append(byte[] bytes) {
        ByteBuffer byteBuffer = this.mappedByteBuffer.slice();
        byteBuffer.position(writePosition.getAndAdd(bytes.length));
        byteBuffer.put(bytes);
    }

    public void destroy() {
        if(this.fileChannel != null) {
            try {
                this.fileChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
