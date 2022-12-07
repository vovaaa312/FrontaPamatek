package com.example.GUI.Dialog;

import Zamek.Zamek;
import Zamek.GPS;
import Enum.enumTypKey;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NajdiGpsWindow extends Stage {
    private TextField Xtf, Ytf;
    private String xStr, yStr;
    private Zamek najdi;
    private int rows;
    private enumTypKey enumTypKey;

    private GridPane mainGridPane;

    public NajdiGpsWindow(enumTypKey typKey) {
        this.enumTypKey = typKey;
        rows = 0;
        setTitle("Najdi");
        setResizable(false);
        setScene(start());
    }

    public Scene start() {

        VBox vBox = new VBox(12);
        vBox.setAlignment(Pos.CENTER);
        mainGridPane = new GridPane();
        mainGridPane.setAlignment(Pos.CENTER);
        mainGridPane.setPadding(new Insets(20));
        mainGridPane.setHgap(7);
        mainGridPane.setVgap(20);
        vBox.getChildren().addAll(mainGridPane);
        Xtf = addRow("X", rows++);
        Ytf = addRow("Y", rows++);

        Button ok = new Button("OK");
        Button cancel = new Button("CANCEL");
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.TOP_CENTER);
        hBox.setPrefHeight(50);
        hBox.setSpacing(12);
        cancel.setPrefSize(75, 25);
        ok.setPrefSize(75, 25);
        hBox.getChildren().addAll(ok, cancel);
        cancel.setOnAction(actionEvent -> {
            hide();
        });
        ok.setOnAction(actionEvent -> {
            if (check()) hide();
        });
        vBox.getChildren().add(hBox);
        return new Scene(vBox);
    }

    protected TextField addRow(String nazev, int row) {
        mainGridPane.add(new Label(nazev), 0, row);
        TextField textField = new TextField();
        textField.setEditable(true);
        mainGridPane.add(textField, 1, row);
        return textField;
    }

    private boolean check() {
        StringBuilder errors = new StringBuilder();
        int errCount = 0;
        if (Xtf.getText() == "" || Xtf.getText() == null) {
            errCount++;
            errors.append("X\n");
        }
        if (Ytf.getText() == "" || Ytf.getText() == null) {
            errCount++;
            errors.append("Y\n");
        }
        if (errCount == 0) {
            xStr = Xtf.getText();
            yStr = Ytf.getText();
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Nevyplnena dalsi pole:");
            alert.setContentText(errors.toString());
            alert.showAndWait();
            return false;
        }

    }

    public String getGps() {
        return xStr + " " + yStr;
    }
}
