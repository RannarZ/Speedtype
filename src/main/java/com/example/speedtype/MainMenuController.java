package com.example.speedtype;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainMenuController {

    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    void speedTypeClicked(MouseEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Modes.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("Stylesheets/TextStyle.css").toExternalForm());
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void leaderboardsClicked(MouseEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Leaderboards.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("Stylesheets/TextStyle.css").toExternalForm());
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void addRemoveClicked(MouseEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AddRemove.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("Stylesheets/TextStyle.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}
