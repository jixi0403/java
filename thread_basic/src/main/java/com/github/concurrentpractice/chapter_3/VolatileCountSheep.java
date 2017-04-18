package com.github.concurrentpractice.chapter_3;

/**
 * volatile 变量，用来确保将变量的更新操作通知到其他线程
 * 当变量声明为volatile后，编译器与运行时都会注意到这个变量是共享的，因此不会讲该
 * 变量上的操作与其他内存一起重排序
 */
public class VolatileCountSheep {

    private volatile boolean asleep;

    public void count() {
        while(!asleep) {
            System.out.println("Do something");
        }
    }
}

/**
 * 在大多数处理器架构上，读取volatile变量的开销只比读非volatile变量开销
 * 略高一些
 *
 * 使用volatile的条件：
 * 1 对变量的写入操作不依赖变量的当前值，或者你能确保只有单个线程更新变量的值
 * 2 该变量不会与其他状态变量一起纳入不变形条件
 * 3 在访问变量时不需要加锁
 */
class VolatileInteger {
    private volatile int value;

    public int get() {return value;}
    public void set(int value) {this.value = value;}
}
