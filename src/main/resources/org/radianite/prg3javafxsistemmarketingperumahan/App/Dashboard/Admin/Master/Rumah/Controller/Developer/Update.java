package org.radianite.prg3javafxsistemmarketingperumahan.Controller.Developer;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Developer;

import java.sql.SQLException;

public class Update {

    @FXML
    private TextField idDeveloperField; // Ganti dari idRoleField menjadi idDeveloperField
    @FXML
    private TextField namaDeveloperField; // Ganti dari namaField menjadi namaDeveloperField

    private Developer developer; // Ganti dari Role menjadi Developer
    private Database connection = new Database();

    public Update() {

    }

    public void setDeveloper(Developer developer) {
        this.developer = developer;
        idDeveloperField.setText(developer.getIdDeveloper());
        namaDeveloperField.setText(developer.getNamaDeveloper());
    }

    @FXML
    private void handleUpdateAction() {
        developer.setNamaDeveloper(namaDeveloperField.getText());

        try {
            updateDataInDatabase(developer);
        } catch (SQLException e) {
            e.printStackTrace();
            // Tampilkan pesan error jika diperlukan
        }

        // Tutup form setelah update
        Stage stage = (Stage) idDeveloperField.getScene().getWindow(); // Ganti dari idRoleField menjadi idDeveloperField
        stage.close();
    }

    private void updateDataInDatabase(Developer developer) throws SQLException {
        try {
            String query = "{call sp_updateDeveloper(?, ?)}"; // Ganti dari sp_updateRole menjadi sp_updateDeveloper
            connection.pstat = connection.conn.prepareCall(query);
            connection.pstat.setString(1, developer.getIdDeveloper()); // Ganti dari getIdRole menjadi getIdDeveloper
            connection.pstat.setString(2, developer.getNamaDeveloper()); // Ganti dari getNamaRole menjadi getNamaDeveloper

            connection.pstat.executeUpdate();
            connection.pstat.close();

        } catch (SQLException ex) {
            System.out.println("Terjadi error saat Update data developer: " + ex);
        }
    }
}
