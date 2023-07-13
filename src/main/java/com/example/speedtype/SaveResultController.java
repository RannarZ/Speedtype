package com.example.speedtype;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;

public class SaveResultController {

    private static boolean text = true; //For detecting whether saving a full text score or random words score

    @FXML
    Button closeButton;
    @FXML
    Text wpmMessage;
    @FXML
    TextField nameField;

    @FXML
    public void closeWindow() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void showScore(int wpm) {
        wpmMessage.setText("Your WPM is " + wpm);
    }

    @FXML
    public void saveScore() {
        String[] arrayMessage = wpmMessage.getText().split(" ");
        int nr = Integer.parseInt(arrayMessage[arrayMessage.length - 1].strip());
        String name = nameField.getText();
        ArrayList<ResultStructure> scores = new ArrayList<>();
        if (text)
        scores = readFromFile("./src/main/resources/LeaderboardFiles/LeaderboardText.txt");
        else
            scores = readFromFile("./src/main/resources/LeaderboardFiles/LeaderboardWords.txt");

        ResultStructure currentScore = new ResultStructure(0, name, nr, LocalDate.now());
        for (int i = 0; i <= scores.size(); i++) {
            if(i == scores.size() || currentScore.getWpm() > scores.get(i).getWpm() ) {
                currentScore.setIndex(i + 1);
                scores.add(i, currentScore);
                for (int j = i + 1; j < scores.size(); j++) {
                    scores.get(j).setIndex(j + 1);
                }
                break;
            }
        }

        saveScoreToFile(scores);
        closeWindow();
    }

    public static void saveScoreToFile(ArrayList<ResultStructure> scores) {
        String filePath;
        if (text)
            filePath = "./src/main/resources/LeaderboardFiles/LeaderboardText.txt";
        else
            filePath = "./src/main/resources/LeaderboardFiles/LeaderboardWords.txt";
        try (BufferedWriter bf = new BufferedWriter(
                new OutputStreamWriter(
                new FileOutputStream(filePath), StandardCharsets.UTF_8))) {

            for (ResultStructure score : scores) {
                bf.write(score.getIndex() + ";" + score.getName() + ";" + score.getWpm() + ";" + score.getDate() + "\n");
            }
            System.out.println("All saved!");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Method readFromFile reads every result from leaderboards
     * @return ArrayList of every score in the leaderboards file
     */
    public static ArrayList<ResultStructure> readFromFile(String filePath) {
        ArrayList<ResultStructure> scores = new ArrayList<>();

        try (BufferedReader bf = new BufferedReader(
                new InputStreamReader(
                new FileInputStream(filePath), StandardCharsets.UTF_8))) {
            String row = bf.readLine();
            while (row != null) {
                String[] rowList = row.split(";");
                int index = Integer.parseInt(rowList[0]);
                String name = rowList[1];
                int wpm = Integer.parseInt(rowList[2]);
                LocalDate date = LocalDate.parse(rowList[3]);
                ResultStructure result = new ResultStructure(index, name, wpm, date);
                scores.add(result);
                row = bf.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scores;
    }

    public void setText(boolean text) {
        SaveResultController.text = text;
    }
}
