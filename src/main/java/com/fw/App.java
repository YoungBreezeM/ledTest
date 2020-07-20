package com.fw;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * @author yqf
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("application.fxml")));
        Scene scene = new Scene(root, 1000, 575);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getClassLoader().getResource("style/main.css")).toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("led 灯测试");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
