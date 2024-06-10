package org.radianite.prg3javafxsistemmarketingperumahan.Controller.Ruko;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.radianite.prg3javafxsistemmarketingperumahan.Methods.Library;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Perumahan;

import java.net.URL;
import java.util.ResourceBundle;

public class createRuko extends Library implements Initializable {
    @FXML
    private TextField txtId,txtBlok,txtElec,txtToilet,txtRent,txtDesc;
    @FXML
    private ComboBox<Perumahan> cbResidence;

    private ObservableList<Perumahan> listPerum = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtId.setDisable(true);
        txtId.setText(generateID("ms_ruko","RKO","id_ruko"));
    }
    public void onActionFile(ActionEvent actionEvent) {
    }

    public void onActionSave(ActionEvent actionEvent) {
    }
}
