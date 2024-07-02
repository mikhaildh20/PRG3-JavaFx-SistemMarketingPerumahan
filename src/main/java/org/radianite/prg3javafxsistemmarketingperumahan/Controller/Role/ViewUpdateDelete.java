package org.radianite.prg3javafxsistemmarketingperumahan.Controller.Role;

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
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Role;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ViewUpdateDelete implements Initializable {

    private Database connection = new Database();
    @FXML
    private TableView<Role> tableRole;
    @FXML
    private TableColumn<Role, String> idRole;
    @FXML
    private TableColumn<Role, String> namaRole;
    @FXML
    private TableColumn<Role, Integer> status;
    @FXML
    private TableColumn<Role, Void> actionCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loaddata();
    }

    public void loaddata() {
        ObservableList<Role> roleList = FXCollections.observableArrayList();
        try {
            connection.stat = connection.conn.createStatement();
            String query = "SELECT * FROM ms_role";
            connection.result = connection.stat.executeQuery(query);

            while (connection.result.next()) {
                roleList.add(new Role(connection.result.getString("id_role"),
                        connection.result.getString("nama_role"),
                        connection.result.getInt("status")));
            }
            connection.stat.close();
            connection.result.close();
        } catch (Exception ex) {
            System.out.println("Terjadi error saat load data: " + ex);
        }

        idRole.setCellValueFactory(new PropertyValueFactory<>("idRole"));
        namaRole.setCellValueFactory(new PropertyValueFactory<>("namaRole"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));

        status.setCellFactory(column -> new TableCell<Role, Integer>() {
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

        actionCol.setCellFactory(new Callback<TableColumn<Role, Void>, TableCell<Role, Void>>() {
            @Override
            public TableCell<Role, Void> call(final TableColumn<Role, Void> param) {
                return new TableCellWithButton();
            }
        });

        tableRole.setItems(roleList);
    }

    public class TableCellWithButton extends TableCell<Role, Void> {
        private final Button editButton = new Button("Edit");   
        private final Button deleteButton = new Button("Delete");

        public TableCellWithButton() {
            editButton.setOnAction(event -> {
                Role role = getTableView().getItems().get(getIndex());
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/radianite/prg3javafxsistemmarketingperumahan/Master/Role/Update.fxml"));
                    Parent parent = loader.load();

                    Update controller = loader.getController();
                    controller.setRole(role);

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
                Role role = getTableView().getItems().get(getIndex());
                deleteDataFromDatabase(role);
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

    private void deleteDataFromDatabase(Role role) {
        try {
            String query = "{call sp_deleteRole(?)}";
            connection.pstat = connection.conn.prepareCall(query);
            connection.pstat.setString(1, role.getIdRole());

            connection.pstat.executeUpdate();
            connection.pstat.close();
            System.out.println("Data berhasil dihapus dari database.");
        } catch (SQLException ex) {
            System.out.println("Terjadi error saat menghapus data dari database: " + ex);
        }
    }
}
