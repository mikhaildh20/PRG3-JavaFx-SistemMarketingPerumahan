package org.radianite.prg3javafxsistemmarketingperumahan.Controller.TipeRumah;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Methods.Library;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.TipeRumah;

import java.sql.SQLException;

public class Update extends Library {

    @FXML
    private TextField txtNama;
    @FXML
    private TextField txtIdTipe;
    @FXML
    private Button btnSimpan;
    // Tambahkan field lainnya sesuai kebutuhan

    private TipeRumah tipeRumah;
    private Database connection = new Database();

    @FXML
    public void initialize() {
        txtNama.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z\\s]*")) {
                txtNama.setText(newValue.replaceAll("[^a-zA-Z\\s]", ""));
            }
        });

    }

    public void setTipeRumah(TipeRumah tipeRumah) {
        this.tipeRumah = tipeRumah;
        txtIdTipe.setText(tipeRumah.getIdTipe());
        txtNama.setText(tipeRumah.getNama());
        // Isi field lainnya sesuai kebutuhan
        txtNama.addEventFilter(KeyEvent.KEY_TYPED, super::handleLetterKey);
    }

    @FXML
    private void handleUpdateAction() {
        tipeRumah.setNama(txtNama.getText());

        try {
            updateDataInDatabase(tipeRumah);
            successBox(btnSimpan, "Data Tipe Rumah Berhasil");
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    private void updateDataInDatabase(TipeRumah tipeRumah) throws SQLException {
         // Sesuaikan dengan parameter stored procedure

        try {
            String query = "{call sp_updateTipeRumah(?, ?)}";
            connection.pstat = connection.conn.prepareCall(query);
            connection.pstat.setString(1, tipeRumah.getIdTipe());
            connection.pstat.setString(2, tipeRumah.getNama());

            connection.pstat.executeUpdate();
            connection.pstat.close();

        } catch (SQLException ex) {
            System.out.println("Terjadi error saat Update data tipe rumah: " + ex);
        }
    }
}
