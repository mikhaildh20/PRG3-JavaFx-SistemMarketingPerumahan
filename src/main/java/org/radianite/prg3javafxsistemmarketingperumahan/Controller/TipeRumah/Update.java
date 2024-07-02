package org.radianite.prg3javafxsistemmarketingperumahan.Controller.TipeRumah;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Methods.Library;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.TipeRumah;

import java.sql.SQLException;

public class Update extends Library {

    @FXML
    private TextField idTipeRumahField;
    @FXML
    private TextField namaField;
    // Tambahkan field lainnya sesuai kebutuhan

    private TipeRumah tipeRumah;
    private Database connection = new Database();

    public Update() {

    }

    public void setTipeRumah(TipeRumah tipeRumah) {
        this.tipeRumah = tipeRumah;
        idTipeRumahField.setText(tipeRumah.getIdTipe());
        namaField.setText(tipeRumah.getNama());
        // Isi field lainnya sesuai kebutuhan
        namaField.addEventFilter(KeyEvent.KEY_TYPED, super::handleLetterKey);
    }

    @FXML
    private void handleUpdateAction() {
        tipeRumah.setNama(namaField.getText());
        // Perbarui nilai lainnya

        try {
            updateDataInDatabase(tipeRumah);
        } catch (SQLException e) {
            e.printStackTrace();
            // Tampilkan pesan error jika diperlukan
        }

        // Tutup form setelah update
        Stage stage = (Stage) idTipeRumahField.getScene().getWindow();
        stage.close();
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
