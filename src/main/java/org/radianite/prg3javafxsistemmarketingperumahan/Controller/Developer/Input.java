package org.radianite.prg3javafxsistemmarketingperumahan.Controller.Developer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Methods.Library;

import java.sql.Connection;
import java.sql.SQLException;

public class Input extends Library {
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
        txtIdDeveloper.setText(generateID("ms_developer","DVL","id_developer"));
        txtNamaDeveloper.addEventFilter(KeyEvent.KEY_TYPED, super::handleLetterKey);
    }

    @FXML
    protected void onBtnSimpanClick() {
        idDeveloper = txtIdDeveloper.getText();
        namaDeveloper = txtNamaDeveloper.getText();

        try {
            // Menggunakan stored procedure sp_inputDeveloper
            String query = "{call sp_inputDeveloper(?, ?)}";
            connection.pstat = connection.conn.prepareCall(query);
            connection.pstat.setString(1, idDeveloper);
            connection.pstat.setString(2, namaDeveloper);

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
            connection.result.close();
            connection.pstat.close();
            txtIdDeveloper.setText(generateID("ms_developer","DVL","id_developer"));
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
