package com.github.concurrentpractice.chapter_3;

/**
 * 1、NoVisibility可能会持续循环下去，因为读线程可能永远看不到ready的值。
 * 2、NoVisibility可能会输出0(可能会被重排序）ready=true;number=42;
 *
 * 在没有同步的情况下，编译器、处理器以及运行时等都可能对操作的执行顺序进行一些意想不到的调整
 * 在缺乏足够同步的多线程程序中，想要对内存操作的执行顺序进行判断几乎无法得到正确的结论
 */
public class NoVisibility {

    private static boolean ready;
    private static int number;

    private static class ReaderThread extends Thread {

        @Override
        public void run() {
            while(!ready) {
                Thread.yield();
            }

            System.out.println(number);
        }
    }

    public static void main(String[] args) {
        new ReaderThread().start();
        number = 42;
        ready = true;
    }
}
