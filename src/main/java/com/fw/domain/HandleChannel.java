package com.fw.domain;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author yqf
 * 处理scoket 消息
 */
public interface HandleChannel {

    /**
     * 读取消息
     * @param msg 消息
     * @param outputStream  输出流
     * */
    void channel(ObjectOutputStream outputStream, String msg);
}
