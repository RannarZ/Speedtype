package com.example.speedtype;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class main extends Application {


    @Override
    public void start(Stage primaryStage) throws IOException {
        /*
        Group mainMenuRoot = new Group();
        Scene mainMenuScene = new Scene(mainMenuRoot, 700, 700);
        MainMenuWindow mainMenu = new MainMenuWindow(primaryStage, mainMenuScene, mainMenuRoot);
        mainMenu.mainMenuOnScreen();
        */

        FXMLLoader load = new FXMLLoader(main.class.getResource("MainMenu.fxml"));
        Scene scene = new Scene(load.load(), 700, 700);




        primaryStage.setTitle("SpeedType");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args){
        launch();
    }
}
