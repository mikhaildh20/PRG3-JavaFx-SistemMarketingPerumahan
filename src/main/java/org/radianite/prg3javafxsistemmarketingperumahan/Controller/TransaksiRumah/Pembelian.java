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
import org.radianite.prg3javafxsistemmarketingperumahan.Models.User;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
    private String id;
    private int selected = 0;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadRumah();
        loadBank();
        cbPayment.setItems(listPayment);
        cbPeriode.setItems(listPeriode);

        txtId.setEditable(false);
        txtDate.setEditable(false);
        cbBank.setEditable(false);
        cbPeriode.setEditable(false);
        txtRekening.setEditable(false);
        txtTotal.setEditable(false);
        txtMinCicil.setEditable(false);
        txtCicil.setEditable(false);
        txtPinjaman.setEditable(false);
        txtBulanan.setEditable(false);
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

        // Set cbBlok to index 0 if listRumah is not empty
        if (!listRumah.isEmpty()) {
            cbBlok.getSelectionModel().select(0);
        }
    }

    public void setDataList(String id) {
        this.id = id;
        System.out.println(id);
        listRumah.clear();
        try{
            Database connect = new Database();
            connect.stat = connect.conn.createStatement();
            String query = "SELECT id_rumah,blok,harga,ketersediaan,status FROM ms_rumah where id_rumah='"+id+"'";
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
        cbBlok.getSelectionModel().select(0);
        txtTotal.setText(convertDoubleString(cbBlok.getSelectionModel().getSelectedItem().getHarga()));
        txtMinCicil.setText(convertDoubleString(minFirstPayment(cbBlok.getSelectionModel().getSelectedItem().getHarga())));

    }

    public void loadRumah(){

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
            file = documentChooser(btnFile);
            LabFile.setText(file.getName());
        }catch (Exception e){
            file = null;
        }
    }

    public void onActionSave(ActionEvent actionEvent) {
        try {
            Database connect = new Database();
            String query = "EXEC sp_inputTrRumah ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";
            connect.pstat = connect.conn.prepareStatement(query);

            // Set parameters
            connect.pstat.setString(1, txtId.getText());
            connect.pstat.setString(2, cbBlok.getSelectionModel().getSelectedItem().getId());
            connect.pstat.setString(3, "1xvee");
            connect.pstat.setString(4, txtNIK.getText());
            connect.pstat.setString(5, txtNama.getText());
            connect.pstat.setString(6, txtContact.getText());
            connect.pstat.setString(7, cbPayment.getSelectionModel().getSelectedItem());

            // Handle payment specific parameters
            if (cbPayment.getSelectionModel().getSelectedIndex() == 1) {
                handlePaymentTypeOne(connect);
            } else {
                handlePaymentTypeZero(connect);
            }

            // Set common parameters
            connect.pstat.setDouble(11, cbPayment.getSelectionModel().getSelectedIndex() == 0 ? convertStringDouble(txtTotal.getText()) : convertStringDouble(txtPinjaman.getText()));
            connect.pstat.setInt(12, cbPayment.getSelectionModel().getSelectedIndex() == 0 ? 1 : 0);
            connect.pstat.setBytes(13, imageToByte(file));

            // Execute the update
            connect.pstat.executeUpdate();
            connect.pstat.close();
            successBox();
        } catch (SQLException | IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void handlePaymentTypeOne(Database connect) throws SQLException {
        connect.pstat.setString(8, cbBank.getSelectionModel().getSelectedItem().getId());
        connect.pstat.setString(9, txtRekening.getText());
        connect.pstat.setInt(10, cbBank.getSelectionModel().getSelectedItem().getBunga());
        connect.pstat.setDouble(14, convertStringDouble(txtBulanan.getText()));
        connect.pstat.setInt(15, tenorTahun());
        connect.pstat.setDouble(16, convertStringDouble(txtPinjaman.getText()));
        LocalDate currentDate = LocalDate.now();
        LocalDate futureDate = currentDate.plusMonths(1);
        Date sqlDate = Date.valueOf(futureDate);
        connect.pstat.setDate(17, sqlDate);
        connect.pstat.setNull(18, Types.DATE);
    }

    private void handlePaymentTypeZero(Database connect) throws SQLException {
        connect.pstat.setNull(8, Types.VARCHAR);
        connect.pstat.setNull(9, Types.VARCHAR);
        connect.pstat.setNull(10, Types.INTEGER);
        connect.pstat.setNull(14, Types.DOUBLE);
        connect.pstat.setNull(15, Types.INTEGER);
        connect.pstat.setNull(16, Types.DOUBLE);
        connect.pstat.setNull(17, Types.DATE);
        Date currentDate = new Date(System.currentTimeMillis());
        connect.pstat.setDate(18, currentDate);
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
