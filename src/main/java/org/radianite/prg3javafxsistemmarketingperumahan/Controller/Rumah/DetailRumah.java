package org.radianite.prg3javafxsistemmarketingperumahan.Controller.Rumah;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Ruko;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Rumah;

public class DetailRumah {
    @FXML
    private Label labelBlok;
    @FXML
    private Label labelPerumahan;
    @FXML
    private Label labelHarga;
    @FXML
    private Label labelTegangan;
    @FXML
    private Label labelKetersediaan;
    @FXML
    private Label labelToilet;
    @FXML
    private Label labelDeskripsi;
    @FXML
    private Label labelType;
    @FXML
    private Label labelBedroom;
    @FXML
    private Label labelYear;


    @FXML
    private ImageView imageFoto;
    @FXML
    private Button btnBack;
    @FXML
    private AnchorPane AncoreMaster;


    public void setData(Rumah rumah){
        System.out.println(rumah.getBlok());
        labelBlok.setText(rumah.getBlok());
        labelPerumahan.setText(rumah.getResidence());
        labelHarga.setText("Rp " + String.format("%,.0f", rumah.getHarga()));
        labelTegangan.setText(String.valueOf(rumah.getWatt()));
        labelToilet.setText(String.valueOf(rumah.getBed()));
        labelDeskripsi.setText(rumah.getDesc());
        labelKetersediaan.setText(rumah.getKetersediaan() == 1 ? "Available" : "Sold");
        labelKetersediaan.setStyle(rumah.getKetersediaan() == 1 ? "-fx-text-fill: green;" : "-fx-text-fill: red;");
        imageFoto.setImage(rumah.getFoto());
        labelType.setText(rumah.getType());
        labelBedroom.setText(String.valueOf(rumah.getRest()));
        labelYear.setText(String.valueOf(rumah.getTahun()));
    }

    public void back() {
        setPane("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/Master/Rumah/viewRumah.fxml");
    }

    private void setPane(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent pane = loader.load(); // Memuat file FXML

            FadeTransition fadeOut = new FadeTransition(Duration.millis(500), AncoreMaster);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setOnFinished(event -> {
                AncoreMaster.getChildren().setAll(pane); // Mengganti seluruh konten di dalam AncoreMaster dengan konten dari pane yang baru dimuat

                FadeTransition fadeIn = new FadeTransition(Duration.millis(500), AncoreMaster);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeIn.play();
            });
            fadeOut.play();
        } catch (Exception e) {
            e.printStackTrace(); // Menangani kesalahan
        }
    }
}
