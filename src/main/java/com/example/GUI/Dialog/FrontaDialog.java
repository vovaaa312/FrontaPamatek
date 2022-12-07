package com.example.GUI.Dialog;

import Pamatky.FrontaPamatek;
import Enum.enumTypProhlidky;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.Iterator;

public class FrontaDialog extends Stage {
    private FrontaPamatek fronta;
    private GridPane mainGridPane;
    private int rows;

    public FrontaDialog(FrontaPamatek abstrHeap) {
        this.fronta = abstrHeap;
        setTitle("Fronta");

        setHeight(150);
        setResizable(false);
        setScene(start());
    }

    private Scene start() {
        VBox vBox = new VBox(12);
        vBox.setAlignment(Pos.CENTER);

        mainGridPane = new GridPane();
        mainGridPane.setAlignment(Pos.CENTER);
        mainGridPane.setPadding(new Insets(20));
        mainGridPane.setHgap(7);
        mainGridPane.setVgap(20);
        mainGridPane.add(new Label("Poloha: " + fronta.getPoloha()), 0, rows);
        rows++;
        vBox.getChildren().addAll(mainGridPane);

        Button zpristupniMax = new Button("Zpristupni max");
        Button odeberMax = new Button("Odeber max");

        zpristupniMax.setOnAction(actionEvent -> {
            zpristupniMax();
        });

        odeberMax.setOnAction(actionEvent -> {
            odeberMax();
        });

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.TOP_CENTER);
        hBox.setPrefHeight(50);
        hBox.setSpacing(12);

        zpristupniMax.setPrefSize(125, 25);
        odeberMax.setPrefSize(125, 25);
        hBox.getChildren().addAll(zpristupniMax, odeberMax);
        hBox.setPadding(new Insets(10));
        vBox.getChildren().add(hBox);

        return new Scene(vBox);
    }

    private void odeberMax() {
        fronta.odeberMax();

    }

    private void zpristupniMax() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Maxiamalni priorita: ");
        alert.setHeaderText(fronta.zpristupniMax().toString());

        alert.showAndWait();

    }




}
