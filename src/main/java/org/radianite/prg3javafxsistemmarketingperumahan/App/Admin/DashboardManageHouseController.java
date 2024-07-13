package org.radianite.prg3javafxsistemmarketingperumahan.App.Admin;

import javafx.animation.FadeTransition;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class DashboardManageHouseController {

    @FXML private Text textNama; // Text untuk menampilkan nama pengguna
    @FXML
    private Button btnView, btnAdd;
    ArrayList<User> userList = new ArrayList<>(); // Daftar pengguna
    @FXML
    AnchorPane AncoreMaster;

    @FXML
    private void initialize() {
        setPane("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/Master/Rumah/ViewRumah.fxml");
    }

    public void setDataList(User data) {
        userList.add(data);
    }

    @FXML
    public void btnAddClick() {
        setPane("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/Master/Rumah/InputRumah.fxml");
        btnView.setOpacity(1.0);
        btnView.setDisable(false);
        btnAdd.setDisable(true);
        btnAdd.setOpacity(0.0);
        // Mengganti tampilan pane dengan form tambah developer
    }

    // Metode untuk menangani klik tombol "View"
    @FXML
    public void btnViewClick() {
        setPane("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/Master/Rumah/ViewRumah.fxml");
        // Mengganti tampilan pane dengan form lihat developer
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
