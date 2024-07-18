package org.radianite.prg3javafxsistemmarketingperumahan.Controller.TransaksiRuko;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Methods.Library;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Bank;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Ruko;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Rumah;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.User;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Penyewaan extends Library implements Initializable {
    @FXML
    private TextField txtId,txtNIK,txtNama,txtContaxt,txtPeriode,txtTotal,txtRek;
    @FXML
    private ComboBox<Ruko> cbRuko;
    @FXML
    private ComboBox<String> cbPayment;
    @FXML
    private Button btnFile;
    @FXML
    private Label LabFile;
    @FXML
    private ComboBox<Bank> cbBank;
    private ObservableList<String> listPay = FXCollections.observableArrayList("Tunai","Debit");
    private ObservableList<Ruko> listRuko = FXCollections.observableArrayList();
    private ObservableList<Bank> listBank = FXCollections.observableArrayList();
    private ArrayList<User> userlist = new ArrayList<>();
    private Double price;
    private String id;
    private String idper;
    File file;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadBank();
        cbPayment.setItems(listPay);
        txtId.setDisable(true);
        cbBank.setDisable(true);
        txtRek.setDisable(true);
        txtTotal.setDisable(true);
        txtId.setText(generateID("tr_ruko","TRO","id_trRuko"));
        txtNIK.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtNIK.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        txtNama.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-Z*")) {
                txtNama.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            }
        });

        txtContaxt.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtContaxt.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        txtContaxt.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 13) {
                txtContaxt.setText(newValue.substring(0, 13));
            }
        });

        txtPeriode.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtPeriode.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        txtTotal.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtTotal.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        txtRek.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtRek.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        cbRuko.setCellFactory(param->new javafx.scene.control.ListCell<Ruko>(){
            protected void updateItem(Ruko item,boolean empty){
                super.updateItem(item,empty);
                if (item == null || empty){
                    setText(null);
                }else{
                    setText(item.getBlok());
                }
            }
        });
        cbRuko.setConverter(new StringConverter<Ruko>() {
            @Override
            public String toString(Ruko perumahan) {
                return perumahan == null ? null : perumahan.getBlok();
            }

            @Override
            public Ruko fromString(String s) {
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
            public String toString(Bank perumahan) {
                return perumahan == null ? null : perumahan.getName();
            }

            @Override
            public Bank fromString(String s) {
                return null;
            }
        });
    }

/*    public void loadRuko(){
        listRuko.clear();
        try{
            Database connect = new Database();
            connect.stat = connect.conn.createStatement();
            String query = "SELECT id_ruko,blok,harga_sewa,ketersediaan,status FROM ms_ruko";
            connect.result = connect.stat.executeQuery(query);
            while (connect.result.next())
            {
                if (connect.result.getInt("ketersediaan") == 1 && connect.result.getInt("status") == 1){
                    listRuko.add(new Ruko(
                            connect.result.getString("id_ruko"),
                            connect.result.getString("blok"),
                            connect.result.getDouble("harga_sewa")
                    ));
                }
            }
            connect.stat.close();
            connect.result.close();
        }catch (SQLException ex){
            System.out.println("Error: "+ex.getMessage());
        }
        cbRuko.setItems(listRuko);
    }*/

    public void setDataList(String id, String idper, User user) {
        this.id = id;
        this.idper = idper;
        listRuko.clear();
        userlist.add(user);
        try{
            Database connect = new Database();
            connect.stat = connect.conn.createStatement();
            String query = "SELECT id_ruko,blok,harga_sewa,ketersediaan,status FROM ms_ruko where id_ruko='"+id+"'";
            connect.result = connect.stat.executeQuery(query);
            while (connect.result.next())
            {
                if (connect.result.getInt("ketersediaan") == 1 && connect.result.getInt("status") == 1){
                    listRuko.add(new Ruko(
                            connect.result.getString("id_ruko"),
                            connect.result.getString("blok"),
                            connect.result.getDouble("harga_sewa")
                    ));
                }
            }
            connect.stat.close();
            connect.result.close();
        }catch (SQLException ex){
            System.out.println("Error: "+ex.getMessage());
        }
        cbRuko.setItems(listRuko);

        if (!listRuko.isEmpty()) {
            cbRuko.getSelectionModel().select(0);
            Ruko selectedRuko = cbRuko.getSelectionModel().getSelectedItem();
            if (selectedRuko != null) {
                txtTotal.setText(convertDoubleString(selectedRuko.getRent()));
            }
        }

/*        if (!listRumah.isEmpty()) {
            cbBlok.getSelectionModel().select(0);
            Rumah selectedRumah = cbBlok.getSelectionModel().getSelectedItem();
            if (selectedRumah != null) {
                txtTotal.setText(convertDoubleString(selectedRumah.getHarga()));
                txtMinCicil.setText(convertDoubleString(minFirstPayment(selectedRumah.getHarga())));
            }
        }*/
    }

    public void loadBank(){
        try{
            Database connect = new Database();
            connect.stat = connect.conn.createStatement();
            String query = "SELECT * FROM ms_bank";
            connect.result = connect.stat.executeQuery(query);
            while (connect.result.next())
            {
                if (connect.result.getInt("status")==1){
                    listBank.add(new Bank(connect.result.getString("id_bank"),
                            connect.result.getString("nama_bank"),
                            connect.result.getInt("suku_bunga")));
                }
            }
            connect.stat.close();
            connect.result.close();
        }catch (SQLException ex)
        {
            System.out.println("Error: "+ex.getMessage());
        }
        cbBank.setItems(listBank);
    }

    public void onActionFile(ActionEvent actionEvent) {
        try{
            file = documentChooser(btnFile);
            LabFile.setText(file.getName());
        }catch (Exception ex){
            file = null;
        }
    }

    public void onActionAdd(ActionEvent actionEvent) {
        if (isEmpty())
        {
            fillBox(btnFile,"Please fill in all the fields");
            return;
        }
        int choice=0;
        LocalDate date = LocalDate.now();
        date = date.plusMonths(Integer.parseInt(txtPeriode.getText()));
        java.sql.Date sqlDate = java.sql.Date.valueOf(date);
        if (cbPayment.getSelectionModel().getSelectedItem().equals("Debit")) choice=1;
        try{
            Database connect = new Database();
            String query = "EXEC sp_inputTrRuko ?,?,?,?,?,?,?,?,?,?,?,?,?";
            connect.pstat = connect.conn.prepareStatement(query);
            connect.pstat.setString(1,txtId.getText());
            connect.pstat.setString(2,cbRuko.getSelectionModel().getSelectedItem().getId());
            connect.pstat.setString(3,userlist.get(0).getUsn());
            connect.pstat.setString(4,txtNIK.getText());
            connect.pstat.setString(5,txtNama.getText());
            connect.pstat.setString(6,txtContaxt.getText());
            connect.pstat.setString(7,cbPayment.getSelectionModel().getSelectedItem());
            if (choice == 1) {
                connect.pstat.setString(8, cbBank.getSelectionModel().getSelectedItem().getId());
                connect.pstat.setString(9,txtRek.getText());
            } else {
                connect.pstat.setNull(8, java.sql.Types.VARCHAR);
                connect.pstat.setNull(9, java.sql.Types.VARCHAR);
            }
            connect.pstat.setInt(10,Integer.parseInt(txtPeriode.getText()));
            connect.pstat.setDouble(11,convertStringDouble(txtTotal.getText()) * Integer.parseInt(txtPeriode.getText()));
            connect.pstat.setBytes(12,imageToByte(file));
            connect.pstat.setDate(13,sqlDate);
            connect.pstat.executeUpdate();
            connect.pstat.close();
            successBox(btnFile,"Data added successfully");
            clear();
            txtId.setText(generateID("tr_ruko","TRO","id_trRuko"));
        }catch (SQLException | IOException ex){
            System.out.println("Error: "+ex.getMessage());
        }
    }

    public boolean isEmpty()
    {
        if (txtNIK.getText().isEmpty() || txtNama.getText().isEmpty() || txtContaxt.getText().isEmpty() || txtPeriode.getText().isEmpty() || txtTotal.getText().isEmpty() || file == null){
            return true;
        }
        return false;
    }

    public void clear(){
        txtId.setText("");
        txtNama.setText("");
        txtNIK.setText("");
        txtContaxt.setText("");
        txtTotal.setText("");
        txtPeriode.setText("");
        txtRek.setText("");
        LabFile.setText("Choose file here..");
        file = null;
    }

    public void onActionRuko(ActionEvent actionEvent) {
        txtTotal.setText(convertDoubleString(cbRuko.getSelectionModel().getSelectedItem().getRent()));
    }

    public void onActionCbPay(ActionEvent actionEvent) {
        if (cbPayment.getSelectionModel().getSelectedItem().equals("Debit")){
            cbBank.setDisable(false);
            txtRek.setDisable(false);
        }else {
            cbBank.setDisable(true);
            txtRek.setDisable(true);
        }
    }

    public void onTypedPeriode(KeyEvent keyEvent) {
        if (cbRuko.getSelectionModel().getSelectedItem() == null){
            return;
        }
        if (txtPeriode.getText().isEmpty()) {
            txtTotal.setText(convertDoubleString(cbRuko.getSelectionModel().getSelectedItem().getRent()));
            return;
        }
        txtTotal.setText(convertDoubleString(cbRuko.getSelectionModel().getSelectedItem().getRent() * Integer.parseInt(txtPeriode.getText())));
    }
}
