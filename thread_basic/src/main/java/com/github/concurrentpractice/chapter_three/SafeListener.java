package com.github.concurrentpractice.chapter_three;

/**
 * 使用工厂方法防止this引用在构造过程中溢出
 */
public class SafeListener {
    private final EventListener listener;

    private SafeListener() {
        listener = new EventListener() {
            public void onEvent() {
                //do something
            }
        };
    }

    public static SafeListener newInstance(EventSource source) {
        SafeListener safeListener = new SafeListener();
        source.registerListener(safeListener.listener);
        return safeListener;
    }
}
