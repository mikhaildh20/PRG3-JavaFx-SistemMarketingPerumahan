package org.radianite.prg3javafxsistemmarketingperumahan.Controller.TipeRumah;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;

import java.sql.SQLException;

public class Input {
    @FXML
    private TextField txtIdTipe;
    @FXML
    private TextField txtNama;
    @FXML
    private Button simpanButton;
    @FXML
    private Button batalButton;
    @FXML
    private Label messageLabel;


    private String nextIdTipe;
    String idTipe, nama;
    int status;
    private Database connection = new Database();
    @FXML
    public void initialize() {
        try {
            // Mengambil id_tipe terakhir dari tabel Tipe_Rumah
            String queryGetLastId = "SELECT id_tipe FROM ms_tipe_rumah ORDER BY id_tipe DESC ";
            connection.pstat = connection.conn.prepareStatement(queryGetLastId);
            connection.result  = connection.pstat.executeQuery();

            if (connection.result.next()) {
                // Mengambil id_tipe terakhir dan menambahkannya untuk mendapatkan id_tipe baru
                String lastId = connection.result.getString("id_tipe");
                int newIdNumber = Integer.parseInt(lastId.substring(3)) + 1; // Asumsikan id_tipe memiliki format seperti "TR01"
                nextIdTipe = "TRM" + String.format("%03d", newIdNumber); // Menghasilkan id_tipe baru, misal "TR02"
                txtIdTipe.setText(nextIdTipe);
            } else {
                // Jika tidak ada id_tipe di tabel, mulai dari "TR01"
                nextIdTipe = "TRM001";
                txtIdTipe.setText(nextIdTipe);
            }

            connection.result.close();
            connection.pstat.close();
        } catch (SQLException ex) {
            System.out.println("Terjadi error saat mengambil id_tipe terakhir: " + ex);
        }
    }



    @FXML
    protected void onBtnSimpanClick() {
        idTipe = txtIdTipe.getText();
        nama = txtNama.getText();
        status = 1;

        try {
            // Menggunakan stored procedure spInsertTipeRumah
            String query = "{call sp_inputTipeRumah(?, ?)}";
            connection.pstat = connection.conn.prepareCall(query);
            connection.pstat.setString(1, idTipe);
            connection.pstat.setString(2, nama);
       /*     connection.pstat.setInt(3, status);*/

            connection.pstat.executeUpdate();
            connection.pstat.close();

            clear();
        } catch (SQLException ex) {
            System.out.println("Terjadi error saat insert data tipe rumah: " + ex);
        }
        try {
            // Mengambil id_tipe terakhir dari tabel Tipe_Rumah
            String queryGetLastId = "SELECT id_tipe FROM ms_tipe_rumah ORDER BY id_tipe DESC ";
            connection.pstat = connection.conn.prepareStatement(queryGetLastId);
            connection.result  = connection.pstat.executeQuery();

            if (connection.result.next()) {
                // Mengambil id_tipe terakhir dan menambahkannya untuk mendapatkan id_tipe baru
                String lastId = connection.result.getString("id_tipe");
                int newIdNumber = Integer.parseInt(lastId.substring(3)) + 1; // Asumsikan id_tipe memiliki format seperti "TR01"
                nextIdTipe = "TRM" + String.format("%03d", newIdNumber); // Menghasilkan id_tipe baru, misal "TR02"
                txtIdTipe.setText(nextIdTipe);
            } else {
                // Jika tidak ada id_tipe di tabel, mulai dari "TR01"
                nextIdTipe = "TRM001";
                txtIdTipe.setText(nextIdTipe);
            }

            connection.result.close();
            connection.pstat.close();
        } catch (SQLException ex) {
            System.out.println("Terjadi error saat mengambil id_tipe terakhir: " + ex);
        }
    }


    private void clear(){
        txtIdTipe.clear();
        txtNama.clear();
    }

    @FXML
    protected void onBatalSimpanClick(){
        System.exit(0);
    }


}
