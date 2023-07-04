package com.example.speedtype;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class AddRemoveController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public TextArea savedText;

    @FXML
    public void returnToMain(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainMenu.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void saveFile(){
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
                    saveTextToFile(filePath, savedText.getText());
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
