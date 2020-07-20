package com.fw.server;

import com.fw.controller.factory.LedThreadFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SocketServer {
    public static void main(String[] args) throws Exception {
        // 监听指定的端口
        int port = 55533;
        ServerSocket server = new ServerSocket(port);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                10,
                10,
                5,
                TimeUnit.MICROSECONDS,
                new LinkedBlockingQueue<>(10),
                new LedThreadFactory("led")
        );

        while (true) {
            // server将一直等待连接的到来
            System.out.println("server将一直等待连接的到来");
            Socket socket = server.accept();

            threadPoolExecutor.execute(()->{

              try {

                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                while (true){
                  System.out.println(objectInputStream.readObject());
                }

              } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
              }finally {
                try {
                  if(socket!=null){
                    socket.close();
                  }
                }catch (Exception e){
                  e.printStackTrace();
                }

              }

            });
        }

    }
}