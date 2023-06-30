package com.example.speedtype;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.*;
import java.nio.charset.StandardCharsets;

public record AddTextWindow(Stage primaryStage, Scene addTextScene,
                            Group rootaddText) {

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

        //Save button clicked on main window
        saveButton.setOnMouseClicked(event -> {

            Stage nameStage = new Stage();
            Group fileNameRoot = new Group();
            Scene nameScene = new Scene(fileNameRoot, 300, 100);

            TextField name = new TextField();
            TextFlow givenText = new TextFlow();
            Text command = new Text("Enter file name");
            givenText.getChildren().add(command);

            Button saveFileNameButton = new Button("Save");

            //Save button clicked on name window
            saveFileNameButton.setOnMouseClicked(event1 -> {

                try {
                    String filePath = "./src/main/resources/texts_english/" + name.getText() + ".txt";
                    File file = new File(filePath);
                    if (file.exists()) {
                        Text repeat = new Text("File already exists!");
                        givenText.getChildren().remove(command);
                        givenText.getChildren().add(repeat);
                    } else {
                        saveTextToFile(filePath, writingBox.getText());
                        nameStage.close();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            VBox vAlignment = new VBox();
            vAlignment.setLayoutX(75);

            vAlignment.getChildren().addAll(givenText, name, saveFileNameButton);
            fileNameRoot.getChildren().add(vAlignment);
            nameStage.setScene(nameScene);
            nameStage.show();

        });

        //Horizontal alignment
        HBox hAlignment = new HBox();


        hAlignment.getChildren().addAll(returnButton, saveButton);
        vertAlignment.getChildren().addAll(writingBox, hAlignment);
        rootaddText.getChildren().add(vertAlignment);

        primaryStage.setScene(addTextScene);
    }

    /**
     * Method saveTextToFile saves given text to a new .txt file
     *
     * @param filePath To where and which file text will be saved
     * @param text     The text that will be saved
     */
    public static void saveTextToFile(String filePath, String text) {
        try (BufferedWriter bf = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(filePath), StandardCharsets.UTF_8))) {
            bf.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
