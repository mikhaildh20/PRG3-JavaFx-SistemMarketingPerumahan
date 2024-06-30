package org.radianite.prg3javafxsistemmarketingperumahan.Controller.TipeRumah;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.TipeRumah;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ViewUpdateDelete implements Initializable {

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loaddata();
    }
    private void loaddata() {
        ObservableList<TipeRumah> TPList = FXCollections.observableArrayList();
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
                } else {
                    setText(item == 1 ? "Tersedia" : "Tidak Tersedia");
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
            editButton.setOnAction(event -> {
                TipeRumah tipeRumah = getTableView().getItems().get(getIndex());
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/radianite/prg3javafxsistemmarketingperumahan/Master/TipeRumah/UpdateTipeRumah.fxml"));
                    Parent parent = loader.load();

                    Update controller = loader.getController();
                    controller.setTipeRumah(tipeRumah);

                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setScene(new Scene(parent));
                    stage.showAndWait();

                    getTableView().refresh();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            // Konfigurasi aksi untuk tombol delete
            deleteButton.setOnAction(event -> {
                TipeRumah tipeRumah = getTableView().getItems().get(getIndex());
                deleteDataFromDatabase(tipeRumah);
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
