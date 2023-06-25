package com.example.speedtype;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.*;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class AddText {

    private final Stage primaryStage;
    private final Scene addTextScene;
    private final Group rootaddText;

    AddText(Stage primaryStage, Scene addTextScene, Group rootaddText) {
        this.primaryStage = primaryStage;
        this.addTextScene = addTextScene;
        this.rootaddText = rootaddText;
    }
    /**
     * Method addTextWindow creates the window where user can insert text into database
     */
    public void addTextWindow(Scene mainMenuScene) {

        VBox vertAlignment = new VBox();
        vertAlignment.setLayoutX(100);

        //Text input box
        TextArea writingBox = new TextArea();
        writingBox.setPrefWidth(500);
        writingBox.setPrefHeight(100);
        writingBox.setWrapText(true);

        //Buttons
        Button returnButton = new Button("Return");
        returnButton.setPrefWidth(75);
        returnButton.setPrefHeight(25);

        returnButton.setOnMouseClicked(event -> {
            primaryStage.setScene(mainMenuScene);
        });

        Button saveButton = new Button("Save text");
        saveButton.setPrefWidth(100);
        saveButton.setPrefHeight(25);

        saveButton.setOnMouseClicked(event -> {

            Stage nameStage = new Stage();
            Group fileNameRoot = new Group();
            Scene nameScene = new Scene(fileNameRoot, 300, 100);

            TextField name = new TextField();
            TextFlow givenText = new TextFlow();
            Text command = new Text("Enter file name");
            givenText.getChildren().add(command);

            Button saveFileNameButton = new Button("Save");

            saveFileNameButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    try {
                        saveTextToFile(name.getText(), writingBox.getText());
                    }
                }
            });

            VBox vAlignment = new VBox();
            vAlignment.setLayoutX(75);

            vAlignment.getChildren().addAll(command, name, saveFileNameButton);
            fileNameRoot.getChildren().add(vAlignment);
            nameStage.setScene(nameScene);
            nameStage.show();

        });

        //Horizontal alignment
        HBox hAlignment = new HBox();


        hAlignment.getChildren().addAll(returnButton, saveButton);
        vertAlignment.getChildren().addAll(writingBox,hAlignment);
        rootaddText.getChildren().add(vertAlignment);

        primaryStage.setScene(addTextScene);
    }

    public static void saveTextToFile(String fileName, String text) {
        //TODO: lepta
        try (BufferedWriter bf = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(fileName + ".txt")))) {

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
