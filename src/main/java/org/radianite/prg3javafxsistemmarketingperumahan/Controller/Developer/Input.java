package org.radianite.prg3javafxsistemmarketingperumahan.Controller.Developer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;

import java.sql.Connection;
import java.sql.SQLException;

public class Input {
    @FXML
    private TextField txtIdDeveloper;
    @FXML
    private TextField txtNamaDeveloper;
    @FXML
    private Button simpanButton;
    @FXML
    private Button batalButton;
    @FXML
    private Label messageLabel;

    private String nextIdDeveloper;
    String idDeveloper, namaDeveloper;
    int status;
    Database connection = new Database();

    @FXML
    public void initialize() {
        try {
            // Mengambil id_developer terakhir dari tabel Developer
            String queryGetLastId = "SELECT id_developer FROM ms_developer ORDER BY id_developer DESC";
            connection.pstat = connection.conn.prepareStatement(queryGetLastId);
            connection.result = connection.pstat.executeQuery();

            if (connection.result.next()) {
                // Mengambil id_developer terakhir dan menambahkannya untuk mendapatkan id_developer baru
                String lastId = connection.result.getString("id_developer");
                int newIdNumber = Integer.parseInt(lastId.substring(3)) + 1; // Asumsikan id_developer memiliki format seperti "D01"
                nextIdDeveloper = "DVL" + String.format("%03d", newIdNumber); // Menghasilkan id_developer baru, misal "D02"
                txtIdDeveloper.setText(nextIdDeveloper);
            } else {
                // Jika tidak ada id_developer di tabel, mulai dari "D01"
                nextIdDeveloper = "DVL001";
                txtIdDeveloper.setText(nextIdDeveloper);
            }

            connection.result.close();
            connection.pstat.close();
        } catch (SQLException ex) {
            System.out.println("Terjadi error saat mengambil id_developer terakhir: " + ex);
        }
    }

    @FXML
    protected void onBtnSimpanClick() {
        idDeveloper = txtIdDeveloper.getText();
        namaDeveloper = txtNamaDeveloper.getText();
        status = 1;

        try {
            // Menggunakan stored procedure sp_inputDeveloper
            String query = "{call sp_inputDeveloper(?, ?)}";
            connection.pstat = connection.conn.prepareCall(query);
            connection.pstat.setString(1, idDeveloper);
            connection.pstat.setString(2, namaDeveloper);
            /* connection.pstat.setInt(3, status);*/

            connection.pstat.executeUpdate();
            connection.pstat.close();

            clear();
        } catch (SQLException ex) {
            System.out.println("Terjadi error saat insert data developer: " + ex);
        }
        try {
            // Mengambil id_developer terakhir dari tabel Developer
            String queryGetLastId = "SELECT id_developer FROM ms_developer ORDER BY id_developer DESC";
            connection.pstat = connection.conn.prepareStatement(queryGetLastId);
            connection.result = connection.pstat.executeQuery();

            if (connection.result.next()) {
                // Mengambil id_developer terakhir dan menambahkannya untuk mendapatkan id_developer baru
                String lastId = connection.result.getString("id_developer");
                int newIdNumber = Integer.parseInt(lastId.substring(3)) + 1; // Asumsikan id_developer memiliki format seperti "D01"
                nextIdDeveloper = "DVL" + String.format("%03d", newIdNumber); // Menghasilkan id_developer baru, misal "D02"
                txtIdDeveloper.setText(nextIdDeveloper);
            } else {
                // Jika tidak ada id_developer di tabel, mulai dari "D01"
                nextIdDeveloper = "DVL001";
                txtIdDeveloper.setText(nextIdDeveloper);
            }

            connection.result.close();
            connection.pstat.close();
        } catch (SQLException ex) {
            System.out.println("Terjadi error saat mengambil id_developer terakhir: " + ex);
        }
    }

    private void clear() {
        txtIdDeveloper.clear();
        txtNamaDeveloper.clear();
    }

    @FXML
    protected void onBatalSimpanClick() {
        System.exit(0);
    }
}
