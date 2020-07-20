package com.fw.server;

import com.alibaba.fastjson.JSONObject;
import com.fw.domain.Body;
import javafx.scene.control.RadioButton;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;

/**
 * @author yqf
 */
public class LedServer implements Runnable {

    private int port = 8089;
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream out;
    private List<RadioButton> radioButtons;


    public LedServer(int port) {
        System.out.println("服务器启动...\n");
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public List<RadioButton> getRadioButtons() {
        return radioButtons;
    }

    public void setRadioButtons(List<RadioButton> radioButtons) {
        this.radioButtons = radioButtons;
    }

    private void init() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true){
                // 一旦有堵塞, 则表示服务器与客户端获得了连接
                socket = serverSocket.accept();
                InputStream inputStream = socket.getInputStream();
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

            }

        } catch (Exception e) {
            System.out.println("服务器异常: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        init();
    }

    private String read() throws IOException {
        // 读取客户端数据
        input = new DataInputStream(socket.getInputStream());
        //这里要注意和客户端输出流的写方法对应,否则会抛 EOFException
        String clientInputStr = input.readUTF();
        // 处理客户端数据
        System.out.println("客户端发过来的内容:" + clientInputStr);

        return clientInputStr;
    }

    private void write(String msg) throws IOException {
        // 向客户端回复信息
         out = new DataOutputStream(socket.getOutputStream());

         out.writeUTF(msg);

    }

    private void resolvePath(Body body) throws IOException {
        System.out.println(body);
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>(10);
        objectObjectHashMap.put("num",radioButtons.size());
        body.setMsg(objectObjectHashMap);
        write(JSONObject.toJSONString(body));

    }
}
