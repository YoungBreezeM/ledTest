package com.fw.controller;

import com.fw.controller.factory.LedThreadFactory;
import com.fw.server.LedServer;
import com.fw.server.ReadThread;
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
    private LedServer ledServer;


    public void initialize() {
        System.out.println("controller init");


    }

    public void toInitService(MouseEvent mouseEvent) throws IOException {

        Integer num = Integer.valueOf(ledNumber.getText());

        List<RadioButton> radioButtons = new ArrayList<>();

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

        startServer(radioButtons);

    }

    private void startServer(List<RadioButton> radioButtons){
        try {
            ServerSocket server = new ServerSocket(8090);
            ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                    10,
                    10,
                    5,
                    TimeUnit.MICROSECONDS,
                    new LinkedBlockingQueue<>(10),
                    new LedThreadFactory("led")
            );

            threadPoolExecutor.execute(()->{
                while (true) {
                    // server将一直等待连接的到来
                    System.out.println("server将一直等待连接的到来");
                    Socket socket = null;
                    try {
                        socket = server.accept();
                        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                        threadPoolExecutor.execute(new ReadThread(objectInputStream,objectOutputStream,radioButtons));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }


    }

}
