package org.radianite.prg3javafxsistemmarketingperumahan.Controller.Role;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Methods.Library;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Role;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ViewUpdateDelete extends Library implements Initializable {

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
    ObservableList<Role> roleList = FXCollections.observableArrayList();
    @FXML
    private Button btnAdd;
    @FXML
    private TextField txtSearch;
    @FXML
    private AnchorPane GroupMenu;
    @FXML
    private void btnAdd() {
        setPane("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/Master/Role/Input.fxml", null);

    }

    private void setPane(String fxml, Role data) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent pane = loader.load();
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), GroupMenu);
            GroupMenu.setOpacity(1.0);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setOnFinished(eventFadeOut -> {
                GroupMenu.getChildren().setAll(pane);
                if (fxml.equals("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/Master/Role/Update.fxml")) {
                    UpdateRole controller = loader.getController();
                    controller.setRole(data);
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
        ObservableList<Role> filteredList = FXCollections.observableArrayList();

        // Jika search text kosong, load semua data dan atur ulang tombol edit dan delete
        if (searchText.isEmpty()) {
            tableRole.setItems(roleList);
            tableRole.refresh(); // Menambahkan ini untuk mereset tampilan tabel
            tableRole.refresh(); // Menambahkan ini untuk mereset tampilan tabel
        } else {
            for (Role role : tableRole.getItems()) {
                if (role.getNamaRole().toLowerCase().contains(searchText)) {
                    filteredList.add(role);
                }
            }
            tableRole.setItems(filteredList);
            tableRole.refresh(); // Menambahkan ini untuk mereset tampilan tabel
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loaddata();
    }

    public void loaddata() {
        roleList.clear();
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
            editButton.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
            editButton.setOnMouseEntered(event -> {
                editButton.setStyle("-fx-background-color: darkblue; -fx-text-fill: white;");
            });
            editButton.setOnMouseExited(event -> {
                editButton.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
            });
            editButton.setOnAction(event -> {
               setPane("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/Master/Role/Update.fxml", roleList.get(getIndex()));
            });
            deleteButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
            deleteButton.setOnMouseEntered(event -> {
                deleteButton.setStyle("-fx-background-color: darkred; -fx-text-fill: white;");
            });
            deleteButton.setOnMouseExited(event -> {
                deleteButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
            });
            deleteButton.setOnAction(event -> {
                confirmBox("sp_deleteRole", roleList.get(getIndex()).getIdRole(),btnAdd);
                loaddata();
            });
            HBox hbox = new HBox(5);
            hbox.getChildren().addAll(editButton, deleteButton);
            hbox.setSpacing(10);
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
