package com.fw.server;

import com.fw.factory.LedThreadFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author yqf
 */
public class Client {
    public static void main(String args[]) throws Exception {
        // 要连接的服务端IP地址和端口
        String host = "127.0.0.1";
        int port = 8090;
        // 与服务端建立连接
        Socket socket = new Socket(host, port);

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                10,
                10,
                5,
                TimeUnit.MICROSECONDS,
                new LinkedBlockingQueue<>(10),
                new LedThreadFactory("led")
        );

        threadPoolExecutor.execute(()->{
            try {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                Scanner scanner = new Scanner(System.in);
                while (true){

                    System.out.println("请输入要发送的信息");
                    objectOutputStream.writeObject(scanner.nextLine());
                    objectOutputStream.flush();
                    System.out.println("服务器说：");
                    System.out.println(objectInputStream.readObject());
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

    }
}