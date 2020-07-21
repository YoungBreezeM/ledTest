package com.fw.domain;

/**
 * @author yqf
 */
public class Printer {
    public void print(Callback callback, String text) {
        System.out.println("正在打印 . . . ");
        try {
            Thread.currentThread();
            Thread.sleep(3000);
        } catch (Exception e) {
        }
        callback.printFinished("打印完成");
    }
}