package org.radianite.prg3javafxsistemmarketingperumahan.Controller.Perumahan;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Controller.User.updateUser;
import org.radianite.prg3javafxsistemmarketingperumahan.Methods.Library;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Bank;
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
    private TableColumn<Perumahan, String> colStatus; // Changed to String type
    @FXML
    private TableColumn<Perumahan, Void> colAction;
    @FXML
    private Button btnSerach;
    @FXML
    private TextField txtSearch;
    private ObservableList<Perumahan> listResidence = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDeveloper.setCellValueFactory(new PropertyValueFactory<>("namadev"));
        colResidence.setCellValueFactory(new PropertyValueFactory<>("name"));
        colStatus.setCellValueFactory(cellData -> {
            int status = cellData.getValue().getStatus();
            return new SimpleStringProperty(status == 1 ? "Available" : "Not Available"); // Changed to display "Available" or "Not Available"
        });

        colStatus.setCellFactory(column -> new TableCell<Perumahan, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    setStyle(item.equals("Available") ? "-fx-text-fill: green; -fx-font-weight: bold;" : "-fx-text-fill: red; -fx-font-weight: bold;");
                }
            }
        });
        colAction.setCellFactory(param->new TableCell<>(){
            private final Button btnUpdate = new Button("Update");
            private final Button btnDelete = new Button("Delete");

            {
                btnUpdate.setOnAction(event -> {
                    loadPage(event,"updatePerumahan",listResidence.get(getIndex()));
                });

                btnDelete.setOnAction(event -> {
                    Perumahan perumahan = getTableView().getItems().get(getIndex());
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Delete Confirmation");
                    alert.setHeaderText("Are you sure you want to delete this data?");
                    alert.setContentText("Deleted data cannot be recovered.");

                    ButtonType buttonTypeYes = new ButtonType("Yes");
                    ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                    alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

                    // Show alert as a pop-up above the main form
                    Stage stage = (Stage) getTableView().getScene().getWindow();
                    alert.initOwner(stage);

                    alert.showAndWait().ifPresent(type -> {
                        if (type == buttonTypeYes) {
                            deleteData("sp_deletePerumahan",listResidence.get(getIndex()).getId());
                            loadData();
                        }
                    });
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttons = new HBox(btnUpdate, btnDelete);
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
                listResidence.add(new Perumahan(connect.result.getString("id_perumahan"),
                        connect.result.getString("id_developer"),
                        connect.result.getString("nama_perumahan"),
                        connect.result.getString("nama_developer"),
                        connect.result.getInt("status"))); // Added status
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
