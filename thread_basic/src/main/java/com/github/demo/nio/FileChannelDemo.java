package com.github.demo.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by error on 2017/3/8.
 */
public class FileChannelDemo {

    public static void main(String[] args) {
        RandomAccessFile randomFile = null;
        FileChannel fileChannel = null;
        ByteBuffer byteBuffer = ByteBuffer.allocate(64);

        try {
            randomFile = new RandomAccessFile("file_channel.txt", "rw");
            fileChannel = randomFile.getChannel();
            System.out.println("Current position: " + fileChannel.position());

            while(fileChannel.read(byteBuffer) != -1) {
                System.out.println("Read " + byteBuffer.position());
                byteBuffer.flip();
            }

            byteBuffer.put("Hello world".getBytes());
            byteBuffer.flip();
            fileChannel.write(byteBuffer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                randomFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
