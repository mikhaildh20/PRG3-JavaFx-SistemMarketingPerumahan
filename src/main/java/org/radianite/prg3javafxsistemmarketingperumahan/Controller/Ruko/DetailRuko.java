package org.radianite.prg3javafxsistemmarketingperumahan.Controller.Ruko;

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
import org.radianite.prg3javafxsistemmarketingperumahan.Models.User;

public class DetailRuko {
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
    private ImageView imageFoto;
    @FXML
    private Button btnBack;
    @FXML
    private AnchorPane AncoreMaster;


    public void setData(Ruko ruko){
        System.out.println(ruko.getBlok());
        labelBlok.setText(ruko.getBlok());
        labelPerumahan.setText(ruko.getNamaperum());
        labelHarga.setText("Rp " + String.format("%,.0f", ruko.getRent()));
        labelTegangan.setText(String.valueOf(ruko.getElectric()+"Va"));
        labelToilet.setText(String.valueOf(ruko.getToilet()));
        labelDeskripsi.setText(ruko.getDesc());
        labelKetersediaan.setText(ruko.getKetersediaan() == 0 ? "Available" : "Rented");
        labelKetersediaan.setStyle(ruko.getKetersediaan() == 0 ? "-fx-text-fill: green;" : "-fx-text-fill: red;");
        imageFoto.setImage(ruko.getFoto());
    }

    public void back() {
        setPane("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/Master/Ruko/View.fxml");
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
