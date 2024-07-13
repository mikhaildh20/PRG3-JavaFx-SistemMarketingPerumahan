package org.radianite.prg3javafxsistemmarketingperumahan.Controller.User;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.User;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;

public class DetailUser {
    @FXML
    private Label labelNama;
    @FXML
    private Label labelEmail;
    @FXML
    private Label labelAlamat;
    @FXML
    private Label labelKelamin;
    @FXML
    private Label labelUmur;
    @FXML
    private Label labelRole;
    @FXML
    private Label labelUsn;
    @FXML
    private Label labelPsw;
    @FXML
    private ImageView imageFoto;
    @FXML
    private Button btnBack;
    @FXML
    private AnchorPane AncoreMaster;


    public void setData(User user){
        labelUsn.setText(user.getUsn());
        labelPsw.setText(user.getPass());
        labelRole.setText(user.getRName());
        labelNama.setText(user.getName());
        labelEmail.setText(user.getEmail());
        labelUmur.setText(user.getAge().toString());
        labelKelamin.setText(user.getGender());
        imageFoto.setImage(user.getFoto());
        labelAlamat.setText(user.getAddress());

    }

    public void back() {
        setPane("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/Master/User/viewUser.fxml");
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
