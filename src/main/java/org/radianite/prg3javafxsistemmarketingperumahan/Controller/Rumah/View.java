package org.radianite.prg3javafxsistemmarketingperumahan.Controller.Rumah;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Rumah;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class View implements Initializable {

    private Database connection = new Database();
    @FXML
    private TableView<Rumah> tableRumah;
    @FXML
    private TableColumn<Rumah, String> idRumah;
    @FXML
    private TableColumn<Rumah, String> idPerumahan;
    @FXML
    private TableColumn<Rumah, String> blok;
    @FXML
    private TableColumn<Rumah, Integer> dayaListrik;
    @FXML
    private TableColumn<Rumah, String> interior;
    @FXML
    private TableColumn<Rumah, Integer> jmlKmrTdr;
    @FXML
    private TableColumn<Rumah, Integer> jmlKmrMdn;
    @FXML
    private TableColumn<Rumah, String> idTipe;
    @FXML
    private TableColumn<Rumah, String> description;
    @FXML
    private TableColumn<Rumah, Double> uangMuka;
    @FXML
    private TableColumn<Rumah, Double> harga;
    @FXML
    private TableColumn<Rumah, String> thnBangun;
    @FXML
    private TableColumn<Rumah, Integer> ketersediaan;
    @FXML
    private TableColumn<Rumah, Integer> status;
    @FXML
    private TableColumn<Rumah, Void> action;

    @FXML
    private TableColumn<Rumah, ImageView> fotoRumah;

    private ObservableList<Rumah> rumahList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
    }

    private void loadData() {
        rumahList.clear();
        try {
            connection.stat = connection.conn.createStatement();
            String query = "SELECT * FROM ms_rumah";
            connection.result = connection.stat.executeQuery(query);

            while (connection.result.next()) {
                // Ambil gambar dari database sebagai InputStream
                InputStream inputStream = connection.result.getBinaryStream("foto_rumah");
                ImageView imageView = null;
                if (inputStream != null) {
                    // Buat Image dari InputStream dan masukkan ke ImageView
                    Image image = new Image(inputStream);
                    imageView = new ImageView(image);
                    imageView.setFitWidth(100); // Sesuaikan lebar gambar
                    imageView.setFitHeight(100); // Sesuaikan tinggi gambar
                }

                rumahList.add(new Rumah(
                        connection.result.getString("id_rumah"),
                        connection.result.getString("id_perumahan"),
                        imageView, // Set imageView sebagai fotoRumah
                        connection.result.getString("blok"),
                        connection.result.getInt("daya_listrik"),
                        connection.result.getString("interior"),
                        connection.result.getInt("jml_kmr_tdr"),
                        connection.result.getInt("jml_kmr_mdn"),
                        connection.result.getString("id_tipe"),
                        connection.result.getString("description"), // Perbaikan ejaan
                        connection.result.getDouble("harga"),
                        connection.result.getDate("thn_bangun"), // Ubah dari Date ke String
                        connection.result.getInt("ketersediaan"),
                        connection.result.getInt("status")
                ));
            }
            connection.stat.close();
            connection.result.close();
        } catch (Exception ex) {
            System.out.println("Terjadi error saat load data: " + ex);
        }

        idRumah.setCellValueFactory(new PropertyValueFactory<>("idRumah"));
        idPerumahan.setCellValueFactory(new PropertyValueFactory<>("idPerumahan"));
        blok.setCellValueFactory(new PropertyValueFactory<>("blok"));
        dayaListrik.setCellValueFactory(new PropertyValueFactory<>("dayaListrik"));
        interior.setCellValueFactory(new PropertyValueFactory<>("interior"));
        jmlKmrTdr.setCellValueFactory(new PropertyValueFactory<>("jmlKmrTdr"));
        jmlKmrMdn.setCellValueFactory(new PropertyValueFactory<>("jmlKmrMdn"));
        idTipe.setCellValueFactory(new PropertyValueFactory<>("idTipe"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        uangMuka.setCellValueFactory(new PropertyValueFactory<>("uangMuka")); // Ditambahkan
        harga.setCellValueFactory(new PropertyValueFactory<>("harga"));
        thnBangun.setCellValueFactory(new PropertyValueFactory<>("thnBangun"));
        ketersediaan.setCellValueFactory(new PropertyValueFactory<>("ketersediaan"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        fotoRumah.setCellValueFactory(new PropertyValueFactory<>("fotoRumah")); // Ditambahkan

        status.setCellFactory(column -> new TableCell<Rumah, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item == 1 ? "Tersedia" : "Tidak Tersedia");
                }
            }
        });

        // Cek apakah kolom "Action" sudah ada
        if (tableRumah.getColumns().contains(action)) {
            tableRumah.getColumns().remove(action);
        }


        action.setCellFactory(new Callback<TableColumn<Rumah, Void>, TableCell<Rumah, Void>>() {
            @Override
            public TableCell<Rumah, Void> call(final TableColumn<Rumah, Void> param) {
                return new TableCellWithButton();
            }
        });

        // Tambahkan kembali kolom "Action" hanya sekali
        if (!tableRumah.getColumns().contains(action)) {
            tableRumah.getColumns().add(action);
        }

        tableRumah.setItems(rumahList);
    }

    public class TableCellWithButton extends TableCell<Rumah, Void> {
        private final Button editButton = new Button("Edit");
        private final Button deleteButton = new Button("Delete");

        public TableCellWithButton() {
            editButton.setOnAction(event -> {
                Rumah rumah = getTableView().getItems().get(getIndex());
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/radianite/prg3javafxsistemmarketingperumahan/Master/Rumah/Update.fxml"));
                    Parent parent = loader.load();

                    Update controller = loader.getController();
                    controller.setRumah(rumah);

                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setScene(new Scene(parent));
                    stage.showAndWait();

                    // Refresh data in the table
                    loadData();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            deleteButton.setOnAction(event -> {
                Rumah rumah = getTableView().getItems().get(getIndex());
                deleteDataFromDatabase(rumah);
                loadData();
            });

            HBox hbox = new HBox(5);
            hbox.getChildren().addAll(editButton, deleteButton);
            setGraphic(hbox);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }

        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                setGraphic(new HBox(5, editButton, deleteButton));
            }
        }
    }

    private void deleteDataFromDatabase(Rumah rumah) {
        try {
            String query = "{call sp_deleteRumah(?)}";
            connection.pstat = connection.conn.prepareCall(query);
            connection.pstat.setString(1, rumah.getIdRumah());

            connection.pstat.executeUpdate();
            connection.pstat.close();
            System.out.println("Data berhasil dihapus dari database.");
        } catch (SQLException ex) {
            System.out.println("Terjadi error saat menghapus data dari database: " + ex);
        }
    }
}
