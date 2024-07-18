package org.radianite.prg3javafxsistemmarketingperumahan.Controller.Perumahan;

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
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Controller.Developer.Update;
import org.radianite.prg3javafxsistemmarketingperumahan.Controller.User.updateUser;
import org.radianite.prg3javafxsistemmarketingperumahan.Methods.Library;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Bank;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Developer;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Perumahan;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class viewPerumahan extends Library implements Initializable {
    @FXML
    private TableView tableView;
    @FXML
    private TableColumn<Perumahan, String> colId, colDeveloper, colResidence;
    @FXML
    private TableColumn<Perumahan, Void> colAction;
    @FXML
    private Button btnSerach;
    @FXML
    private TextField txtSearch;
    private ObservableList<Perumahan> listResidence = FXCollections.observableArrayList();
    @FXML
    private AnchorPane GroupMenu;
    @FXML
    private void btnAdd() {
        setPane("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/Master/Perumahan/Add.fxml", null);

    }

    private void setPane(String fxml, Perumahan data) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent pane = loader.load();
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), GroupMenu);
            GroupMenu.setOpacity(1.0);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setOnFinished(eventFadeOut -> {
                GroupMenu.getChildren().setAll(pane);
                if (fxml.equals("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/Master/Perumahan/updatePerumahan.fxml")) {
                    updatePerumahan controller = loader.getController();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDeveloper.setCellValueFactory(new PropertyValueFactory<>("namadev"));
        colResidence.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAction.setCellFactory(param->new TableCell<>(){
            private final Button btnUpdate = new Button("Edit");
            private final Button btnDelete = new Button("Delete");

            {
                btnUpdate.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
                btnUpdate.setOnMouseEntered(event -> {
                    btnUpdate.setStyle("-fx-background-color: darkblue; -fx-text-fill: white;");
                });
                btnUpdate.setOnMouseExited(event -> {
                    btnUpdate.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
                });
                btnUpdate.setOnAction(event -> {
                    setPane("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/Master/Perumahan/updatePerumahan.fxml",listResidence.get(getIndex()));
                });
                btnDelete.setStyle("-fx-background-color: red; -fx-text-fill: white;");
                btnDelete.setOnMouseEntered(event -> {
                    btnDelete.setStyle("-fx-background-color: darkred; -fx-text-fill: white;");
                });
                btnDelete.setOnMouseExited(event -> {
                    btnDelete.setStyle("-fx-background-color: red; -fx-text-fill: white;");
                });
                btnDelete.setOnAction(event -> {
                    confirmBox("sp_deletePerumahan", listResidence.get(getIndex()).getId(), btnDelete);
                    loadData();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttons = new HBox(btnUpdate, btnDelete);
                    buttons.setAlignment(Pos.CENTER);
                    buttons.setSpacing(5);
                    setGraphic(buttons);
                }
            }
        });
    }

    @FXML
    private void handleSearch() {
        String searchText = txtSearch.getText().toLowerCase();
        ObservableList<Perumahan> filteredList = FXCollections.observableArrayList();

        if (searchText.isEmpty()) {
            loadData();
        } else {
            for (Perumahan perumahan : listResidence) {
                if (perumahan.getName().toLowerCase().contains(searchText) ||
                    perumahan.getNamadev().toLowerCase().contains(searchText)) {
                    filteredList.add(perumahan);
                }
            }
            tableView.setItems(filteredList);
        }
    }

    public void loadData(){
        listResidence.clear();
        try{
            Database connect = new Database();
            connect.stat = connect.conn.createStatement();
            String query = "EXEC sp_viewPerumahan";
            connect.result = connect.stat.executeQuery(query);
            while (connect.result.next()){
                if (connect.result.getInt("status") == 1) {
                    listResidence.add(new Perumahan(connect.result.getString("id_perumahan"),
                            connect.result.getString("id_developer"),
                            connect.result.getString("nama_perumahan"),
                            connect.result.getString("nama_developer"),
                            connect.result.getInt("status"))); // Added status
                }
            }
            connect.stat.close();
            connect.result.close();
        }catch (SQLException ex){
            System.out.println("Error: "+ex.getMessage());
        }
        tableView.setItems(listResidence);
    }

    public void loadPage(ActionEvent event, String page, Perumahan data){
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/radianite/prg3javafxsistemmarketingperumahan/" + page + ".fxml"));
            Parent root = loader.load();

            updatePerumahan controller = loader.getController();
            controller.setDataList(data);

            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void onClickAdd(ActionEvent actionEvent) {
        loadPage(actionEvent,"inputPerumahan");
    }
}
