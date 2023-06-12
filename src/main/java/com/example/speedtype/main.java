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

public class main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Scene testScene = new Scene(root,700, 700);
        TextComparison writing = new TextComparison("Rannar oli siin!");



        TextField textBox = new TextField();
        textBox.setPrefWidth(500);
        TextFlow baseTextBox = new TextFlow();

        Text sampleText = new Text(writing.getBaseText());//Sample text
        sampleText.setFill(Color.RED);
        baseTextBox.getChildren().add(sampleText);
        TextFlow test = new TextFlow();

        TextFlow userTxt = new TextFlow(); //User text
        Text written = new Text(writing.getWrittenText().toString());
        userTxt.getChildren().add(written);


        VBox vAlignment = new VBox();
        vAlignment.setAlignment(Pos.CENTER);
        vAlignment.setLayoutY(100);
        vAlignment.setLayoutX(100);
        vAlignment.setStyle("-fx-background-color : #000000;");


        vAlignment.getChildren().add(textBox);
        vAlignment.getChildren().add(baseTextBox);
        vAlignment.getChildren().add(test);


        textBox.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE)
                textBox.setText(null);
            if(event.getCode() == KeyCode.BACK_SPACE
                    && writing.getIndex() != 0
                    && !writing.getWrittenText().toString().equals("")) {
                writing.removeFromIndex();
                writing.removeFromWritten();
                System.out.println(writing.getWrittenText());
                System.out.println(writing.getIndex());
            }
            else {
                writing.addToWritten(event.getCharacter().toLowerCase());
                writing.addToIndex();
                System.out.println(writing.getWrittenText());
                System.out.println(writing.getIndex());

            }
                }
                );




        root.getChildren().add(vAlignment);
        primaryStage.setScene(testScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
