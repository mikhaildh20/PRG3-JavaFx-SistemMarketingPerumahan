package org.radianite.prg3javafxsistemmarketingperumahan.Controller.Role;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Methods.Library;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Role;

import java.sql.SQLException;

public class Update extends Library {

    @FXML
    private TextField idRoleField;
    @FXML
    private TextField namaField;
    private Role role;
    private Database connection = new Database();
    public Update() {

    }

    public void setRole(Role role) { // Ganti dari setTipeRumah menjadi setRole
        this.role = role;
        idRoleField.setText(role.getIdRole()); // Ganti dari getIdTipe menjadi getIdRole
        namaField.setText(role.getNamaRole()); // Ganti dari getNama menjadi getNamaRole
        // Isi field lainnya sesuai kebutuhan
        namaField.addEventFilter(KeyEvent.KEY_TYPED, super::handleLetterKey);
    }

    @FXML
    private void handleUpdateAction() {
        role.setNamaRole(namaField.getText()); // Ganti dari setNama menjadi setNamaRole
        // Perbarui nilai lainnya

        try {
            updateDataInDatabase(role);
        } catch (SQLException e) {
            e.printStackTrace();
            // Tampilkan pesan error jika diperlukan
        }

        // Tutup form setelah update
        Stage stage = (Stage) idRoleField.getScene().getWindow(); // Ganti dari idTipeRumahField menjadi idRoleField
        stage.close();
    }

    private void updateDataInDatabase(Role role) throws SQLException {
        try {
            String query = "{call sp_updateRole(?, ?)}"; // Ganti dari sp_updateTipeRumah menjadi sp_updateRole
            connection.pstat = connection.conn.prepareCall(query);
            connection.pstat.setString(1, role.getIdRole()); // Ganti dari getIdTipe menjadi getIdRole
            connection.pstat.setString(2, role.getNamaRole()); // Ganti dari getNama menjadi getNamaRole

            connection.pstat.executeUpdate();
            connection.pstat.close();

        } catch (SQLException ex) {
            System.out.println("Terjadi error saat Update data role: " + ex);
        }
    }
}
