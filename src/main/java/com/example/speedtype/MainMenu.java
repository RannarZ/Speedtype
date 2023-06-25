package com.example.speedtype;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenu {
    private final Stage primaryStage;
    private final Scene mainMenuScene;
    private final Group rootMainMenu;

    MainMenu(Stage primaryStage, Scene mainMenuScene, Group rootMainMenu) {
        this.primaryStage = primaryStage;
        this.mainMenuScene = mainMenuScene;
        this.rootMainMenu = rootMainMenu;
    }


    /**
     * Method mainMenuOnScreen executes the main menu scene.
     */
    public void mainMenuOnScreen() {
        VBox alignment = new VBox();
        alignment.setSpacing(100);
        alignment.setLayoutX((mainMenuScene.getWidth() - 200 )/ 2);

        Button speedType = new Button("Speedtype");
        speedType.setPrefWidth(200);
        speedType.setPrefHeight(50);

        Button leaderboard = new Button("Leaderboard");
        leaderboard.setPrefWidth(200);
        leaderboard.setPrefHeight(50);

        Button addText = new Button("Add text");
        addText.setPrefWidth(200);
        addText.setPrefHeight(50);

        alignment.getChildren().addAll(speedType, leaderboard, addText);





        rootMainMenu.getChildren().add(alignment);

        speedType.setOnMouseClicked(event -> {
            Group rootTypeTest = new Group();
            Scene typeTestRanScene = new Scene(rootTypeTest, 700, 700);
            TypeTest typeTest = new TypeTest(primaryStage, typeTestRanScene, rootTypeTest);
            try {
                typeTest.typeTest(mainMenuScene);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                System.out.println("To speed typing window");
            }

        });

        addText.setOnMouseClicked(event -> {
            Group rootAddText = new Group();
            Scene addTextScene = new Scene(rootAddText, 700, 700);
            AddText addTextW = new AddText(primaryStage, addTextScene, rootAddText);
            try {
                addTextW.addTextWindow(mainMenuScene);
            } finally {
                System.out.println("To text adding window");
            }
        });



        primaryStage.setScene(mainMenuScene);
    }

}
