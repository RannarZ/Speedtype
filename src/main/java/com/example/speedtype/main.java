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
        mainMenu(primaryStage);
        primaryStage.setTitle("SpeedType");
        primaryStage.show();
    }

    /**
     * Method main menu executes the main menu scene.
     * @param primaryStage
     */
    public static void mainMenu(Stage primaryStage) {
        Group rootMainMenu = new Group();
        Scene mainMenu = new Scene(rootMainMenu, 700, 700);

        VBox alignment = new VBox();
        alignment.setSpacing(100);
        alignment.setLayoutX((mainMenu.getWidth() - 200 )/ 2);

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
            try {
                mainTest(primaryStage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        addText.setOnMouseClicked(event -> {
            try {
                addTextWindow(primaryStage);
            } finally {
                System.out.println("okay");
            }
        });



        primaryStage.setScene(mainMenu);
    }



    /**
     * Main test window
     */
    public static void mainTest(Stage primaryStage) throws IOException {
        //Ei tuvasta caps locki iga kord.
        Group rootSpeedTestRandom = new Group();
        Scene testScene = new Scene(rootSpeedTestRandom, 700, 700);
        TextComparison writing = new TextComparison(getRandomText());

        TextField textBox = new TextField();
        textBox.setPrefWidth(500);

        TextFlow baseTextBox = new TextFlow();
        Text sampleText = new Text(writing.getBaseText());//Sample text
        baseTextBox.getChildren().add(sampleText);
        baseTextBox.setPrefWidth(500);


        TextFlow userTxt = new TextFlow(); //User text
        Text written = new Text(writing.getWrittenText().toString());
        userTxt.getChildren().add(written);

        Button returnButton = new Button("Return");
        returnButton.setPrefWidth(75);
        returnButton.setPrefHeight(25);


        returnButton.setOnMouseClicked(event -> {
            mainMenu(primaryStage);
        });



        VBox vAlignment = new VBox();
        vAlignment.setLayoutY(100);
        vAlignment.setLayoutX(100);

        vAlignment.getChildren().addAll(textBox, baseTextBox, returnButton);

        rootSpeedTestRandom.getChildren().add(vAlignment);

        AtomicLong startTime = new AtomicLong();


        textBox.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.BACK_SPACE) {
                textBox.setEditable(false);
            }
        });

        //Main speed typing algorithm
        textBox.setOnKeyReleased(event -> {
            if (writing.getIndex() == 0) {
                startTime.set(System.currentTimeMillis());
            }
            if (!event.getText().isBlank() || event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.SPACE) {
                int index = writing.getIndex();
                int mistakes = writing.getNrOfMistakes();

                //SPACE BAR PRESS
                if (event.getCode().equals(KeyCode.SPACE) && writing.getMatch() && writing.getBaseText().charAt(index) == ' ' && textBox.isEditable()) { //Deleting last writing from textField
                    textBox.clear();

                    if (!(mistakes >= 7)) {//Checks whether mistake Array is full or not
                        if (index != 0 && !(writing.getWrittenText().charAt(index - 1) == ' ')) { // Check whether last symbol in users writing is empty space
                            writing.addToWritten(event.getText());
                            writing.addToIndex();
                            writing.setLastCorrect(index + 1); //Changes last deletable index to last correct spacebar press
                        }
                        if (writing.sameSymbolCheck() != -1) {
                            writing.addToMistakeArray(writing.sameSymbolCheck());
                            writing.setMatchFalse();
                        }
                    }
                }

                //BACKSPACE PRESS
                else if (event.getCode() == KeyCode.BACK_SPACE) { //Deleting symbols
                    textBox.setEditable(true);
                    if (index > 0 && index != writing.getLastCorrect()) {
                        writing.removeFromIndex();
                        writing.removeFromWritten();
                        index = writing.getIndex();
                        int indexInArray = containsNr(writing.getMistakeIndex(), index);
                        if (indexInArray != -1) {
                            if (writing.getMistakeIndex()[0] == index) {
                                writing.removeFromMistakeArray();
                                writing.setMatchTrue();
                            } else {
                                writing.removeFromMistakeArray();
                            }
                        }
                    }

                }

                //ANY OTHER KEY PRESS
                else {
                    if (!(mistakes >= 7) && textBox.isEditable()) {
                        if ((event.isShiftDown() || event.isShortcutDown())) { //Checks whether shift key or AltGR key is pressed down
                            //Then takse symbol from textBox
                            writing.addToWritten(Character.toString(textBox.getCharacters().charAt(textBox.getCharacters().length() - 1)));
                        } else {
                            writing.addToWritten(event.getText());
                        }
                        if (writing.getBaseText().length() > index) {
                            if (writing.sameSymbolCheck() != -1) {
                                writing.addToMistakeArray(writing.sameSymbolCheck());
                                writing.setMatchFalse();
                            }
                        }
                        writing.addToIndex();
                    }
                }

                if (!writing.getMatch()) { //Checks whether last symbol entered is the same as the corresponding symbol in given text
                    testScene.setFill(Color.RED);
                } else {
                    testScene.setFill(Color.WHITE);
                }
            }
            if (writing.getNrOfMistakes() == -1 && writing.getWrittenText().length() == writing.getBaseText().length()) { //Checks whether full text is written
                textBox.setText(null);
                textBox.setDisable(true);

                double words = writing.getBaseText().split(" ").length;
                long endTime = System.currentTimeMillis();
                double finalWPM = words / ((double) (endTime - startTime.get()) / (1000.0 * 60.0)); //Calculates words per minute

                System.out.println("Final WPM is " + Math.round(finalWPM));
            }
        });
        primaryStage.setScene(testScene);
    }

    /**
     * Method addTextWindow creates the window where user can insert text into database
     * @param primaryStage main stage
     */
    public static void addTextWindow(Stage primaryStage) {
        Group addTextRoot = new Group();
        Scene addTextScene = new Scene(addTextRoot, 700, 700);

        VBox vertAlignment = new VBox();

        TextField writingBox = new TextField();
        addTextRoot.getChildren().add(writingBox);

        primaryStage.setScene(addTextScene);
    }

    /**
     * Method getRandomText finds a random text file from the directory where the texts are located
     * @return the final text
     */
    public static String getRandomText() throws IOException {
        String[] listOfFiles = new File("./src/main/resources/texts_english").list();

        int nrOfTexts = Objects.requireNonNull(listOfFiles).length;
        double randomNr = Math.random();
        int whatNrText = (int) (randomNr * nrOfTexts + 1);

        StringBuilder finalText;
        try (BufferedReader bf = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream("./src/main/resources/texts_english/" + listOfFiles[whatNrText - 1]), StandardCharsets.UTF_8))) {
            String row = bf.readLine();
            finalText = new StringBuilder();
            while (row != null) {
                finalText.append(row);
                row = bf.readLine();
            }
        }
        return finalText.toString();
    }

    /**
     * Checks whether an array contains given number
     * @param array given array
     * @param nr given number
     * @return index of given number if exists and -1 if not
     */
    public static int containsNr(int[] array, int nr){
        for (int i = 0; i < array.length; i++) {
            if (array[i] == nr)
                return i;
        }
        return -1;
    }

    public static void main(String[] args){
        launch();
    }
}
