package org.radianite.prg3javafxsistemmarketingperumahan.Controller.Role;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Methods.Library;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Role;

import java.sql.SQLException;

public class UpdateRole extends Library {

    @FXML
    private TextField txtIdRole;
    @FXML
    private TextField txtNamaRole;
    @FXML
    private Button btnSave;
    private Role role;

    private Database connection = new Database();
    @FXML
    public void initialize() {
        txtNamaRole.addEventFilter(KeyEvent.KEY_TYPED, super::handleLetterKey);
    }

    public void setRole(Role role) { // Ganti dari setTipeRumah menjadi setRole
        this.role = role;
        txtIdRole.setText(role.getIdRole()); // Ganti dari getIdTipe menjadi getIdRole
        txtNamaRole.setText(role.getNamaRole()); // Ganti dari getNama menjadi getNamaRole
        // Isi field lainnya sesuai kebutuhan
        txtNamaRole.addEventFilter(KeyEvent.KEY_TYPED, super::handleLetterKey);
    }

    @FXML
    private void handleUpdateAction() {
        if (txtNamaRole.getText().isEmpty()){
            fillBox(btnSave,"Please fill in the field");
            return;
        }
        role.setNamaRole(txtNamaRole.getText());
        try {
            updateDataInDatabase(role);
            successBox(btnSave, "Update Success");
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
