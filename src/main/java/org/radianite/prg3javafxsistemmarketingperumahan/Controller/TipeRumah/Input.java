package org.radianite.prg3javafxsistemmarketingperumahan.Controller.TipeRumah;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Methods.Library;

import java.sql.SQLException;

public class Input extends Library {
    @FXML
    private TextField txtIdTipe;
    @FXML
    private TextField txtNama;
    @FXML
    private Button btnSimpan;
    @FXML
    private Button batalButton;
    @FXML
    private Label messageLabel;


    private String nextIdTipe;
    String idTipe, nama;
    int status;
    private Database connection = new Database();
    @FXML
    public void initialize() {
        txtIdTipe.setText(generateID("ms_tipe_rumah","TRM","id_tipe"));
    txtNama.textProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue.matches("[a-zA-Z\\s]*")) {
            txtNama.setText(newValue.replaceAll("[^a-zA-Z\\s]", ""));
        }
    });
        
    }



    @FXML
    protected void onBtnSimpanClick() {
        idTipe = txtIdTipe.getText();
        nama = txtNama.getText();
        status = 1;
        if (idTipe.isEmpty() || nama.isEmpty()) {
            fillBox(btnSimpan, "Please fill all the fields");
            return;
        } else {
            messageLabel.setText("");
        }

        try {
            // Menggunakan stored procedure spInsertTipeRumah
            String query = "{call sp_inputTipeRumah(?, ?)}";
            connection.pstat = connection.conn.prepareCall(query);
            connection.pstat.setString(1, idTipe);
            connection.pstat.setString(2, nama);
       /*     connection.pstat.setInt(3, status);*/

            connection.pstat.executeUpdate();
            connection.pstat.close();
            successBox(btnSimpan, "Data Successfully Saved");
            clear();
        } catch (SQLException ex) {
            System.out.println("Terjadi error saat insert data tipe rumah: " + ex);
        }
        txtIdTipe.setText(generateID("ms_tipe_rumah","TRM","id_tipe"));
    }


    private void clear(){
        txtIdTipe.clear();
        txtNama.clear();
    }

    @FXML
    protected void onBatalSimpanClick(){
        System.exit(0);
    }


}
