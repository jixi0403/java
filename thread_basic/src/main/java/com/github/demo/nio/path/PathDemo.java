package com.github.demo.nio.path;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by error on 2017/3/15.
 */
public class PathDemo {

    public static void main(String[] args) {
        //获取Path
        //绝对路径
        Path path = Paths.get("f:/test/text.txt");
        //相对路径
        path = Paths.get("test.txt");
    }
}
