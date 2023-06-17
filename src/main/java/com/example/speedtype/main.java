package com.example.speedtype;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
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
import java.util.concurrent.atomic.AtomicLong;

public class main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Scene testScene = new Scene(root,700, 700);
        TextComparison writing = new TextComparison("Following after the old man, Frink pushed open the big metal door to the main work area. The rumble of machinery, which he had heard around him every day for so long - sight of men at the machines, air filled with flash of light, waste dust, movement. There went the old man. Frink increased his pace.");

        TextField textBox = new TextField();
        textBox.setPrefWidth(500);
        TextFlow baseTextBox = new TextFlow();


        Text sampleText = new Text(writing.getBaseText());//Sample text
        sampleText.setFill(Color.RED);
        baseTextBox.getChildren().add(sampleText);
        baseTextBox.setPrefWidth(500);
        TextFlow test = new TextFlow();

        TextFlow userTxt = new TextFlow(); //User text
        Text written = new Text(writing.getWrittenText().toString());
        userTxt.getChildren().add(written);


        VBox vAlignment = new VBox();
        vAlignment.setAlignment(Pos.CENTER);
        vAlignment.setLayoutY(100);
        vAlignment.setLayoutX(100);
        vAlignment.setStyle("-fx-background-color : #000000;");

        mainTest(writing, textBox,testScene);

        vAlignment.getChildren().add(textBox);
        vAlignment.getChildren().add(baseTextBox);
        vAlignment.getChildren().add(test);






        root.getChildren().add(vAlignment);
        primaryStage.setScene(testScene);
        primaryStage.show();
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


    /**
     * Main test algorithm. Method implements an lambda function
     * @param writing Text comparison type object
     * @param textBox TextField type object where the user writes
     * @param testScene Scene where this takes place.
     */
    public static void mainTest(TextComparison writing, TextField textBox, Scene testScene) {
        //Ei tuvasta caps locki iga kord.
        AtomicLong startTime = new AtomicLong();
        textBox.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.BACK_SPACE) {
                textBox.setEditable(false);
            }
        });

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
                    if (writing.getNrOfMistakes() == -1 && writing.getWrittenText().length() == writing.getBaseText().length()){ //Checks whether full text is written
                        textBox.setText(null);
                        textBox.setDisable(true);

                        double words = writing.getBaseText().split(" ").length;
                        long endTime = System.currentTimeMillis();
                        double finalWPM = words / ((double) (endTime - startTime.get()) / (1000.0 * 60.0)); //Calculates words per minute

                        System.out.println("Final WPM is " + Math.round(finalWPM));
                    }
                }
        );
    }
    /*
    public static String getRandomText() throws IOException {
        int nrOfTexts = Objects.requireNonNull(new File("texts_english").list()).length;
        System.out.println(nrOfTexts);
        double randomNr = Math.random();
        int whatNrText = (int) (randomNr * nrOfTexts + 1);
        String finalText = "";
        try (BufferedReader bf = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream("NR" + whatNrText + ".txt"), StandardCharsets.UTF_8))) {
            finalText = bf.readLine();
        }
        return finalText;
    }
     */

    public static void main(String[] args) {
        launch();
    }
}
