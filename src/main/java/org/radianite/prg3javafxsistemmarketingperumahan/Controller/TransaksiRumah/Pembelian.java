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
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Rumah;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Pembelian extends Library implements Initializable {
    @FXML
    private TextField txtId,txtDate,txtNIK,txtNama,txtContact,txtRekening,txtTotal,txtMinCicil,txtCicil,txtPinjaman,txtBulanan;
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
    private ObservableList<String> listPayment = FXCollections.observableArrayList("CASH","CREDIT");
    private ObservableList<String> listPeriode = FXCollections.observableArrayList("5 Year", "10 Year", "15 Year", "20 Year", "25 Year");
    private File file;
    private int selected = 0;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadRumah();
        loadBank();
        cbPayment.setItems(listPayment);
        cbPeriode.setItems(listPeriode);

        txtId.setDisable(true);
        txtDate.setDisable(true);
        cbBank.setDisable(true);
        cbPeriode.setDisable(true);
        txtRekening.setDisable(true);
        txtTotal.setDisable(true);
        txtMinCicil.setDisable(true);
        txtCicil.setDisable(true);
        txtPinjaman.setDisable(true);
        txtBulanan.setDisable(true);
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

        txtRekening.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // if focus is lost
                if (cbBank.getSelectionModel().getSelectedItem() != null && cbPeriode.getSelectionModel().getSelectedItem() != null)
                {
                    txtCicil.setDisable(false);
                }
            }

        });

        txtCicil.focusedProperty().addListener((observable, oldValue, newValue) -> {
            try{
                Double firstpay = convertStringDouble(txtCicil.getText());
                Double total = convertStringDouble(txtTotal.getText());
                Double downpay = convertStringDouble(txtMinCicil.getText());
                Integer bunga = cbBank.getSelectionModel().getSelectedItem().getBunga();
                Double finale = total - firstpay;
                Double totalPinjaman;
                Double totalBunga;
                if (!newValue) {
                    if (firstpay >= downpay){
                        totalBunga = cicilanPerbulan(finale,bunga);
                        totalPinjaman = totalPinjamanBunga(totalBunga);
                        txtPinjaman.setText(convertDoubleString(totalPinjaman));
                        txtBulanan.setText(convertDoubleString(totalBunga));
                    }
                }
            }catch (Exception ignored){

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
            file = null;
        }
    }

    public void onActionSave(ActionEvent actionEvent) {
        try{
             Database connect = new Database();
             String query = "EXEC sp_inputTrRumah ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";
             connect.pstat = connect.conn.prepareStatement(query);
             connect.pstat.setString(1,txtId.getText());
             connect.pstat.setString(2,cbBlok.getSelectionModel().getSelectedItem().getId());
             connect.pstat.setString(3,"1xvee");
             connect.pstat.setString(4,txtNIK.getText());
             connect.pstat.setString(5,txtNama.getText());
             connect.pstat.setString(6,txtContact.getText());
             connect.pstat.setString(7,cbPayment.getSelectionModel().getSelectedItem());
             if (cbPayment.getSelectionModel().getSelectedIndex() == 1){
                 connect.pstat.setString(8,cbBank.getSelectionModel().getSelectedItem().getId());
                 connect.pstat.setString(9,txtRekening.getText());
                 connect.pstat.setInt(10,cbBank.getSelectionModel().getSelectedItem().getBunga());
             }else {
                 connect.pstat.setNull(8,Types.VARCHAR);
                 connect.pstat.setNull(9,Types.VARCHAR);
                 connect.pstat.setNull(10,Types.INTEGER);
             }
             connect.pstat.setDouble(11,convertStringDouble(txtTotal.getText()));
             connect.pstat.setInt(12,cbPayment.getSelectionModel().getSelectedIndex() == 0 ? 1 : 0);
             connect.pstat.setBytes(13,imageToByte(file));
            if (cbPayment.getSelectionModel().getSelectedIndex() == 1){
                connect.pstat.setDouble(14,convertStringDouble(txtBulanan.getText()));
                connect.pstat.setInt(15,tenorTahun());
                connect.pstat.setDouble(16,convertStringDouble(txtPinjaman.getText()));
            }else {
                connect.pstat.setNull(14,Types.DOUBLE);
                connect.pstat.setNull(15,Types.INTEGER);
                connect.pstat.setNull(16,Types.DOUBLE);
            }
             connect.pstat.setNull(17, Types.DATE);
            if (cbPayment.getSelectionModel().getSelectedIndex() == 1){
                connect.pstat.setNull(18, Types.DATE);
            }else {
                Date currentDate = new Date(System.currentTimeMillis());
                connect.pstat.setDate(18, currentDate);
            }
             connect.pstat.executeUpdate();


            query = "EXEC sp_cicilanRumah ?";
            connect.pstat = connect.conn.prepareStatement(query);
            connect.pstat.setString(1,cbBlok.getSelectionModel().getSelectedItem().getId());
            connect.pstat.executeUpdate();
            connect.pstat.close();
            loadRumah();
            txtId.setText(generateID("tr_rumah","TRR","id_trRumah"));
        }catch(SQLException | IOException ex)
        {
            System.out.println("Error: "+ex.getMessage());
        }
    }

    public void onActionCbPay(ActionEvent actionEvent) {
        if (selected == 0)return;
        if (cbPayment.getSelectionModel().isSelected(1)){
            cbBank.setDisable(false);
            cbPeriode.setDisable(false);
            txtRekening.setDisable(false);
            return;
        }
        txtRekening.setText("");
        cbBank.setDisable(true);
        cbPeriode.setDisable(true);
        txtRekening.setDisable(true);
        txtTotal.setText(convertDoubleString(cbBlok.getSelectionModel().getSelectedItem().getHarga()));
    }

    private Double cicilanPerbulan(Double total, Integer bunga){
        Integer tenor_bulan = tenorTahun() * 12;
        Integer tenor_tahun = tenorTahun();

        Double persentase = bunga / 100.0;
        Double cicilanPokok = total / tenor_bulan;
        Double cicilanBunga = total * persentase * tenor_tahun / tenor_bulan;

        return cicilanPokok + cicilanBunga;
    }

    private int tenorTahun(){
        if (cbPeriode.getSelectionModel().isSelected(0)){
            return 5;
        } else if (cbPeriode.getSelectionModel().isSelected(1)) {
            return 10;
        } else if (cbPeriode.getSelectionModel().isSelected(2)) {
            return 15;
        } else if (cbPeriode.getSelectionModel().isSelected(3)) {
            return 20;
        }
        return 25;
    }

    private Double totalPinjamanBunga(Double total){
        return total * (tenorTahun() * 12);
    }

    private Double minFirstPayment(Double total){
        return (15 * total) / 100;
    }

    public void onActionPeriode(ActionEvent actionEvent) {
        if(cbBank.getSelectionModel().getSelectedItem() != null && !txtRekening.getText().isEmpty()){
            txtCicil.setDisable(false);
        }
    }

    public void onActionBlok(ActionEvent actionEvent) {
        selected = 1;
        txtTotal.setText(convertDoubleString(cbBlok.getSelectionModel().getSelectedItem().getHarga()));
        txtMinCicil.setText(convertDoubleString(minFirstPayment(cbBlok.getSelectionModel().getSelectedItem().getHarga())));
        if (cbPayment.getSelectionModel().isSelected(1))
        {
            cbBank.setDisable(false);
            cbPeriode.setDisable(false);
            txtRekening.setDisable(false);
        }
    }

    public void onActionBank(ActionEvent actionEvent) {
        if(cbPeriode.getSelectionModel().getSelectedItem() != null || !txtRekening.getText().isEmpty()){
            txtCicil.setDisable(false);
        }
    }
}
