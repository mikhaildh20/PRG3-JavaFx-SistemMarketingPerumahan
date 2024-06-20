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
import javafx.util.StringConverter;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Methods.Library;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Bank;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Perumahan;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Rumah;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Pembelian extends Library implements Initializable {
    @FXML
    private TextField txtId,txtDate,txtNIK,txtNama,txtContact,txtRekening,txtTotal,txtMinCicil,txtCicil;
    @FXML
    private ComboBox<Rumah> cbBlok;
    @FXML
    private ComboBox<Bank> cbBank;
    @FXML
    private ComboBox<String> cbPayment;
    @FXML
    private ComboBox<String> cbPeriode;
    @FXML
    private Button btnFile;
    @FXML
    private Label LabFile;
    private ObservableList<Rumah> listRumah = FXCollections.observableArrayList();
    private ObservableList<Bank> listBank = FXCollections.observableArrayList();
    private ObservableList<String> listPayment = FXCollections.observableArrayList("Tunai","Debit");
    private ObservableList<String> listPeriode = FXCollections.observableArrayList("5 Year", "10 Year", "15 Year", "20 Year", "25 Year");
    private File file;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadRumah();
        loadBank();
        cbPayment.setItems(listPayment);
        cbPeriode.setItems(listPeriode);

        txtId.setDisable(true);
        txtDate.setDisable(true);
        cbBank.setDisable(true);
        txtRekening.setDisable(true);
        txtId.setText(generateID("tr_rumah","TRR","id_trRumah"));
        txtDate.setText(LocalDate.now().toString());

        cbBlok.setCellFactory(param->new javafx.scene.control.ListCell<Rumah>(){
            protected void updateItem(Rumah item,boolean empty){
                super.updateItem(item,empty);
                if (item == null || empty){
                    setText(null);
                }else{
                    setText(item.getBlok());
                }
            }
        });
        cbBlok.setConverter(new StringConverter<Rumah>() {
            @Override
            public String toString(Rumah rumah) {
                return rumah == null ? null : rumah.getBlok();
            }

            @Override
            public Rumah fromString(String s) {
                return null;
            }
        });

        cbBank.setCellFactory(param->new javafx.scene.control.ListCell<Bank>(){
            protected void updateItem(Bank item,boolean empty){
                super.updateItem(item,empty);
                if (item == null || empty){
                    setText(null);
                }else{
                    setText(item.getName());
                }
            }
        });
        cbBank.setConverter(new StringConverter<Bank>() {
            @Override
            public String toString(Bank bank) {
                return bank == null ? null : bank.getName();
            }

            @Override
            public Bank fromString(String s) {
                return null;
            }
        });
    }

    public void loadRumah(){
        listRumah.clear();
        try{
            Database connect = new Database();
            connect.stat = connect.conn.createStatement();
            String query = "SELECT id_rumah,blok,harga,ketersediaan,status FROM ms_rumah";
            connect.result = connect.stat.executeQuery(query);
            while (connect.result.next())
            {
                if (connect.result.getInt("status")==1 && connect.result.getInt("ketersediaan")==1) {
                    listRumah.add(new Rumah(connect.result.getString("id_rumah"),
                            connect.result.getString("blok"),
                            connect.result.getDouble("harga")));
                }
            }
            connect.stat.close();
            connect.result.close();
        }catch (SQLException ex)
        {
            System.out.println("Error: "+ex.getMessage());
        }
        cbBlok.setItems(listRumah);
    }

    public void loadBank(){
        try{
            Database connect = new Database();
            connect.stat = connect.conn.createStatement();
            String query = "SELECT * FROM ms_bank";
            connect.result = connect.stat.executeQuery(query);
            while (connect.result.next())
            {
                if (connect.result.getInt("status")==1)
                {
                    listBank.add(new Bank(connect.result.getString("id_bank"),
                            connect.result.getString("nama_bank"),
                            connect.result.getInt("suku_bunga")));
                }
            }
            connect.stat.close();
            connect.result.close();
        }catch (SQLException ex){
            System.out.println("Error: "+ex.getMessage());
        }
        cbBank.setItems(listBank);
    }

    public void onActionDocument(ActionEvent actionEvent) {
        try{
            file = imageChooser(btnFile);
            LabFile.setText(file.getName());
        }catch (Exception e){
        }
    }

    public void onActionSave(ActionEvent actionEvent) {
    }

    public void onActionCbPay(ActionEvent actionEvent) {
        if (cbPayment.getSelectionModel().getSelectedItem().equals("Debit")){
            cbBank.setDisable(false);
            txtRekening.setDisable(false);
            return;
        }
        cbBank.setDisable(true);
        txtRekening.setDisable(true);
    }
}
