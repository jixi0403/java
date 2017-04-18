package com.github.reflection.classloader;

/**
 * Created by Sunshine_wx on 17/4/18.
 */
public class MainClass {
    public static void main(String[] args) {
        ClassLoader classLoader = MainClass.class.getClassLoader();
        try {
            Class aClass = classLoader.loadClass("com.github.reflection.classloader.MyClass");
            System.out.println("aClass.getName() = " + aClass.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
