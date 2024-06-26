package org.radianite.prg3javafxsistemmarketingperumahan.App.Admin;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Controller.Role.UpdateRole;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Role;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class DashboardManageRoleController {

    @FXML
    private TextField txtIdRole; // TextField untuk ID Role
    @FXML
    private TextField txtNamaRole; // TextField untuk Nama Role
    @FXML
    TableView<Role> tblRole = new TableView<Role>(); // TableView untuk menampilkan daftar Role
    @FXML
    private Text textNama; // Text untuk menampilkan nama pengguna

    Database connection = new Database(); // Inisialisasi koneksi ke database

    @FXML
    private TableColumn<Role, String> colIdRole; // Kolom untuk ID Role
    @FXML
    private TableColumn<Role, String> colNamaRole; // Kolom untuk Nama Role
    @FXML
    private TableColumn<Role, Integer> colStatus; // Kolom untuk Status Role
    @FXML
    private TableColumn<Role, Void> colAction; // Kolom untuk aksi (edit dan delete)
    @FXML
    private TableColumn<Role, Void> colNo; // Kolom untuk nomor urut
    ArrayList<User> userList = new ArrayList<>(); // Daftar pengguna

    @FXML
    private void initialize() {
        loaddata(); // Memuat data saat inisialisasi
    }

    public void setDataList(User data) {
        userList.add(data);
        textNama.setText(data.getName()); // Menampilkan nama pengguna pada textNama
    }

    public void loaddata() {
        ObservableList<Role> roleList = FXCollections.observableArrayList(); // Membuat daftar Role
        try {
            connection.stat = connection.conn.createStatement(); // Membuat statement
            String query = "SELECT * FROM ms_role"; // Query untuk mengambil data dari ms_role
            connection.result = connection.stat.executeQuery(query); // Eksekusi query

            while (connection.result.next()) {
                // Menambahkan data Role ke dalam roleList
                roleList.add(new Role(
                        connection.result.getString("id_role"),
                        connection.result.getString("nama_role"),
                        connection.result.getInt("status")));
            }
            connection.stat.close(); // Menutup statement
            connection.result.close(); // Menutup result set
        } catch (Exception ex) {
            System.out.println("Terjadi error saat load data: " + ex); // Menangani error
        }

        colIdRole.setCellValueFactory(new PropertyValueFactory<>("idRole")); // Menghubungkan kolom dengan property idRole
        colNamaRole.setCellValueFactory(new PropertyValueFactory<>("namaRole")); // Menghubungkan kolom dengan property namaRole
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status")); // Menghubungkan kolom dengan property status

        colStatus.setCellFactory(column -> new TableCell<Role, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null); // Jika data kosong, tidak menampilkan teks
                } else {
                    setText(item == 1 ? "Tersedia" : "Tidak Tersedia"); // Menampilkan status
                    if (item == 1) {
                        setStyle(" -fx-text-fill: green; -fx-font-weight: bold;"); // Menambahkan gaya untuk status tersedia
                    } else if (item == 0) {
                        setStyle("-fx-text-fill: red; -fx-font-weight: bold;"); // Menambahkan gaya untuk status tidak tersedia
                    }
                }
            }
        });

        colAction.setCellFactory(new Callback<TableColumn<Role, Void>, TableCell<Role, Void>>() {
            @Override
            public TableCell<Role, Void> call(final TableColumn<Role, Void> param) {
                return new TableCellWithButton(); // Mengatur kolom aksi untuk menampilkan tombol
            }
        });

        tblRole.setItems(roleList); // Mengatur items pada tabel dengan roleList
    }

    public class TableCellWithButton extends TableCell<Role, Void> {
        private final Button editButton = new Button("Edit"); // Tombol edit
        private final Button deleteButton = new Button("Delete"); // Tombol delete

        public TableCellWithButton() {
            // Mengatur gaya tombol
            editButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
            deleteButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");

            editButton.setOnAction(event -> {
                Role role = getTableView().getItems().get(getIndex()); // Mendapatkan Role yang dipilih
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/radianite/prg3javafxsistemmarketingperumahan/Master/Role/Update.fxml"));
                    Parent parent = loader.load();

                    UpdateRole controller = loader.getController();
                    controller.setRole(role); // Mengatur Role yang dipilih ke controller

                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setScene(new Scene(parent));
                    stage.showAndWait(); // Menampilkan jendela modal

                    getTableView().refresh(); // Menyegarkan tabel
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            deleteButton.setOnAction(event -> {
                Role role = getTableView().getItems().get(getIndex()); // Mendapatkan Role yang dipilih
                deleteDataFromDatabase(role); // Menghapus data dari database
                loaddata(); // Memuat ulang data
            });

            HBox hbox = new HBox(5); // Mengatur layout HBox dengan jarak antar tombol 5
            hbox.getChildren().addAll(editButton, deleteButton); // Menambahkan tombol ke HBox
            setGraphic(hbox); // Menampilkan HBox pada cell
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY); // Hanya menampilkan grafik
        }

        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null); // Jika cell kosong, tidak menampilkan grafik
            } else {
                setGraphic(new HBox(5, editButton, deleteButton)); // Menampilkan tombol edit dan delete pada cell
            }
        }
    }

    private void deleteDataFromDatabase(Role role) {
        try {
            String query = "{call sp_deleteRole(?)}"; // Query untuk menghapus Role
            connection.pstat = connection.conn.prepareCall(query);
            connection.pstat.setString(1, role.getIdRole()); // Mengatur parameter ID Role

            connection.pstat.executeUpdate(); // Eksekusi query
            connection.pstat.close(); // Menutup PreparedStatement
            System.out.println("Data berhasil dihapus dari database."); // Pesan sukses
        } catch (SQLException ex) {
            System.out.println("Terjadi error saat menghapus data dari database: " + ex); // Menangani error
        }
    }
}
