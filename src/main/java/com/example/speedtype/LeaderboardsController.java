package com.example.speedtype;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class LeaderboardsController implements Initializable {

    @FXML
    TableView<ResultStructure> table;
    @FXML
    TableColumn<? extends Object, ? extends Object> number;
    @FXML
    TableColumn<? extends Object, ? extends Object> name;
    @FXML
    TableColumn<? extends Object, ? extends Object> wordsPerMinute;
    @FXML
    TableColumn<? extends Object, ? extends Object> date;

    @FXML
    public void returnToMain(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainMenu.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<ResultStructure> results = SaveResultController.readFromFile();
        for (int i = 0; i < results.size(); i++) {
            //Each result will be added to TableView aka leaderboards
            number.setCellValueFactory(new PropertyValueFactory<>("index"));
            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            wordsPerMinute.setCellValueFactory(new PropertyValueFactory<>("wpm"));
            date.setCellValueFactory(new PropertyValueFactory<>("date"));
            table.getItems().add(results.get(i));
        }
    }
}
