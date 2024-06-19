package org.radianite.prg3javafxsistemmarketingperumahan.App.Admin;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.User;
import org.radianite.prg3javafxsistemmarketingperumahan.Methods.Library;
import java.io.IOException;
import java.util.List;

public class AncoreDasboardUtama {

    private List<User> users;

    @FXML
    private Text DasbordNama;
    @FXML
    private AnchorPane Group2;
    @FXML
    private AnchorPane Group3;
    @FXML
    private AnchorPane Group4;
    @FXML
    private AnchorPane Group5;
    @FXML
    private AnchorPane Group6;
    @FXML
    private AnchorPane Group7;
    @FXML
    private AnchorPane GroupMenu;


    // Setter untuk menerima list user dan menampilkan nama user pertama
    public void setUsersAdmin(List<User> users) {
        this.users = users;
        if (users != null && !users.isEmpty()) {
            DasbordNama.setText(users.get(0).getName() + " (Admin)");
        }
    }

    // Metode untuk memulai animasi saat memuat

    // Metode untuk mengatur animasi elemen-elemen UI
    public void animasi() {

        // Mengatur posisi awal elemen-elemen UI
        Group2.setTranslateY(-300);
        Group3.setTranslateY(-300);
        Group4.setTranslateY(-300);
        Group5.setTranslateY(-300);
        Group6.setTranslateY(-300);
        Group7.setTranslateY(-300);
        // Membuat animasi fade transition

        FadeTransition fadeGroup2 = createFadeTransition(Group2, 2);
        FadeTransition fadeGroup3 = createFadeTransition(Group3, 2);
        FadeTransition fadeGroup4 = createFadeTransition(Group4, 2);
        FadeTransition fadeGroup5 = createFadeTransition(Group5, 2);
        FadeTransition fadeGroup6 = createFadeTransition(Group6, 2);
        FadeTransition fadeGroup7 = createFadeTransition(Group7, 2);

        // Menggabungkan animasi paralel untuk group
        ParallelTransition parallelGroup = new ParallelTransition(

                fadeGroup2, fadeGroup3,
                fadeGroup4, fadeGroup5,
                fadeGroup6, fadeGroup7
        );
        // Memulai animasi
        parallelGroup.play();
    }

    // Membuat animasi fade transition
    private FadeTransition createFadeTransition(javafx.scene.Node node, double durationSeconds) {
        FadeTransition fade = new FadeTransition(Duration.seconds(durationSeconds), node);
        fade.setFromValue(0.0);
        fade.setToValue(1.0);
        return fade;
    }

    // Membuat animasi translate transition
    private TranslateTransition createTranslateTransition(javafx.scene.Node node, double durationSeconds) {
        TranslateTransition translate = new TranslateTransition(Duration.seconds(durationSeconds), node);
        translate.setToY(0);
        return translate;
    }

    private AnchorPane loadFXMLWithController(String fxmlPath, List<User> users) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            AnchorPane pane = loader.load();


            // Mendapatkan controller yang terkait dengan FXML
            AncoreDasboardUtama controller = loader.getController();

            // Mengirimkan data ke controller
            if (controller != null) {
                controller.setUsersAdmin(users);
            } else {
                System.err.println("Controller is null!");
            }

            return pane;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
