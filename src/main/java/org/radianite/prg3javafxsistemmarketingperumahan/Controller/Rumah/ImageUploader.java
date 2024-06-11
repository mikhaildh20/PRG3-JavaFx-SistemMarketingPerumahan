package org.radianite.prg3javafxsistemmarketingperumahan.Controller.Rumah;

import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ImageUploader {
    public void uploadImageToDatabase(File imageFile, Connection connection) {
        try {
            // Baca foto sebagai larik byte
            byte[] imageData = convertImageToByteArray(imageFile);

            // Simpan foto ke database
            saveImageToDatabase(imageData, connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public byte[] convertImageToByteArray(File imageFile) throws Exception {
        FileInputStream fis = new FileInputStream(imageFile);
        byte[] imageData = new byte[(int) imageFile.length()];
        fis.read(imageData);
        fis.close();
        return imageData;
    }

    private void saveImageToDatabase(byte[] imageData, Connection connection) throws SQLException {
        String sql = "INSERT INTO photos (image_data) VALUES (?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setBytes(1, imageData);
            pstmt.executeUpdate();
        }
    }

    public File chooseImageFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Pilih Foto");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        return fileChooser.showOpenDialog(null);
    }
}
