package org.radianite.prg3javafxsistemmarketingperumahan.Controller.Role;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;

import java.sql.SQLException;

public class Input {
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
        try {
            // Mengambil id_role terakhir dari tabel Role
            String queryGetLastId = "SELECT id_role FROM ms_role ORDER BY id_role DESC ";
            connection.pstat = connection.conn.prepareStatement(queryGetLastId);
            connection.result  = connection.pstat.executeQuery();

            if (connection.result.next()) {
                // Mengambil id_role terakhir dan menambahkannya untuk mendapatkan id_role baru
                String lastId = connection.result.getString("id_role");
                int newIdNumber = Integer.parseInt(lastId.substring(2)) + 1; // Asumsikan id_role memiliki format seperti "R01"
                nextIdRole = "R" + String.format("%02d", newIdNumber); // Menghasilkan id_role baru, misal "R02"
                txtIdRole.setText(nextIdRole);
            } else {
                // Jika tidak ada id_role di tabel, mulai dari "R01"
                nextIdRole = "R01";
                txtIdRole.setText(nextIdRole);
            }

            connection.result.close();
            connection.pstat.close();
        } catch (SQLException ex) {
            System.out.println("Terjadi error saat mengambil id_role terakhir: " + ex);
        }
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

            clear();
        } catch (SQLException ex) {
            System.out.println("Terjadi error saat insert data role: " + ex);
        }
        try {
            // Mengambil id_role terakhir dari tabel Role
            String queryGetLastId = "SELECT id_role FROM ms_role ORDER BY id_role DESC ";
            connection.pstat = connection.conn.prepareStatement(queryGetLastId);
            connection.result  = connection.pstat.executeQuery();

            if (connection.result.next()) {
                // Mengambil id_role terakhir dan menambahkannya untuk mendapatkan id_role baru
                String lastId = connection.result.getString("id_role");
                int newIdNumber = Integer.parseInt(lastId.substring(2)) + 1; // Asumsikan id_role memiliki format seperti "R01"
                nextIdRole = "R" + String.format("%02d", newIdNumber); // Menghasilkan id_role baru, misal "R02"
                txtIdRole.setText(nextIdRole);
            } else {
                // Jika tidak ada id_role di tabel, mulai dari "R01"
                nextIdRole = "R01";
                txtIdRole.setText(nextIdRole);
            }

            connection.result.close();
            connection.pstat.close();
        } catch (SQLException ex) {
            System.out.println("Terjadi error saat mengambil id_role terakhir: " + ex);
        }
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
