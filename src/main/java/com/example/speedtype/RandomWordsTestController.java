package com.example.speedtype;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Stream;

public class RandomWordsTestController {

    private int time = 30;
    private TextComparison comparison = new TextComparison(getRandomWords());
    private boolean started = false;


    @FXML
    public AnchorPane anchor;
    public TextField textBox;
    public Text baseText;
    public TextFlow timePlace;
    public Text timerText;

    public RandomWordsTestController() throws FileNotFoundException {
    }


    public void timerMethod() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                time -= 1;
                timerText.setText("Time: " + time);
                if (time == 0) {
                    timer.cancel();
                    textBox.setDisable(true);
                    int words;
                    if (comparison.getMatch())
                         words = comparison.getWrittenText().toString().split(" ").length;
                    else {
                        String correctlyWritten = comparison.getWrittenText().substring(0, comparison.getLastCorrect());
                        words = correctlyWritten.split(" ").length;
                    }
                    int finalWPM = words * 2;
                    Platform.runLater(() -> {
                        try {
                            saveResultWindow(finalWPM);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        }, 0, 1000);

    }


    @FXML
    public void initialize() throws FileNotFoundException {
        baseText.setText(comparison.getBaseText());
    }

    @FXML
    public void keyReleased(KeyEvent event) {
        if (event.getCode().equals(KeyCode.SPACE) && comparison.getMatch()) {
            textBox.setText(null);
        }
        //For detecting shift and shortcut keys to get alternate symbols
        else if (comparison.isShiftDown() && event.getCode() != KeyCode.BACK_SPACE) {
            comparison.setShiftDown(false);
            comparison.addToWritten(textBox.getText().charAt(textBox.getText().length() - 1));
            if (comparison.getBaseText().length() > comparison.getIndex()) {
                if (comparison.sameSymbolCheck() != -1) {
                    comparison.addToMistakeArray(comparison.sameSymbolCheck());
                    comparison.setMatchFalse();
                }
            }
            comparison.addToIndex();
        }
        if (!comparison.getMatch()) { //Checks whether last symbol entered is the same as the corresponding symbol in given text
            anchor.setStyle("-fx-background-color: #ff0000");
        } else {
            anchor.setStyle("-fx-background-color: #ffffff");
        }
    }

    @FXML
    public void keyPressed(KeyEvent event) throws IOException {

        if (comparison.getIndex() == 0 && !started)
            timerMethod();

        if (!event.getText().isBlank() || event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.SPACE) {
            int index = comparison.getIndex();
            int mistakes = comparison.getNrOfMistakes();

            //SPACE BAR PRESS
            if (event.getCode().equals(KeyCode.SPACE) && comparison.getMatch() && comparison.getBaseText().charAt(index) == ' ') { //Deleting last comparison from textField
                textBox.clear();
                if (!(mistakes >= 7)) {//Checks whether mistake Array is full or not
                    if (index != 0 && !(comparison.getWrittenText().charAt(index - 1) == ' ')) { // Check whether last symbol in users comparison is empty space
                        comparison.addToWritten(' ');
                        comparison.addToIndex();
                        comparison.setLastCorrect(index + 1); //Changes last deletable index to last correct spacebar press
                    }
                    if (comparison.sameSymbolCheck() != -1) {
                        comparison.addToMistakeArray(comparison.sameSymbolCheck());
                        comparison.setMatchFalse();
                    }
                }
            }

            //BACKSPACE PRESS
            else if (event.getCode() == KeyCode.BACK_SPACE) { //Deleting symbols
                if (index > 0 && index != comparison.getLastCorrect()) {
                    comparison.removeFromIndex();
                    comparison.removeFromWritten();
                    index = comparison.getIndex();
                    int indexInArray = containsNr(comparison.getMistakeIndex(), index);
                    if (indexInArray != -1) {
                        if (comparison.getMistakeIndex()[0] == index) {
                            comparison.removeFromMistakeArray();
                            comparison.setMatchTrue();
                        } else {
                            comparison.removeFromMistakeArray();
                        }
                    }
                }

            }

            //ANY OTHER KEY PRESS
            else {
                if (!(mistakes >= 7) && textBox.isEditable()) {
                    if (event.isShiftDown() || event.isShortcutDown())
                        comparison.setShiftDown(true);

                    else {
                        comparison.addToWritten(event.getText().charAt(0));
                        if (comparison.getBaseText().length() > index)
                            if (comparison.sameSymbolCheck() != -1) {
                                comparison.addToMistakeArray(comparison.sameSymbolCheck());
                                comparison.setMatchFalse();
                            }
                        comparison.addToIndex();
                    }
                }
            }

        }
    }

    @FXML
    public void returnToPrevious(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Modes.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method saveResultWindow creates the window where user can save a result
     * @param wpm words per minute
     */
    public void saveResultWindow(int wpm) throws IOException {
        Stage resultStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SaveResult.fxml"));
        Parent root = loader.load();

        SaveResultController saveResult = loader.getController();
        saveResult.showScore(wpm);
        saveResult.setText(false);

        Scene resultScene = new Scene(root);
        resultStage.setTitle("Speedtype");
        resultStage.setScene(resultScene);
        resultStage.show();

    }

    /**
     * Method getRandomWords generates 100 random words from file random_words.txt
     * @return All 100 words in one String
     */
    public static String getRandomWords() throws FileNotFoundException {
        String finalText = "";
        for (int i = 0; i < 120; i++) {
            int randomNumber = (int) (Math.random() * 84071.0);
            try (Stream<String> lines = Files.lines(Paths.get("./src/main/resources/random_words.txt"))) {
                finalText = finalText + lines.skip(randomNumber).findFirst().get() + " ";
                System.out.println(finalText);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return finalText;
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
