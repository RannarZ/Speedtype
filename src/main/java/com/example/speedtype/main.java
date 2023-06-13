package com.example.speedtype;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.security.Key;

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


        textBox.setOnKeyReleased(event -> {

                    if (!event.getText().isBlank() || event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.SPACE) {
                        int index = writing.getIndex();

                        if (event.getCode().equals(KeyCode.SPACE)) { //Deleting last writing from textBox
                            textBox.setText(null);
                            if (index != -1 && !(writing.getWrittenText().charAt(index) == ' ')) { // Check whether last symbol in users writing is empty space
                                writing.addToWritten(event.getText());
                                writing.addToIndex();
                            }

                        } else if (event.getCode() == KeyCode.BACK_SPACE) { //Deleting symbols
                            if (index > -1) {
                                writing.removeFromIndex();
                                writing.removeFromWritten();
                            }

                        } else {
                            if (event.isShiftDown() || event.isShortcutDown()) { //Checks whether shift key or AltGR key is pressed down
                                //Then takse symbol from textBox
                                writing.addToWritten(Character.toString(textBox.getCharacters().charAt(textBox.getCharacters().length() - 1)));
                            }
                            else {
                                writing.addToWritten(event.getText());
                            }
                            writing.addToIndex();
                        }
                        //Siin viga veel TEGELE!!!!
                        if (writing.sameSymbolCheck() == -1) { //Checks whether last symbol entered is the same as the corresponding symbol in given text
                            testScene.setFill(Color.RED);
                        }
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
