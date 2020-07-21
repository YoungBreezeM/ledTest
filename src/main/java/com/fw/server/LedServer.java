package com.fw.server;

import com.fw.domain.HandleChannel;
import com.fw.factory.LedThreadFactory;
import com.sun.security.ntlm.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author yqf
 */
public class LedServer implements Runnable {
    private Integer port;
    private HandleChannel handleChannel;
    private Server server;
    private Socket socket;

    public LedServer(Integer port, HandleChannel handleChannel) {
        this.port = port;

        this.handleChannel = handleChannel;

    }

    @Override
    public void run() {
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("服务已创建" + server.getLocalSocketAddress());
            while (true) {
                try {
                    if (server.isClosed()){
                        break;
                    }
                    socket = server.accept();

                    System.out.println("客户端已连接" + socket.getRemoteSocketAddress());


                    ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

                    connectChannel(objectOutputStream,objectInputStream);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
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
    }

    /**服务器与客户端通信通道*/
    private void connectChannel(ObjectOutputStream outputStream, ObjectInputStream objectInputStream) {
        while (true) {
            try {
                handleChannel.channel(
                        outputStream,
                        objectInputStream.readObject().toString()
                );

            } catch (Exception e) {
                System.out.println(e.getMessage());
                break;
            }
        }
        System.out.println(socket.getRemoteSocketAddress()+"通信断开>>>");

    }
}
