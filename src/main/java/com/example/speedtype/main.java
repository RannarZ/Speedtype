package com.example.speedtype;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class main extends Application {


    @Override
    public void start(Stage primaryStage) {

        Group mainMenuRoot = new Group();
        Scene mainMenuScene = new Scene(mainMenuRoot, 700, 700);
        MainMenuWindow mainMenu = new MainMenuWindow(primaryStage, mainMenuScene, mainMenuRoot);
        mainMenu.mainMenuOnScreen();

        primaryStage.setTitle("SpeedType");
        primaryStage.show();
    }

    public static void main(String[] args){
        launch();
    }
}
