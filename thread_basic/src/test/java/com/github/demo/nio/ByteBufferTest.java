package com.github.demo.nio;

import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * Created by error on 2017/3/8.
 */
public class ByteBufferTest {

    @Test
    public void testReadWriteSwitch() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(64);
        System.out.println("Initial state: position: " + byteBuffer.position() + ", limit: " + byteBuffer.limit() +
            ", capacity: " + byteBuffer.capacity());

        byteBuffer.put("a".getBytes());
        System.out.println("After write a byte: position: " + byteBuffer.position() + ", limit: " + byteBuffer.limit() +
                ", capacity: " + byteBuffer.capacity());

        byteBuffer.flip();
        System.out.println("After flip: position: " + byteBuffer.position() + ", limit: " + byteBuffer.limit() +
                ", capacity: " + byteBuffer.capacity());

        byteBuffer.put("a".getBytes());
        System.out.println("write -> flip -> write: position: " + byteBuffer.position() + ", limit: " + byteBuffer.limit() +
                ", capacity: " + byteBuffer.capacity());

        byte b = byteBuffer.get();
        System.out.println("write -> flip -> write -> get: position: " + byteBuffer.position() + ", limit: " + byteBuffer.limit() +
                ", capacity: " + byteBuffer.capacity());
    }
}
