package com.example.speedtype;

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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class ChosenSpeedtypeController {

    private String text;

    private TextComparison comparison;

    @FXML
    public TextField textBox;
    public Text baseText;
    public AnchorPane anchor;

    public ChosenSpeedtypeController(){
    }

    /**
     * Method setBaseText sets the text to one that user chose in the ChooseText window
     * @param txt file name from where the text is taken
     */
    public void setBaseText(String txt) throws IOException {
        this.text = txt;
        comparison = new TextComparison(getGivenText());
        baseText.setText(comparison.getBaseText());
    }

    @FXML
    public void returnToMain(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Modes.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("Stylesheets/TextStyle.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
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
            baseText.setSelectionStart(comparison.getMistakeIndex()[0]);
            baseText.setSelectionEnd(comparison.getIndex());
            baseText.setSelectionFill(Color.RED);
        } else {
            baseText.setSelectionFill(Color.BLACK);
        }
    }

    @FXML
    public void keyPressed(KeyEvent event) throws IOException {

        if (comparison.getIndex() == 0)
            comparison.setStartTime(System.currentTimeMillis());

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
        //When text is finished
        if (comparison.getNrOfMistakes() == -1 && comparison.getWrittenText().length() == comparison.getBaseText().length()) { //Checks whether full text is written
            textBox.setText(null);
            textBox.setDisable(true);
            double words = comparison.getBaseText().split(" ").length;
            long endTime = System.currentTimeMillis();
            int finalWPM = (int) Math.round(words / ((double) (endTime - comparison.getStartTime()) / (1000.0 * 60.0))); //Calculates words per minute
            saveResultWindow(finalWPM);

        }
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

        Scene resultScene = new Scene(root);
        resultStage.setTitle("Speedtype");
        resultStage.setScene(resultScene);
        resultStage.show();

    }

    /**
     * Method getGivenText returns the text that user had chosen
     * @return the final text
     */
    public String getGivenText() throws IOException {
        System.out.println(text);
        StringBuilder finalText;
        try (BufferedReader bf = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream("./src/main/resources/texts_english/" + text), StandardCharsets.UTF_8))) {
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