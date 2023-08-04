package com.example.speedtype;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class main extends Application {


    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader load = new FXMLLoader(main.class.getResource("MainMenu.fxml"));
        System.out.println(load.getLocation());
        Scene scene = new Scene(load.load(), 700, 700);
        scene.getStylesheets().add(getClass().getResource("Stylesheets/TextStyle.css").toExternalForm());

        primaryStage.setTitle("SpeedType");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args){
        launch();
    }
}
