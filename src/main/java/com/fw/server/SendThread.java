package com.fw.server;

import com.fw.domain.Body;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author yqf
 */
public class SendThread implements Runnable {

    private ObjectOutputStream objectOutputStream;
    private Body body;

    public SendThread(ObjectOutputStream objectOutputStream, Body body) {
        this.objectOutputStream = objectOutputStream;
        this.body = body;
    }

    @Override
    public void run() {
        try {
                objectOutputStream.writeObject(body);
                objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
