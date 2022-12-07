package com.example.GUI.MainWindow;

import java.net.URL;
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;

import Generator.Generator;
import Pamatky.Pamatky;
import Enum.enumTypKey;
import Zamek.Zamek;
import com.example.GUI.Dialog.FrontaDialog;
import com.example.GUI.Dialog.NajdiGpsWindow;
import com.example.GUI.Dialog.NovyZamekDialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import Enum.enumTypProhlidky;
import Pamatky.FrontaPamatek;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button FirsBTN;

    @FXML
    private Button LastBTN;

    @FXML
    private ListView<Zamek> MainListView;

    @FXML
    private Button NextBTN;

    @FXML
    private Button PreviousBTN;

    @FXML
    private Button importDat;

    @FXML
    private Button najdiNejbliz;

    @FXML
    private Button najdiZamek;

    @FXML
    private Button odeberZamek;

    @FXML
    private Button prebuduj;

    @FXML
    private TextField prebudujTf;

    @FXML
    private Button vlozZamek;

    @FXML
    private Button zrus;

    @FXML
    private Button generujBTN;

    @FXML
    private Slider generujSlider;

    @FXML
    private Button fronta;

    @FXML
    private TextField xTf;

    @FXML
    private TextField yTf;

    @FXML
    private CheckBox defoltCheckBox;

    @FXML
    private CheckBox souborFrontaCheckBox;


    private final Pamatky pamatky = new Pamatky();


    FrontaPamatek frontaZamku;

    @FXML
    void OnClickFronta(ActionEvent event) {

        if (souborFrontaCheckBox.isSelected()) souborFronta();
        else pamatkyFronta();
    }

    private void pamatkyFronta() {
        if (!defoltCheckBox.isSelected() && checkCoord()) {
            frontaZamku = new FrontaPamatek(xTf.getText() + " " + yTf.getText());
        } else if (defoltCheckBox.isSelected()) {
            frontaZamku = new FrontaPamatek();

        }
        frontaZamku.nactiPamatky(pamatky);
        FrontaDialog dialog = new FrontaDialog(frontaZamku);
        dialog.showAndWait();
    }

    private void souborFronta() {
        try {
            TextInputDialog textInputDialog = new TextInputDialog();
            textInputDialog.setTitle("Import");
            textInputDialog.setHeaderText("Nazev souboru:");
            Optional<String> res = textInputDialog.showAndWait();
            if (res.isPresent()) {
                if (!defoltCheckBox.isSelected() && checkCoord()) {
                    frontaZamku = new FrontaPamatek(xTf.getText() + " " + yTf.getText());
                } else if (defoltCheckBox.isSelected()) {
                    frontaZamku = new FrontaPamatek();

                }
                frontaZamku.nactiSoubor(res.get());
                FrontaDialog dialog = new FrontaDialog(frontaZamku);
                dialog.showAndWait();

            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(e.getLocalizedMessage());

            alert.showAndWait();
        }
    }

    private boolean checkCoord() {
        int i = 0;
        String error = "";
        if (xTf == null || xTf.getText() == "") {
            i++;
            error += "X\n";
        }
        if (yTf == null || yTf.getText() == "") {
            i++;
            error += "Y\n";
        }
        if (i != 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Vyplnte dalsi pole:");
            alert.setContentText(error);

            alert.showAndWait();
            return false;
        }
        return true;
    }

    @FXML
    void OnClickNajdiNejbliz(ActionEvent event) {
        try {
            NajdiGpsWindow najdiGpsWindow = new NajdiGpsWindow(pamatky.getTypKey());
            najdiGpsWindow.showAndWait();

            najdiGpsWindow.close();
            Zamek najdi = null;
            if (najdiGpsWindow.getGps() != null && najdiGpsWindow.getGps() != "") {
                najdi = pamatky.najdiNejbliz(najdiGpsWindow.getGps());
                MainListView.getSelectionModel().select(najdi);
                MainListView.scrollTo(najdi);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Nejblizsi");
                alert.setHeaderText(najdi.toString());
                alert.showAndWait();
            }

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(e.getLocalizedMessage());

            alert.showAndWait();
        }
    }

    @FXML
    void OnClickNajdiZamek(ActionEvent event) {
        try {
            TextInputDialog textInputDialog = new TextInputDialog();
            textInputDialog.setTitle("Najdi");
            textInputDialog.setHeaderText("Zadejte nazev zamku");
            Optional<String> name = textInputDialog.showAndWait();
            if (name.isPresent()) {
                Zamek najdeny = pamatky.najdiZamek(name.get());
                MainListView.getSelectionModel().select(najdeny);
                MainListView.scrollTo(najdeny);
            }


        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(e.getLocalizedMessage());

            alert.showAndWait();
        }
    }

    @FXML
    void OnClickImportDat(ActionEvent event) {
        TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.setTitle("Import");
        textInputDialog.setHeaderText("Nazev souboru:");
        Optional<String> res = textInputDialog.showAndWait();
        if (res.isPresent()) {
            int i = 0;
            try {
                i = pamatky.importDatTXT(res.get());
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText(e.getLocalizedMessage());

                alert.showAndWait();
            }

            if (i > 0) draw();
        }


    }

    @FXML
    void OnClickLastBTN(ActionEvent event) {
        try {
            MainListView.getSelectionModel().selectLast();
            MainListView.scrollTo(MainListView.getSelectionModel().getSelectedItem());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(e.getLocalizedMessage());

            alert.showAndWait();
        }
    }

    @FXML
    void OnClickFirsBTN(ActionEvent event) {
        try {
            MainListView.getSelectionModel().selectFirst();
            MainListView.scrollTo(MainListView.getSelectionModel().getSelectedItem());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(e.getLocalizedMessage());

            alert.showAndWait();
        }
    }

    @FXML
    void OnClickNextBTN(ActionEvent event) {
        try {
            MainListView.getSelectionModel().selectNext();
            MainListView.scrollTo(MainListView.getSelectionModel().getSelectedItem());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(e.getLocalizedMessage());

            alert.showAndWait();
        }
    }

    @FXML
    void OnClickPreviousBTN(ActionEvent event) {
        try {
            MainListView.getSelectionModel().selectPrevious();
            MainListView.scrollTo(MainListView.getSelectionModel().getSelectedItem());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(e.getLocalizedMessage());

            alert.showAndWait();
        }
    }

    @FXML
    void OnClickOdeberZamek(ActionEvent event) {
        try {
            switch (pamatky.getTypKey()) {
                case NAZEV -> pamatky.odeberZamek(MainListView.getSelectionModel().getSelectedItem().getNazev());
                case GPS ->
                        pamatky.odeberZamek(String.valueOf(MainListView.getSelectionModel().getSelectedItem().getGps()));
            }
            draw();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(e.getLocalizedMessage());

            alert.showAndWait();
        }
    }

    @FXML
    void OnClickPrebuduj(ActionEvent event) {
        try {
            if (pamatky.getTypKey() == enumTypKey.NAZEV) pamatky.nastavKlic(enumTypKey.GPS);
            else if (pamatky.getTypKey() == enumTypKey.GPS) pamatky.nastavKlic(enumTypKey.NAZEV);
            pamatky.prebuduj();
            prebudujTf.setText(pamatky.getTypKey().toString());
            draw();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(e.getLocalizedMessage());

            alert.showAndWait();
        }
    }

    @FXML
    void OnClickVlozZamek(ActionEvent event) {
        try {
            NovyZamekDialog dialog = new NovyZamekDialog(pamatky.getTypKey());
            dialog.showAndWait();
            Zamek novy = dialog.getNovyZamek();
            dialog.close();

            if (novy != null) {
                pamatky.vlozZamek(novy);
                draw();
                MainListView.scrollTo(novy);
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(e.getLocalizedMessage());

            alert.showAndWait();
        }
    }

    @FXML
    void OnClickZrus(ActionEvent event) {
        try {
            if (pamatky.jePrazdny()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("List je prazdny");

                alert.showAndWait();
            } else {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("Opravdu chcete smazat vsechna data?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    pamatky.zrus();
                    draw();
                }
            }


        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(e.getLocalizedMessage());

            alert.showAndWait();
        }
    }

    @FXML
    public void OnClickGenerujBTN(ActionEvent actionEvent) {
        try {
            for (int i = 0; i < generujSlider.getValue(); i++) {
                pamatky.vlozZamek(Generator.generateRandomZamek(pamatky.getTypKey()));
            }
            draw();


        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(e.getLocalizedMessage());

            alert.showAndWait();
        }
    }

    private void draw() {
        try {
            MainListView.getItems().clear();
            Iterator iterator = pamatky.vytvorIterator(enumTypProhlidky.HLOUBLKA);
            while (iterator.hasNext()) {
                MainListView.getItems().add((Zamek) iterator.next());


            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    @FXML
    void initialize() {

    }
}
