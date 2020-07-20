package com.fw.server;

import java.io.*;
import java.net.Socket;

class HandlerThread {
    private Socket socket;

    HandlerThread(Socket client) {
        socket = client;
    }

    public String read() throws IOException {
        // 读取客户端数据
        DataInputStream input = new DataInputStream(socket.getInputStream());
        //这里要注意和客户端输出流的写方法对应,否则会抛 EOFException
        String clientInputStr = input.readUTF();
        // 处理客户端数据
        System.out.println("客户端发过来的内容:" + clientInputStr);

        input.close();

        return clientInputStr;
    }

    public void write(String msg) throws IOException {
        // 向客户端回复信息
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

        out.writeUTF(msg);

        out.close();
    }

    public void close(){
        if (socket != null) {
            try {
                socket.close();
            } catch (Exception e) {
                socket = null;
                System.out.println("服务端 finally 异常:" + e.getMessage());
            }
        }
    }
}