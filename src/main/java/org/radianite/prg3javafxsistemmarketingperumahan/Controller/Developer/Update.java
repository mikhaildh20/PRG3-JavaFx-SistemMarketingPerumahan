package org.radianite.prg3javafxsistemmarketingperumahan.Controller.Developer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Methods.Library;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Developer;

import java.sql.SQLException;

public class Update extends Library {

    @FXML
    private TextField idDeveloperField; // Ganti dari idRoleField menjadi idDeveloperField
    @FXML
    private TextField namaDeveloperField; // Ganti dari namaField menjadi namaDeveloperField

    private Developer developer; // Ganti dari Role menjadi Developer
    private Database connection = new Database();

    private ObservableList<Developer> developerList = FXCollections.observableArrayList();

    public Update() {

    }

    public void loadData()
    {
        try {
            connection.stat = connection.conn.createStatement();
            String query = "SELECT * FROM ms_developer"; // Ganti dari ms_role menjadi ms_developer
            connection.result = connection.stat.executeQuery(query);

            while (connection.result.next()) {
                developerList.add(new Developer(connection.result.getString("id_developer"), // Ganti dari id_role menjadi id_developer
                        connection.result.getString("nama_developer"), // Ganti dari nama_role menjadi nama_developer
                        connection.result.getInt("status")));
            }
            connection.stat.close();
            connection.result.close();
        } catch (Exception ex) {
            System.out.println("Terjadi error saat load data: " + ex);
        }
    }

    public void setDeveloper(Developer developer) { // Ganti dari setRole menjadi setDeveloper
        this.developer = developer;
        idDeveloperField.setText(developer.getIdDeveloper()); // Ganti dari getIdRole menjadi getIdDeveloper
        namaDeveloperField.setText(developer.getNamaDeveloper()); // Ganti dari getNamaRole menjadi getNamaDeveloper
        namaDeveloperField.addEventFilter(KeyEvent.KEY_TYPED, super::handleLetterKey);

        loadData();
    }

    @FXML
    private void handleUpdateAction() {

        if (namaDeveloperField.getText().isEmpty())
        {
            fillBox();
            return;
        }

        for (int i=0;i<developerList.size();i++)
        {
            if (namaDeveloperField.getText().equals(developerList.get(i).getNamaDeveloper()) && !idDeveloperField.getText().equals(developerList.get(i).getIdDeveloper())){
                errorBox();
                return;
            }
        }

        developer.setNamaDeveloper(namaDeveloperField.getText()); // Ganti dari setNamaRole menjadi setNamaDeveloper

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
