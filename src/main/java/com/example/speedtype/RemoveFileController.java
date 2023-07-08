package com.example.speedtype;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class RemoveFileController implements Initializable {

    private String activeFileName;

    @FXML
    public ListView<String> list;
    @FXML
    public VBox vertical;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createListViewContent();
    }

    @FXML
    public void returnToSaveFile(MouseEvent event)  throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AddRemove.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void deleteFile(){
        File deletable = new File("./src/main/resources/texts_english/" + activeFileName);
        if (deletable.delete()) {
            Text deleted = new Text(activeFileName + " is deleted!");
            deleted.setFont(new Font(30));
            createListViewContent();
            vertical.getChildren().add(deleted);
        } else {
            Text notDeleted = new Text(activeFileName + " can not be deleted!");
            notDeleted.setFont(new Font(30));
            vertical.getChildren().add(notDeleted);
        }
    }

    public void createListViewContent() {
        String[] listOfFiles = new File("./src/main/resources/texts_english").list();
        list.getItems().removeAll(list.getItems());
        list.getItems().addAll(listOfFiles);
        list.getSelectionModel().selectedItemProperty().addListener(selection -> {
            activeFileName = list.getSelectionModel().getSelectedItem();
            System.out.println(activeFileName);
        });
    }
}
