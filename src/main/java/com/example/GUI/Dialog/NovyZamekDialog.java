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


public class NovyZamekDialog extends Stage {
    private TextField idTf, nazevTf, XTf, YTf;
    private Zamek novyZamek;
    private GridPane mainGridPane;
    private int rows;
    private enumTypKey enumTypKey;

    public NovyZamekDialog(enumTypKey typKey) {
        enumTypKey = typKey;
        novyZamek = null;
        rows = 0;
        setTitle("Novy");
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
        idTf = addRow("Id:", rows++);
        nazevTf = addRow("Nazev:", rows++);
        XTf = addRow("GPS-x:", rows++);
        YTf = addRow("GPS-y:", rows++);
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
        if (nazevTf.getText() == "" || nazevTf.getText() == null) {
            errCount++;
            errors.append("Nazev\n");
        }
        if (idTf.getText() == "" || idTf.getText() == null) {
            errCount++;
            errors.append("ID\n");
        }
        if (XTf.getText() == "" || XTf.getText() == null) {
            errCount++;
            errors.append("X\n");
        }
        if (YTf.getText() == "" || YTf.getText() == null) {
            errCount++;
            errors.append("Y\n");
        }
        if (errCount == 0) {
            int X = Integer.valueOf(XTf.getText());
            int Y = Integer.valueOf(YTf.getText());
            novyZamek = new Zamek(idTf.getText(), nazevTf.getText(), new GPS(X, Y), enumTypKey);
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


    public Zamek getNovyZamek() {
        return novyZamek;
    }
}
