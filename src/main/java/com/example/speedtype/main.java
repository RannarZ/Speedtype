package com.example.speedtype;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class main extends Application {


    @Override
    public void start(Stage primaryStage) {

        Group mainMenuRoot = new Group();
        Scene mainMenuScene = new Scene(mainMenuRoot, 700, 700);
        MainMenu mainMenu = new MainMenu(primaryStage, mainMenuScene, mainMenuRoot);
        mainMenu.mainMenuOnScreen();

        primaryStage.setTitle("SpeedType");
        primaryStage.show();
    }

    public static void main(String[] args){
        launch();
    }
}
