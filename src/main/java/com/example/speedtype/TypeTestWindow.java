package com.example.speedtype;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public record TypeTestWindow(Stage primaryStage, Scene typeTestSceneRandom,
                             Group rootSpeedTestRandom) {

    /**
     * Main test window
     */
    public void typeTest(Scene mainMenuScene) throws IOException {
        //Ei tuvasta caps locki iga kord.
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
            primaryStage.setScene(mainMenuScene);
        });


        VBox vAlignment = new VBox();
        vAlignment.setLayoutY(100);
        vAlignment.setLayoutX(100);

        vAlignment.getChildren().addAll(textBox, baseTextBox, returnButton);

        rootSpeedTestRandom.getChildren().add(vAlignment);

        AtomicLong startTime = new AtomicLong();
        AtomicBoolean shiftDown = new AtomicBoolean(false);
        //Main speed typing algorithm
        textBox.setOnKeyPressed(event -> {
            if (writing.getIndex() == 0)
                startTime.set(System.currentTimeMillis());

            if (!event.getText().isBlank() || event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.SPACE) {
                int index = writing.getIndex();
                int mistakes = writing.getNrOfMistakes();

                //SPACE BAR PRESS
                if (event.getCode().equals(KeyCode.SPACE) && writing.getMatch() && writing.getBaseText().charAt(index) == ' ') { //Deleting last writing from textField
                    textBox.clear();
                    if (!(mistakes >= 7)) {//Checks whether mistake Array is full or not
                        if (index != 0 && !(writing.getWrittenText().charAt(index - 1) == ' ')) { // Check whether last symbol in users writing is empty space
                            writing.addToWritten(' ');
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
                        if (event.isShiftDown() || event.isShortcutDown())
                            shiftDown.set(true);

                        else {
                            writing.addToWritten(event.getText().charAt(0));
                            if (writing.getBaseText().length() > index)
                                if (writing.sameSymbolCheck() != -1) {
                                    writing.addToMistakeArray(writing.sameSymbolCheck());
                                    writing.setMatchFalse();
                                }
                            writing.addToIndex();
                        }
                    }
                }

                if (!writing.getMatch())  //Checks whether last symbol entered is the same as the corresponding symbol in given text
                    typeTestSceneRandom.setFill(Color.RED);
                else
                    typeTestSceneRandom.setFill(Color.WHITE);

            }
            //When text is finished
            if (writing.getNrOfMistakes() == -1 && writing.getWrittenText().length() == writing.getBaseText().length()) { //Checks whether full text is written
                textBox.setText(null);
                textBox.setDisable(true);

                double words = writing.getBaseText().split(" ").length;
                long endTime = System.currentTimeMillis();
                double finalWPM = words / ((double) (endTime - startTime.get()) / (1000.0 * 60.0)); //Calculates words per minute
                System.out.println("Final WPM is " + Math.round(finalWPM));
            }
        });


        textBox.setOnKeyReleased(event -> {
            if (event.getCode().equals(KeyCode.SPACE) && writing.getMatch()) {
                textBox.setText(null);
            }
            //For detecting shift and shortcut keys to get alternate symbols
            else if (shiftDown.get() && event.getCode() != KeyCode.BACK_SPACE) {
                shiftDown.set(false);
                writing.addToWritten(textBox.getText().charAt(textBox.getText().length() - 1));
                if (writing.getBaseText().length() > writing.getIndex()) {
                    if (writing.sameSymbolCheck() != -1) {
                        writing.addToMistakeArray(writing.sameSymbolCheck());
                        writing.setMatchFalse();
                    }
                }
                writing.addToIndex();
            }
            if (!writing.getMatch()) { //Checks whether last symbol entered is the same as the corresponding symbol in given text
                typeTestSceneRandom.setFill(Color.RED);
            } else {
                typeTestSceneRandom.setFill(Color.WHITE);
            }
        });
        primaryStage.setScene(typeTestSceneRandom);
    }

    /**
     * Method getRandomText finds a random text file from the directory where the texts are located
     *
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
     *
     * @param array given array
     * @param nr    given number
     * @return index of given number if exists and -1 if not
     */
    public static int containsNr(int[] array, int nr) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == nr)
                return i;
        }
        return -1;
    }
}
