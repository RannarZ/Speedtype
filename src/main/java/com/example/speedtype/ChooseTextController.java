package com.example.speedtype;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ChooseTextController implements Initializable {

    private String activeFileName;

    @FXML
    ListView<String> list;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createListViewContent();
    }

    @FXML
    public void returnToPrevious(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Modes.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void goToSpeedtype(MouseEvent event) throws IOException {
        if (activeFileName != null) {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("ChosenSpeedtype.fxml")));
            Parent root = loader.load();

            ChosenSpeedtypeController chosenST = loader.getController();
            chosenST.setBaseText(activeFileName);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }


    }

    public void createListViewContent() {
        String[] listOfFiles = new File("./src/main/resources/texts_english").list();
        list.getItems().addAll(listOfFiles);
        list.getSelectionModel().selectedItemProperty().addListener(selection -> {
            activeFileName = list.getSelectionModel().getSelectedItem();
        });
    }
}
