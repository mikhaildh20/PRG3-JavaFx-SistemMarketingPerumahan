package org.radianite.prg3javafxsistemmarketingperumahan.App.Admin;

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
import org.radianite.prg3javafxsistemmarketingperumahan.Controller.Developer.Update;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Developer;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class DashboardManageShopHouseController {

    @FXML
    private TextField txtIdDeveloper;
    @FXML
    private TextField txtNamaDeveloper;
    @FXML
    private TableView tblDeveloper;
    @FXML
    private Text textNama;

    Database connection = new Database();

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
                }
            }
        });

        colAction.setCellFactory(new Callback<TableColumn<Developer, Void>, TableCell<Developer, Void>>() {
            @Override
            public TableCell<Developer, Void> call(final TableColumn<Developer, Void> param) {
                return new DashboardManageShopHouseController.TableCellWithButton();
            }
        });

        tblDeveloper.setItems(developerList); // Ganti dari roleList menjadi developerList
    }

    public class TableCellWithButton extends TableCell<Developer, Void> {
        private final Button editButton = new Button("Edit");
        private final Button deleteButton = new Button("Delete");

        public TableCellWithButton() {
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

            HBox hbox = new HBox(5);
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

    private void deleteDataFromDatabase(Developer developer) { // Ganti dari Role menjadi Developer
        try {
            String query = "{call sp_deleteDeveloper(?, ?)}"; // Ganti dari sp_deleteRole menjadi sp_deleteDeveloper
            connection.pstat = connection.conn.prepareCall(query);
            connection.pstat.setString(1, developer.getIdDeveloper()); // Ganti dari getIdRole menjadi getIdDeveloper
            connection.pstat.setString(2, developer.getNamaDeveloper()); // Ganti dari getNamaRole menjadi getNamaDeveloper

            connection.pstat.executeUpdate();
            connection.pstat.close();
            System.out.println("Data berhasil dihapus dari database.");
        } catch (SQLException ex) {
            System.out.println("Terjadi error saat menghapus data dari database: " + ex);
        }
    }

}
