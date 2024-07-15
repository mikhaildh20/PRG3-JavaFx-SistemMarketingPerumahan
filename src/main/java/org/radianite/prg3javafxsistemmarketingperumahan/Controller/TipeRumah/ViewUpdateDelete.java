package org.radianite.prg3javafxsistemmarketingperumahan.Controller.TipeRumah;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Methods.Library;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Developer;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.TipeRumah;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ViewUpdateDelete extends Library implements Initializable {

    private Database connection = new Database();
    @FXML
    private TableView<TipeRumah> tableTipeRumah;
    @FXML
    private TableColumn<TipeRumah, String> idTipe;
    @FXML
    private TableColumn<TipeRumah, String> nama;
    @FXML
    private TableColumn<TipeRumah, Integer> status;
    @FXML
    private TableColumn<TipeRumah, Void> actionCol;
    ObservableList<TipeRumah> TPList = FXCollections.observableArrayList();

    @FXML
    private Button btnSerach;
    @FXML
    private TextField txtSearch;

    @FXML
    private AnchorPane GroupMenu;
    @FXML
    private void btnAdd() {
        setPane("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/Master/TipeRumah/Input.fxml", null);

    }

    private void setPane(String fxml, TipeRumah data) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent pane = loader.load();
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), GroupMenu);
            GroupMenu.setOpacity(1.0);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setOnFinished(eventFadeOut -> {
                GroupMenu.getChildren().setAll(pane);
                if (fxml.equals("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/Master/TipeRumah/UpdateTipeRumah.fxml")) {
                    Update controller = loader.getController();
                    controller.setTipeRumah(data);
                }
                FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), GroupMenu);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                ParallelTransition parallelTransition = new ParallelTransition(fadeIn);
                parallelTransition.play();
            });
            fadeOut.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSearch() {
        String searchText = txtSearch.getText().toLowerCase();
        ObservableList<TipeRumah> filteredList = FXCollections.observableArrayList();

        if (searchText.isEmpty()) {
            tableTipeRumah.setItems(TPList);
            tableTipeRumah.refresh();
        } else {
            for (TipeRumah tipeRumah : tableTipeRumah.getItems()) {
                if (tipeRumah.getNama().toLowerCase().contains(searchText)) {
                    filteredList.add(tipeRumah);
                }
            }
        }

        if (filteredList.isEmpty()) {
            tableTipeRumah.getItems().clear(); // Menghapus data dari tabel jika tidak ada yang cocok
        } else {
            tableTipeRumah.setItems(filteredList);
            tableTipeRumah.refresh(); // Menambahkan ini untuk mereset tampilan tabel
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loaddata();
    }
    private void loaddata() {
        TPList.clear();
        try {
            connection.stat = connection.conn.createStatement();
            String query = "SELECT * FROM ms_tipe_rumah";
            connection.result = connection.stat.executeQuery(query);

            while (connection.result.next()) {
                TPList.add(new TipeRumah(connection.result.getString("id_tipe"),
                        connection.result.getString("nama_tipe"),
                        connection.result.getInt("status")));
            }
            connection.stat.close();
            connection.result.close();
        } catch (Exception ex) {
            System.out.println("Terjadi error saat load data: " + ex);
        }

        idTipe.setCellValueFactory(new PropertyValueFactory<>("idTipe"));
        nama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        status.setCellFactory(column -> new TableCell<TipeRumah, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item == 1 ? "Available" : "Not Available");
                    setStyle(item == 1 ? "-fx-text-fill: green; -fx-font-weight: bold;" : "-fx-text-fill: red; -fx-font-weight: bold;");
                }
            }
        });


        actionCol.setCellFactory(new Callback<TableColumn<TipeRumah, Void>, TableCell<TipeRumah, Void>>() {
            @Override
            public TableCell<TipeRumah, Void> call(final TableColumn<TipeRumah, Void> param) {
                return new TableCellWithButton();
            }
        });

        tableTipeRumah.setItems(TPList);
    }

    public class TableCellWithButton extends TableCell<TipeRumah, Void> {
        private final Button editButton = new Button("Edit");
        private final Button deleteButton = new Button("Delete");

        public TableCellWithButton() {
            // Konfigurasi aksi untuk tombol edit
            editButton.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
            editButton.setOnMouseEntered(event -> {
                editButton.setStyle("-fx-background-color: darkblue; -fx-text-fill: white;");
            });
            editButton.setOnMouseExited(event -> {
                editButton.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
            });
            editButton.setOnAction(event -> {
                setPane("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/Master/TipeRumah/UpdateTipeRumah.fxml", TPList.get(getIndex()));
            });
            deleteButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
            deleteButton.setOnMouseEntered(event -> {
                deleteButton.setStyle("-fx-background-color: darkred; -fx-text-fill: white;");
            });
            deleteButton.setOnMouseExited(event -> {
                deleteButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
            });
            deleteButton.setOnAction(event -> {
                confirmBox("sp_deleteTipeRumah", TPList.get(getIndex()).getIdTipe(), btnSerach);
                loaddata();
            });

            // Membuat HBox yang berisi tombol edit dan delete
            HBox hbox = new HBox(5);
            hbox.getChildren().addAll(editButton, deleteButton);

            // Menetapkan HBox sebagai isi dari sel
            setGraphic(hbox);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }

        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                // Jika baris tabel kosong, tidak menampilkan tombol edit dan delete
                setGraphic(null);
            } else {
                // Menampilkan tombol edit dan delete pada setiap baris tabel
                setGraphic(new HBox(5, editButton, deleteButton));
            }
        }
    }


    private void deleteDataFromDatabase(TipeRumah tipeRumah) {
        try {
            // Membuat prepared statement untuk memanggil stored procedure
            String query = "{call sp_deleteTipeRumah(?)}";
            connection.pstat = connection.conn.prepareCall(query);
            connection.pstat.setString(1, tipeRumah.getIdTipe());

            connection.pstat.executeUpdate();
            connection.pstat.close();
            // Menampilkan pesan berhasil dihapus jika diperlukan
            System.out.println("Data berhasil dihapus dari database.");
        } catch (SQLException ex) {
            // Menampilkan pesan jika terjadi kesalahan saat menghapus data dari database
            System.out.println("Terjadi error saat menghapus data dari database: " + ex);
        }
    }

}
