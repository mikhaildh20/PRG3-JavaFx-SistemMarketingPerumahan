package org.radianite.prg3javafxsistemmarketingperumahan.Controller.Rumah;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.radianite.prg3javafxsistemmarketingperumahan.App.Admin.DashboardManageDeveloperController;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Controller.Developer.Update;
import org.radianite.prg3javafxsistemmarketingperumahan.Controller.Ruko.DetailRuko;
import org.radianite.prg3javafxsistemmarketingperumahan.Controller.Ruko.updateRuko;
import org.radianite.prg3javafxsistemmarketingperumahan.Methods.Library;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Developer;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Ruko;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Rumah;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class viewRumah extends Library implements Initializable {
    @FXML
    private TableView<Rumah> tableView;
    @FXML
    private TableColumn<Rumah, String> colId, colResidence, colType,colBlok;
    @FXML
    private TableColumn<Rumah, Double> colPrice;
    @FXML
    private TableColumn<Rumah, Void> colAction;
    @FXML
    private AnchorPane GroupMenu;
    private ObservableList<Rumah> listRumah = FXCollections.observableArrayList();
    @FXML
    private Button btnSerach;
    @FXML
    private TextField txtSearch;

    @FXML
    private void btnAdd() {
        setPanes("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/Master/Rumah/inputRumah.fxml", null);

    }

    private void setPanes(String fxml, Developer data) {
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

    @FXML
    private void handleSearch() {
        String searchText = txtSearch.getText().toLowerCase();
        ObservableList<Rumah> filteredList = FXCollections.observableArrayList();

        if (searchText.isEmpty()) {
            loadData();
        } else {
            for (Rumah rumah : listRumah) {
                if (rumah.getResidence().toLowerCase().contains(searchText) ||
                        rumah.getType().toLowerCase().contains(searchText)) {
                    filteredList.add(rumah);
                }
            }
            tableView.setItems(filteredList);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colResidence.setCellValueFactory(new PropertyValueFactory<>("residence"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("harga"));
        colBlok.setCellValueFactory(new PropertyValueFactory<>("blok"));
        colPrice.setCellFactory(column -> new TableCell<Rumah, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("Rp %,.0f", item).replace(",", "."));
                }
            }
        });
        colAction.setCellFactory(param -> new TableCell<>() {

            private final Button btnUpdate = new Button("Edit");
            private final Button btnDelete = new Button("Delete");
            private final Button btnDetail = new Button("Detail");

            {
                btnUpdate.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
                btnUpdate.setOnMouseEntered(event -> {
                    btnUpdate.setStyle("-fx-background-color: darkblue; -fx-text-fill: white;");
                });
                btnUpdate.setOnMouseExited(event -> {
                    btnUpdate.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
                });
                btnUpdate.setOnAction(event -> {
                    setPane("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/Master/Rumah/updateRumah.fxml", listRumah.get(getIndex()));
                });

                btnDelete.setStyle("-fx-background-color: red; -fx-text-fill: white;");
                btnDelete.setOnMouseEntered(event -> {
                    btnDelete.setStyle("-fx-background-color: darkred; -fx-text-fill: white;");
                });
                btnDelete.setOnMouseExited(event -> {
                    btnDelete.setStyle("-fx-background-color: red; -fx-text-fill: white;");
                });
                btnDelete.setOnAction(event -> {
                    confirmBox("sp_deleteRumah", listRumah.get(getIndex()).getId(),btnSerach);
                    loadData();
                });

                btnDetail.setOnAction(event -> {
                    setPane( "/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/Master/Rumah/DetailRumah.fxml", listRumah.get(getIndex()));
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttons = new HBox(btnUpdate, btnDelete, btnDetail);
                    buttons.setSpacing(10);
                    buttons.setAlignment(Pos.CENTER);
                    setGraphic(buttons);
                }
            }
        });
    }

    public void loadData() {
        listRumah.clear();
        try {
            Database connect = new Database();
            connect.stat = connect.conn.createStatement();
            String query = "EXEC sp_viewRumah";
            connect.result = connect.stat.executeQuery(query);
            while (connect.result.next()) {
                if (connect.result.getInt("status") == 1) {
                    listRumah.add(new Rumah(connect.result.getString("id_rumah"),
                            connect.result.getString("id_perumahan"),
                            convertToImage(connect.result.getBytes("foto_rumah")),
                            connect.result.getString("blok"),
                            connect.result.getInt("daya_listrik"),
                            connect.result.getString("interior"),
                            connect.result.getInt("jml_kmr_tdr"),
                            connect.result.getInt("jml_kmr_mdn"),
                            connect.result.getString("id_tipe"),
                            connect.result.getString("descrption"),
                            connect.result.getDouble("harga"),
                            connect.result.getInt("thn_bangun"),
                            connect.result.getString("nama_perumahan"),
                            connect.result.getString("nama_tipe"),
                            connect.result.getInt("status"),
                            connect.result.getInt("ketersediaan")));
                }
            }
            connect.stat.close();
            connect.result.close();
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        tableView.setItems(listRumah);
    }

    public void loadPage(ActionEvent event, String page, Rumah data) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/radianite/prg3javafxsistemmarketingperumahan/" + page + ".fxml"));
            Parent root = loader.load();

            updateRumah controller = loader.getController();
            controller.setDataList(data);

            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void onActionAdd(ActionEvent actionEvent) {
        loadPage(actionEvent, "inputRumah");
    }

    private void setPane(String fxml, Rumah data) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent pane = loader.load();
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), GroupMenu);
            GroupMenu.setOpacity(1.0);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setOnFinished(eventFadeOut -> {
                GroupMenu.getChildren().setAll(pane);
                if (fxml.equals("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/Master/Rumah/DetailRumah.fxml")) {
                    DetailRumah controller = loader.getController();
                    controller.setData(data);
                } else if (fxml.equals("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/Master/Rumah/updateRumah.fxml")) {
                    updateRumah controller = loader.getController();
                    controller.setDataList(data);
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
}
