package com.fw.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fw.domain.HandleChannel;
import com.fw.domain.Led;
import com.fw.domain.Request;
import com.fw.domain.Response;
import com.fw.factory.LedThreadFactory;
import com.fw.server.LedServer;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Paint;

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
public class MainController {
    public TextField ledNumber;
    public Button initBtn;
    public FlowPane ledBox;
    private ThreadPoolExecutor threadPoolExecutor;
    private List<RadioButton> radioButtons;

    public MainController() {
         threadPoolExecutor = new ThreadPoolExecutor(
                10,
                10,
                5,
                TimeUnit.MICROSECONDS,
                new LinkedBlockingQueue<>(10),
                new LedThreadFactory("led")
        );
    }

    public void initialize() {
        System.out.println("controller init");

    }

    public void toInitService(MouseEvent mouseEvent) throws IOException {

        Integer num = Integer.valueOf(ledNumber.getText());

        radioButtons = new ArrayList<>();

        ledBox.getChildren().clear();

        for (Integer i = 1; i <= num; i++) {
            RadioButton radioButton = new RadioButton();
            radioButton.setText("灯" + i);
            radioButton.setTextFill(Paint.valueOf("white"));
            radioButton.setLineSpacing(10);
            radioButton.setPrefWidth(80);
            radioButton.setPrefHeight(50);
            radioButtons.add(radioButton);
            ledBox.getChildren().add(radioButton);
        }

        //处理socket 对话
        HandleChannel handleChannel =(outputStream, msg)->{
            System.out.println(msg);

            Response response = requestParsing(msg);

            if(response!=null){
                try {
                    outputStream.writeObject(response);
                    outputStream.flush();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        };

        LedServer ledServer = new LedServer(8090, handleChannel);
        threadPoolExecutor.execute(ledServer);
        System.out.println("初始化任务发布");

    }

    /**解析对话*/
    private Response requestParsing(String msg){

        Request request = JSON.parseObject(msg, Request.class);

        switch (request.getUrl()){
            case "/changeLed":
                toChangeLed(request);
                System.out.println(request);
                return new Response(200,"success");
            case "/getLedNum":
                return new Response(200,radioButtons.size());
            default:
                return null;
        }

    }

    /**改变led灯状态*/
    private void toChangeLed(Request request){

        Led led = JSONObject.parseObject(request.getBody().toString(), Led.class);
        RadioButton radioButton = radioButtons.get(led.getIndex());
        if(led.getStatus()){
            radioButton.setSelected(true);
        }else {
            radioButton.setSelected(false);
        }
    }


}
