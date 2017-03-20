package com.github.demo.netty;

import java.io.IOException;

/**
 * Created by error on 2017/3/15.
 */
public interface Server {
    void serve(int port) throws IOException;
}
