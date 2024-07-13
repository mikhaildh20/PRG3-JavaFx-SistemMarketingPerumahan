package org.radianite.prg3javafxsistemmarketingperumahan.Controller.Role;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Methods.Library;

import java.sql.SQLException;

public class Input extends Library {
    @FXML
    private TextField txtIdRole;
    @FXML
    private TextField txtNamaRole;
    @FXML
    private Button simpanButton;
    @FXML
    private Button batalButton;
    @FXML
    private Label messageLabel;


    private String nextIdRole;
    String idRole, namaRole;
    int status;
    private Database connection = new Database();
    @FXML
    public void initialize() {
        txtIdRole.setText(generateID("ms_role","RLE","id_role"));
        txtNamaRole.addEventFilter(KeyEvent.KEY_TYPED, super::handleLetterKey);
    }



    @FXML
    protected void onBtnSimpanClick() {
        idRole = txtIdRole.getText();
        namaRole = txtNamaRole.getText();
        status = 1;

        try {
            // Menggunakan stored procedure spInsertRole
            String query = "{call sp_inputRole(?, ?)}";
            connection.pstat = connection.conn.prepareCall(query);
            connection.pstat.setString(1, idRole);
            connection.pstat.setString(2, namaRole);
            /* connection.pstat.setInt(3, status);*/

            connection.pstat.executeUpdate();
            connection.pstat.close();

            successBox(simpanButton, "Data Successfully Saved");
            clear();
        } catch (SQLException ex) {
            System.out.println("Terjadi error saat insert data role: " + ex);
        }
        txtIdRole.setText(generateID("ms_role","RLE","id_role"));
    }


    private void clear(){
        txtIdRole.clear();
        txtNamaRole.clear();
    }

    @FXML
    protected void onBatalSimpanClick(){
        System.exit(0);
    }
}
