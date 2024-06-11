package org.radianite.prg3javafxsistemmarketingperumahan.Controller.Rumah;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

public class Input {
    @FXML
    private TextField txtIdRumah;
    @FXML
    private ComboBox<String> cmbIdPerumahan;
    @FXML
    private TextField txtBlok;
    @FXML
    private TextField txtDayaListrik;
    @FXML
    private TextField txtInterior;
    @FXML
    private TextField txtJmlKmrTdr;
    @FXML
    private TextField txtJmlKmrMdn;
    @FXML
    private ComboBox<String> cmbIdTipe;
    @FXML
    private TextField txtDescription;
    @FXML
    private TextField txtUangMuka;
    @FXML
    private TextField txtHarga;
    @FXML
    private TextField txtThnBangun;
    @FXML
    private TextField txtKetersediaan;
    @FXML
    private TextField txtFotoPath;
    @FXML
    private Button simpanButton;
    @FXML
    private Button batalButton;
    @FXML
    private Button btnPilihFoto;
    @FXML
    private Label messageLabel;

    private File fotoFile;
    private String nextIdRumah;
    private Database connection = new Database();

    @FXML
    public void initialize() {
        generateNextIdRumah();
        populateComboBox(cmbIdPerumahan, "SELECT nama_perumahan FROM ms_perumahan where status = 1 ");
        populateComboBox(cmbIdTipe, "SELECT nama_tipe FROM ms_tipe_rumah where status = 1 ");
    }

    private void generateNextIdRumah() {
        try {
            String queryGetLastId = "SELECT id_rumah FROM ms_rumah ORDER BY id_rumah DESC";
            connection.pstat = connection.conn.prepareStatement(queryGetLastId);
            connection.result = connection.pstat.executeQuery();

            if (connection.result.next()) {
                String lastId = connection.result.getString("id_rumah");
                int newIdNumber = Integer.parseInt(lastId.substring(1)) + 1;
                nextIdRumah = "R" + String.format("%02d", newIdNumber);
                txtIdRumah.setText(nextIdRumah);
            } else {
                nextIdRumah = "R01";
                txtIdRumah.setText(nextIdRumah);
            }

            connection.result.close();
            connection.pstat.close();
        } catch (SQLException ex) {
            System.out.println("Terjadi error saat mengambil id_rumah terakhir: " + ex);
        }
    }

    private void populateComboBox(ComboBox<String> comboBox, String query) {
        ObservableList<String> options = FXCollections.observableArrayList();
        try {
            connection.stat = connection.conn.createStatement();
            connection.result = connection.stat.executeQuery(query);

            while (connection.result.next()) {
                options.add(connection.result.getString(1));
            }

            comboBox.setItems(options);
            connection.result.close();
            connection.stat.close();
        } catch (SQLException ex) {
            System.out.println("Terjadi error saat mengisi ComboBox: " + ex);
        }
    }

    @FXML
    protected void onBtnSimpanClick() {
        String idRumah = txtIdRumah.getText();
        String idPerumahan = cmbIdPerumahan.getValue();
        String blok = txtBlok.getText();
        int dayaListrik = Integer.parseInt(txtDayaListrik.getText());
        String interior = txtInterior.getText();
        int jmlKmrTdr = Integer.parseInt(txtJmlKmrTdr.getText());
        int jmlKmrMdn = Integer.parseInt(txtJmlKmrMdn.getText());
        String idTipe = cmbIdTipe.getValue();
        String description = txtDescription.getText();
        double uangMuka = Double.parseDouble(txtUangMuka.getText());
        double harga = Double.parseDouble(txtHarga.getText());
        Date thnBangun = Date.valueOf(txtThnBangun.getText());


        try {
            String query = "{call sp_inputRumah(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            connection.pstat = connection.conn.prepareCall(query);
            connection.pstat.setString(1, idRumah);
            connection.pstat.setString(2, idPerumahan);
            connection.pstat.setBinaryStream(3, new FileInputStream(fotoFile), (int) fotoFile.length());
            connection.pstat.setString(4, blok);
            connection.pstat.setInt(5, dayaListrik);
            connection.pstat.setString(6, interior);
            connection.pstat.setInt(7, jmlKmrTdr);
            connection.pstat.setInt(8, jmlKmrMdn);
            connection.pstat.setString(9, idTipe);
            connection.pstat.setString(10, description);
            connection.pstat.setDouble(11, uangMuka);
            connection.pstat.setDouble(12, harga);
            connection.pstat.setDate(13, thnBangun);


            connection.pstat.executeUpdate();
            connection.pstat.close();

            clear();
            initialize();
        } catch (SQLException | IOException ex) {
            System.out.println("Terjadi error saat insert data rumah: " + ex);
        }
    }

    @FXML
    public void chooseImageFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Pilih Foto");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            txtFotoPath.setText(selectedFile.getAbsolutePath());
            fotoFile = selectedFile;
        }
    }

    private void clear() {
        txtIdRumah.clear();
        cmbIdPerumahan.getSelectionModel().clearSelection();
        txtBlok.clear();
        txtDayaListrik.clear();
        txtInterior.clear();
        txtJmlKmrTdr.clear();
        txtJmlKmrMdn.clear();
        cmbIdTipe.getSelectionModel().clearSelection();
        txtDescription.clear();
        txtUangMuka.clear();
        txtHarga.clear();
        txtThnBangun.clear();
        txtKetersediaan.clear();
        txtFotoPath.clear();
        messageLabel.setText("");
    }

    @FXML
    protected void onBatalSimpanClick() {
        System.exit(0);
    }
}
