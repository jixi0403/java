package com.github.demo.nio.mapped;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by error on 2017/3/8.
 */
public class MappedFile {

    private final String filePath;
    private FileChannel fileChannel;
    private MappedByteBuffer mappedByteBuffer;

    public MappedFile(final String filePath) {
        this.filePath = filePath;
    }

    public boolean initialize() {
        try {
            RandomAccessFile file = new RandomAccessFile(this.filePath, "rw");
            this.fileChannel = file.getChannel();
            this.mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 1024 * 1024);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
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
