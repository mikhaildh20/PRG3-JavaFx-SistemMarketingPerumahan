package org.radianite.prg3javafxsistemmarketingperumahan.Controller.Rumah;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Rumah;

import java.io.File;
import java.sql.Date;
import java.sql.SQLException;

public class Update {

    @FXML
    private TextField idRumahField;
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
    private TextField txtFotoPath;
    private File fotoFile;

    private Rumah rumah;
    private Database connection = new Database();
    private ImageUploader imageUploader = new ImageUploader();

    public Update() {

    }
    public void initialize() {
        populateComboBox(cmbIdPerumahan, "SELECT nama_perumahan FROM ms_perumahan where status = 1 ");
        populateComboBox(cmbIdTipe, "SELECT nama_tipe FROM ms_tipe_rumah where status = 1 ");
    }

    public void setRumah(Rumah rumah) {
        this.rumah = rumah;
        idRumahField.setText(rumah.getIdRumah());
        cmbIdPerumahan.setValue(rumah.getIdPerumahan());
        txtBlok.setText(rumah.getBlok());
        txtDayaListrik.setText(String.valueOf(rumah.getDayaListrik()));
        txtInterior.setText(rumah.getInterior());
        txtJmlKmrTdr.setText(String.valueOf(rumah.getJmlKmrTdr()));
        txtJmlKmrMdn.setText(String.valueOf(rumah.getJmlKmrMdn()));
        cmbIdTipe.setValue(rumah.getIdTipe());
        txtDescription.setText(rumah.getDescription());
        txtHarga.setText(String.valueOf(rumah.getHarga()));
        txtThnBangun.setText(rumah.getThnBangun().toString());

    }

    @FXML
    private void handleUpdateAction() {
        rumah.setIdPerumahan(cmbIdPerumahan.getValue());
        rumah.setBlok(txtBlok.getText());
        rumah.setDayaListrik(Integer.parseInt(txtDayaListrik.getText()));
        rumah.setInterior(txtInterior.getText());
        rumah.setJmlKmrTdr(Integer.parseInt(txtJmlKmrTdr.getText()));
        rumah.setJmlKmrMdn(Integer.parseInt(txtJmlKmrMdn.getText()));
        rumah.setIdTipe(cmbIdTipe.getValue());
        rumah.setDescription(txtDescription.getText());

        rumah.setHarga(Double.parseDouble(txtHarga.getText()));
        rumah.setThnBangun(Date.valueOf(txtThnBangun.getText()));

        try {
            updateDataInDatabase(rumah);
        } catch (Exception e) {
            e.printStackTrace();
            // Tampilkan pesan error jika diperlukan
        }

        // Tutup form setelah update
        Stage stage = (Stage) idRumahField.getScene().getWindow();
        stage.close();
    }

    private void updateDataInDatabase(Rumah rumah) throws Exception {
        try {
            String query = "{call sp_updateRumah(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            connection.pstat = connection.conn.prepareCall(query);
            connection.pstat.setString(1, rumah.getIdRumah());
            connection.pstat.setString(2, rumah.getIdPerumahan());
            if (fotoFile != null) {
                byte[] imageData = imageUploader.convertImageToByteArray(fotoFile);
                connection.pstat.setBytes(3, imageData);
            } else {
                connection.pstat.setBytes(3, null);
            }
            connection.pstat.setString(4, rumah.getBlok());
            connection.pstat.setInt(5, rumah.getDayaListrik());
            connection.pstat.setString(6, rumah.getInterior());
            connection.pstat.setInt(7, rumah.getJmlKmrTdr());
            connection.pstat.setInt(8, rumah.getJmlKmrMdn());
            connection.pstat.setString(9, rumah.getIdTipe());
            connection.pstat.setString(10, rumah.getDescription());
            connection.pstat.setDouble(11, rumah.getHarga());
            connection.pstat.setDate(13, rumah.getThnBangun());

            connection.pstat.executeUpdate();
            connection.pstat.close();

        } catch (SQLException ex) {
            System.out.println("Terjadi error saat Update data rumah: " + ex);
        }
    }

    @FXML
    private void chooseImage() {
        System.out.println("chooseImage method called");
        fotoFile = imageUploader.chooseImageFile();
        if (fotoFile != null) {
            txtFotoPath.setText(fotoFile.getAbsolutePath());
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
}
