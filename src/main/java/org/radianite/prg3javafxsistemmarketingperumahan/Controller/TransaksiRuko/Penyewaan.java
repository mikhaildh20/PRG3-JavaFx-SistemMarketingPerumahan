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
import javafx.util.StringConverter;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Methods.Library;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Ruko;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Penyewaan extends Library implements Initializable {
    @FXML
    private TextField txtId,txtNIK,txtNama,txtTelp,txtPeriode,txtTotal;
    @FXML
    private ComboBox<Ruko> cbRuko;
    @FXML
    private ComboBox<String> cbPayment;
    @FXML
    private Button btnFile;
    @FXML
    private Label LabFile;
    private ObservableList<String> listPay = FXCollections.observableArrayList("Tunai","Debit");
    private ObservableList<Ruko> listRuko = FXCollections.observableArrayList();
    File file;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadRuko();
        cbPayment.setItems(listPay);
        txtId.setDisable(true);
        txtTotal.setDisable(true);
        txtId.setText(generateID("tr_ruko","TRO","id_trRuko"));

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
    }

    public void loadRuko(){
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
        }catch (SQLException ex){
            System.out.println("Error: "+ex.getMessage());
        }
        cbRuko.setItems(listRuko);
    }

    public void onActionFile(ActionEvent actionEvent) {
        file = imageChooser(btnFile);
        LabFile.setText(file.getName());
    }

    public void onActionAdd(ActionEvent actionEvent) {
        LocalDate date = LocalDate.now();
        date.plusMonths(Integer.parseInt(txtPeriode.getText()));
        java.sql.Date sqlDate = java.sql.Date.valueOf(date);
        try{
            Database connect = new Database();
            String query = "EXEC sp_inputTrRuko ?,?,?,?,?,?,?,?,?,?,?";
            connect.pstat = connect.conn.prepareStatement(query);
            connect.pstat.setString(1,txtId.getText());
            connect.pstat.setString(2,cbRuko.getSelectionModel().getSelectedItem().getId());
            connect.pstat.setString(3,"1xvee");
            connect.pstat.setString(4,txtNIK.getText());
            connect.pstat.setString(5,txtNama.getText());
            connect.pstat.setString(6,txtTelp.getText());
            connect.pstat.setString(7,cbPayment.getSelectionModel().getSelectedItem());
            connect.pstat.setInt(8,Integer.parseInt(txtPeriode.getText()));
            connect.pstat.setDouble(9,convertStringDouble(txtTotal.getText()));
            connect.pstat.setBytes(10,imageToByte(file));
            connect.pstat.setDate(11,sqlDate);
            connect.pstat.executeUpdate();
            connect.pstat.close();
            successBox();
            clear();
            loadRuko();
            txtId.setText(generateID("tr_ruko","TRO","id_trRuko"));
        }catch (SQLException | IOException ex){
            System.out.println("Error: "+ex.getMessage());
        }
    }

    public void clear(){
        txtId.setText("");
        txtNama.setText("");
        txtNIK.setText("");
        txtTelp.setText("");
        txtTotal.setText("");
        txtPeriode.setText("");
        file = null;
    }

    public void onActionRuko(ActionEvent actionEvent) {
        txtTotal.setText(convertDoubleString(cbRuko.getSelectionModel().getSelectedItem().getRent()));
    }
}
