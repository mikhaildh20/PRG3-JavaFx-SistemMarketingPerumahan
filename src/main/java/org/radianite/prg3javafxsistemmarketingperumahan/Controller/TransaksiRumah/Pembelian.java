package org.radianite.prg3javafxsistemmarketingperumahan.Controller.TransaksiRumah;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.radianite.prg3javafxsistemmarketingperumahan.Methods.Library;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Bank;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Rumah;

import java.net.URL;
import java.util.ResourceBundle;

public class Pembelian extends Library implements Initializable {
    @FXML
    private TextField txtId,txtDate,txtNIK,txtNama,txtContact,txtRekening,txtTotal;
    @FXML
    private ComboBox<Rumah> cbBlok;
    @FXML
    private ComboBox<Bank> cbBank;
    @FXML
    private ComboBox<String> cbPayment;
    @FXML
    private Button btnFile;
    @FXML
    private Label LabFile;
    private ObservableList<Rumah> listRumah = FXCollections.observableArrayList();
    private ObservableList<Bank> listBank = FXCollections.observableArrayList();
    private ObservableList<String> listPayment = FXCollections.observableArrayList("Tunai","Debit");
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void onActionDocument(ActionEvent actionEvent) {
    }

    public void onActionSave(ActionEvent actionEvent) {
    }
}
