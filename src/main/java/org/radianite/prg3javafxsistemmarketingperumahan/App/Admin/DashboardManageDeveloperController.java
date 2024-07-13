package org.radianite.prg3javafxsistemmarketingperumahan.App.Admin;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Developer;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.User;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DashboardManageDeveloperController {

    // Deklarasi elemen-elemen FXML
    @FXML
    private TextField txtIdDeveloper, txtNamaDeveloper;
    @FXML
    private TableView<Developer> tblDeveloper = new TableView<>();
    @FXML
    private Text textNama, tglLabel;
    @FXML
    private Button btnView, btnAdd;
    @FXML
    private AnchorPane AncoreMaster;
    @FXML
    private TableColumn<Developer, String> colNo, colIdDeveloper, colNamaDeveloper;
    @FXML
    private TableColumn<Developer, Integer> colStatus;
    @FXML
    private TableColumn<Developer, Void> colAction;

    // Inisialisasi koneksi database dan daftar pengguna
    private final Database connection = new Database();
    private final ArrayList<User> userList = new ArrayList<>();

    // Metode untuk menambahkan data pengguna ke daftar dan menampilkan nama pengguna
    public void setDataList(User data) {
        userList.add(data);
    }

    // Metode inisialisasi yang dipanggil saat controller diinisialisasi
    @FXML
    private void initialize() {
        setPane("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/Master/Developer/View.fxml");
    }

    // Metode untuk membersihkan input nama developer
    private void clear() {
        txtNamaDeveloper.clear(); // Mengosongkan field nama developer
    }

    // Metode untuk menangani klik tombol "Add"
    @FXML
    public void btnAddClick() {
        setPane("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/Master/Developer/Add.fxml");
        btnView.setOpacity(1.0);
        btnView.setDisable(false);
        btnAdd.setDisable(true);
        btnAdd.setOpacity(0.0);

        // Mengganti tampilan pane dengan form tambah developer
    }

    // Metode untuk menangani klik tombol "View"
    @FXML
    public void btnViewClick() {
        setPane("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/Master/Developer/View.fxml");
    }


    // Metode untuk menghapus data developer dari database

    // Metode untuk memulai jam yang menampilkan waktu saat ini
    private void startClock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            tglLabel.setText(LocalDateTime.now().format(formatter)); // Mengatur label tanggal dengan waktu saat ini
        }), new KeyFrame(Duration.seconds(1))); // Memperbarui setiap detik

        clock.setCycleCount(Timeline.INDEFINITE); // Mengatur agar timeline berjalan terus menerus
        clock.play(); // Memulai timeline
    }

    // Metode untuk mengganti tampilan pane dengan file FXML yang diberikan
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
