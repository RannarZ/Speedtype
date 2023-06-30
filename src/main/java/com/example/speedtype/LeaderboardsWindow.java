package com.example.speedtype;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public record LeaderboardsWindow(Stage primaryStage, Scene leaderboardsScene,
                                 Group rootLeaderboards) {

    public void windowLB(Scene mainMenuScene) {
        //ScrollPane for scrollable leaderboard
        VBox vAlignment = new VBox();
        TextFlow titleBox = new TextFlow();
        Text textTitle = new Text("Full texts");
        titleBox.getChildren().add(textTitle);

        Button returnButton = new Button("Return");
        returnButton.setPrefWidth(75);
        returnButton.setPrefHeight(25);

        returnButton.setOnMouseClicked(event -> {
            primaryStage.setScene(mainMenuScene);
        });

        rootLeaderboards.getChildren().addAll(titleBox, returnButton);
        primaryStage.setScene(leaderboardsScene);
    }

}
