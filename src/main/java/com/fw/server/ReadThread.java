package com.fw.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fw.domain.Body;
import com.fw.domain.Led;
import javafx.scene.control.RadioButton;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Map;

/**
 * @author yqf
 */
public class ReadThread implements Runnable {


    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private List<RadioButton> radioButtons;


    public ReadThread(ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream, List<RadioButton> radioButtons) {
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;
        this.radioButtons = radioButtons;
    }

    @Override
    public void run() {
        try{
            while (true){

                String msg = objectInputStream.readObject().toString();
                System.out.println(msg);
                handel(msg);
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("客户端连接异常"+e.getMessage());
        }
    }

    private void handel(String msg){
        Body body = JSONObject.parseObject(msg, Body.class);

        switch (body.getUrl()){
            case "/changeLed":
                Led led = JSONObject.parseObject(body.getMsg().toString(), Led.class);
                RadioButton radioButton = radioButtons.get(led.getIndex());
                if(led.getStatus()){
                    radioButton.setSelected(true);
                }else {
                    radioButton.setSelected(false);
                }
                return;
            case "/getLedNum":
                body.setMsg(radioButtons.size());
                new Thread(new SendThread(objectOutputStream,body)).start();
                return;
            default:
        }
    }
}
