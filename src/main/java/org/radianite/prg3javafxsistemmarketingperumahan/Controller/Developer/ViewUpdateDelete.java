package org.radianite.prg3javafxsistemmarketingperumahan.Controller.Developer;

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
import org.radianite.prg3javafxsistemmarketingperumahan.App.Admin.DashboardManageDeveloperController;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Controller.Rumah.DetailRumah;
import org.radianite.prg3javafxsistemmarketingperumahan.Controller.Rumah.updateRumah;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Developer;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Rumah;
import org.radianite.prg3javafxsistemmarketingperumahan.Methods.Library;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ViewUpdateDelete extends Library implements Initializable  {

    private Database connection = new Database();
    @FXML
    private TableView<Developer> tableDeveloper; // Ganti dari tableRole menjadi tableDeveloper
    @FXML
    private TableColumn<Developer, String> idDeveloper; // Ganti dari idRole menjadi idDeveloper
    @FXML
    private TableColumn<Developer, String> namaDeveloper; // Ganti dari namaRole menjadi namaDeveloper
    @FXML
    private TableColumn<Developer, Integer> status;
    @FXML
    private TableColumn<Developer, Void> actionCol;
    @FXML
    private Button btnSerach;
    @FXML
    private TextField txtSearch;
    ObservableList<Developer> developerList = FXCollections.observableArrayList(); // Ganti dari roleList menjadi developerList
    @FXML
    private AnchorPane GroupMenu;
    @FXML
    private void btnAdd() {
        setPane("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/Master/Developer/Add.fxml", null);

    }

    private void setPane(String fxml, Developer data) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent pane = loader.load();
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), GroupMenu);
            GroupMenu.setOpacity(1.0);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setOnFinished(eventFadeOut -> {
                GroupMenu.getChildren().setAll(pane);
                if (fxml.equals("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/Master/Developer/Update.fxml")) {
                    Update controller = loader.getController();
                    controller.setDeveloper(data);
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loaddata();
    }

    @FXML
    private void handleSearch() {
        String searchText = txtSearch.getText().toLowerCase();
        ObservableList<Developer> filteredList = FXCollections.observableArrayList();

        if (searchText.isEmpty()) {
            loaddata();
        } else {
            for (Developer developer : tableDeveloper.getItems()) {
                if (developer.getIdDeveloper().toLowerCase().contains(searchText) ||
                        developer.getNamaDeveloper().toLowerCase().contains(searchText)) {
                    filteredList.add(developer);
                }
            }
            tableDeveloper.setItems(filteredList);
        }
    }

    public void loaddata() {
    developerList.clear();
        try {
            connection.stat = connection.conn.createStatement();
            String query = "SELECT * FROM ms_developer";
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

        idDeveloper.setCellValueFactory(new PropertyValueFactory<>("idDeveloper")); // Ganti dari idRole menjadi idDeveloper
        namaDeveloper.setCellValueFactory(new PropertyValueFactory<>("namaDeveloper")); // Ganti dari namaRole menjadi namaDeveloper
        status.setCellValueFactory(new PropertyValueFactory<>("status"));

        status.setCellFactory(column -> new TableCell<Developer, Integer>() {
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

        actionCol.setCellFactory(new Callback<TableColumn<Developer, Void>, TableCell<Developer, Void>>() {
            @Override
            public TableCell<Developer, Void> call(final TableColumn<Developer, Void> param) {
                return new TableCellWithButton();
            }
        });

        tableDeveloper.setItems(developerList);
    }

    public class TableCellWithButton extends TableCell<Developer, Void> {
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
                setPane("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/Master/Developer/Update.fxml", developerList.get(getIndex()));
            });
            deleteButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
            deleteButton.setOnMouseEntered(event -> {
                deleteButton.setStyle("-fx-background-color: darkred; -fx-text-fill: white;");
            });
            deleteButton.setOnMouseExited(event -> {
                deleteButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
            });
            deleteButton.setOnAction(event -> {
                confirmBox("sp_deleteDeveloper", developerList.get(getIndex()).getIdDeveloper(),btnSerach);
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

}
