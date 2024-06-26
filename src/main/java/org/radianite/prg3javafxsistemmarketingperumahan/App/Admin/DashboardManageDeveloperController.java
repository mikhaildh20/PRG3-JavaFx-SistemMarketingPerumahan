package org.radianite.prg3javafxsistemmarketingperumahan.App.Admin;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Controller.Developer.Update;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Developer;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.User;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardManageDeveloperController {

    @FXML
    private TextField txtIdDeveloper;
    @FXML
    private TextField txtNamaDeveloper;
    @FXML
    TableView<Developer> tblDeveloper = new TableView<Developer>();
    @FXML
    private Text textNama;
    @FXML
    private Text tglLabel;
    private String nextIdDeveloper;
    String idDeveloper, namaDeveloper;

    Database connection = new Database();

    @FXML
    private TableColumn<Developer, String> colNo;
    @FXML
    private TableColumn<Developer, String> colIdDeveloper;
    @FXML
    private TableColumn<Developer, String> colNamaDeveloper;
    @FXML
    private TableColumn<Developer, Integer> colStatus;
    @FXML
    private TableColumn<Developer, Void> colAction;
    ArrayList<User> userList = new ArrayList<>();
    public void setDataList(User data){
        userList.add(data);
        textNama.setText(data.getName());
    }
    @FXML
    private void initialize() {
        loaddata();
        generateId();
        startClock();
    }

    public void loaddata() {
        ObservableList<Developer> developerList = FXCollections.observableArrayList(); // Ganti dari roleList menjadi developerList
        try {
            connection.stat = connection.conn.createStatement();
            String query = "SELECT * FROM ms_developer"; // Ganti dari ms_role menjadi ms_developer
            connection.result = connection.stat.executeQuery(query);

            while (connection.result.next()) {
                developerList.add(new Developer(connection.result.getString("id_developer"), // Ganti dari id_role menjadi id_developer
                        connection.result.getString("nama_developer"), // Ganti dari nama_role menjadi nama_developer
                        connection.result.getInt("status")));

            }
            connection.stat.close();
            connection.result.close();
        } catch (Exception ex) {
            System.out.println("Terjadi error saat load data: " + ex);
        }
        colIdDeveloper.setCellValueFactory(new PropertyValueFactory<>("idDeveloper")); // Ganti dari idRole menjadi idDeveloper
        colNamaDeveloper.setCellValueFactory(new PropertyValueFactory<>("namaDeveloper")); // Ganti dari namaRole menjadi namaDeveloper
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        colStatus.setCellFactory(column -> new TableCell<Developer, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item == 1 ? "Tersedia" : "Tidak Tersedia");
                    if (item == 1) {
                        setStyle(" -fx-text-fill: green; -fx-font-weight: bold;");
                    } else if (item == 0) {
                        setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                    }
                }
            }
        });
        colNo.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(tblDeveloper.getItems().indexOf(cellData.getValue()) + 1).asString()
        );

        colAction.setCellFactory(new Callback<TableColumn<Developer, Void>, TableCell<Developer, Void>>() {
            @Override
            public TableCell<Developer, Void> call(final TableColumn<Developer, Void> param) {
                return new DashboardManageDeveloperController.TableCellWithButton();
            }
        });

        tblDeveloper.setItems(developerList);
    }

    public void generateId() {
        try {
            // Mengambil id_developer terakhir dari tabel Developer
            String queryGetLastId = "SELECT id_developer FROM ms_developer ORDER BY id_developer DESC";
            connection.pstat = connection.conn.prepareStatement(queryGetLastId);
            connection.result = connection.pstat.executeQuery();

            if (connection.result.next()) {
                // Mengambil id_developer terakhir dan menambahkannya untuk mendapatkan id_developer baru
                String lastId = connection.result.getString("id_developer");
                int newIdNumber = Integer.parseInt(lastId.substring(3)) + 1; // Asumsikan id_developer memiliki format seperti "D01"
                nextIdDeveloper = "DVL" + String.format("%03d", newIdNumber); // Menghasilkan id_developer baru, misal "D02"
                txtIdDeveloper.setText(nextIdDeveloper);
            } else {
                // Jika tidak ada id_developer di tabel, mulai dari "D01"
                nextIdDeveloper = "DVL001";
                txtIdDeveloper.setText(nextIdDeveloper);
            }

            connection.result.close();
            connection.pstat.close();
        } catch (SQLException ex) {
            System.out.println("Terjadi error saat mengambil id_developer terakhir: " + ex);
        }
    }



    public class TableCellWithButton extends TableCell<Developer, Void> {
        private final Button editButton = new Button("Edit");
        private final Button deleteButton = new Button("Delete");

        public TableCellWithButton() {
            // Set button styles
            editButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
            deleteButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");

            editButton.setOnAction(event -> {
                Developer developer = getTableView().getItems().get(getIndex());
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/radianite/prg3javafxsistemmarketingperumahan/Master/Developer/Update.fxml"));
                    Parent parent = loader.load();

                    Update controller = loader.getController();
                    controller.setDeveloper(developer); // Ganti dari setRole menjadi setDeveloper

                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setScene(new Scene(parent));
                    stage.showAndWait();

                    getTableView().refresh();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            deleteButton.setOnAction(event -> {
                Developer developer = getTableView().getItems().get(getIndex());
                deleteDataFromDatabase(developer); // Ganti dari role menjadi developer
                loaddata();
            });

            HBox hbox = new HBox();
            hbox.setPadding(new Insets(100, 0, 5, 0));
            hbox.getChildren().addAll(editButton, deleteButton);
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


    @FXML
    protected void onBtnSimpanClick() {
        idDeveloper = txtIdDeveloper.getText();
        namaDeveloper = txtNamaDeveloper.getText();
        int status = 1;

        try {
            // Menggunakan stored procedure sp_inputDeveloper
            String query = "{call sp_inputDeveloper(?, ?)}";
            connection.pstat = connection.conn.prepareCall(query);
            connection.pstat.setString(1, idDeveloper);
            connection.pstat.setString(2, namaDeveloper);
            /* connection.pstat.setInt(3, status);*/

            connection.pstat.executeUpdate();
            connection.pstat.close();

            clear();
        } catch (SQLException ex) {
            System.out.println("Terjadi error saat insert data developer: " + ex);
        }
        try {
            // Mengambil id_developer terakhir dari tabel Developer
            String queryGetLastId = "SELECT id_developer FROM ms_developer ORDER BY id_developer DESC";
            connection.pstat = connection.conn.prepareStatement(queryGetLastId);
            connection.result = connection.pstat.executeQuery();

            if (connection.result.next()) {
                // Mengambil id_developer terakhir dan menambahkannya untuk mendapatkan id_developer baru
                String lastId = connection.result.getString("id_developer");
                int newIdNumber = Integer.parseInt(lastId.substring(1)) + 1; // Asumsikan id_developer memiliki format seperti "D01"
                nextIdDeveloper = "D" + String.format("%02d", newIdNumber); // Menghasilkan id_developer baru, misal "D02"
                txtIdDeveloper.setText(nextIdDeveloper);
            } else {
                // Jika tidak ada id_developer di tabel, mulai dari "D01"
                nextIdDeveloper = "D01";
                txtIdDeveloper.setText(nextIdDeveloper);
            }

            connection.result.close();
            connection.pstat.close();
        } catch (SQLException ex) {
            System.out.println("Terjadi error saat mengambil id_developer terakhir: " + ex);
        }
        loaddata();
    }

    private void clear() {
        txtNamaDeveloper.clear();
    }

    private void deleteDataFromDatabase(Developer developer) { // Ganti dari Role menjadi Developer
        try {
            String query = "{call sp_deleteDeveloper(?)}"; // Ganti dari sp_deleteRole menjadi sp_deleteDeveloper
            connection.pstat = connection.conn.prepareCall(query);
            connection.pstat.setString(1, developer.getIdDeveloper()); // Ganti dari getIdRole menjadi getIdDeveloper
            connection.pstat.executeUpdate();
            connection.pstat.close();
            System.out.println("Data berhasil dihapus dari database.");
        } catch (SQLException ex) {
            System.out.println("Terjadi error saat menghapus data dari database: " + ex);
        }
    }

    private void startClock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            tglLabel.setText(LocalDateTime.now().format(formatter));
        }),
                new KeyFrame(Duration.seconds(1))
        );

        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();
    }

}
