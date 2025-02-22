package org.radianite.prg3javafxsistemmarketingperumahan.Controller.Bank;

import javafx.beans.property.SimpleObjectProperty;
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
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Developer;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Rumah;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class viewBank extends Library implements Initializable {
    @FXML
    private TableView<Bank> tableView;
    @FXML
    private TableColumn<Bank, String> colId, colBank;
    @FXML
    private TableColumn<Bank, String> colBunga; // Changed data type to String
    @FXML
    private TableColumn<Bank, String> colStatus; // Changed data type to String
    @FXML
    private TableColumn<Bank, Void> colAction;
    @FXML private Button btnSerach;
    @FXML
    private TextField txtSearch;

    private ObservableList<Bank> listBank = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colBank.setCellValueFactory(new PropertyValueFactory<>("name"));
        colBunga.setCellValueFactory(cellData -> {
            int bunga = cellData.getValue().getBunga();
            return new SimpleStringProperty(bunga + "%"); // Menambahkan % di belakang nilai bunga
        });
        colStatus.setCellValueFactory(cellData -> {
            int status = cellData.getValue().getStatus();
            return new SimpleStringProperty(status == 1 ? "Available" : "Not Available"); // Changed status to text
        });
        colStatus.setCellFactory(column -> new TableCell<Bank, String>() {
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
                    loadPage(event,"updateBank",listBank.get(getIndex()));
                });
                btnDelete.setOnAction(event -> {
                    Bank Bank = getTableView().getItems().get(getIndex());
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
                            deleteData("sp_deleteBank",listBank.get(getIndex()).getId());
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
    private void handleSearch(ActionEvent event) {
        String searchText = txtSearch.getText().toLowerCase();
        ObservableList<Bank> filteredList = FXCollections.observableArrayList();

        for (Bank bank : listBank) {
            if (bank.getName().toLowerCase().contains(searchText) || 
                bank.getId().toLowerCase().contains(searchText) ||
                String.valueOf(bank.getBunga()).contains(searchText)) {
                filteredList.add(bank);
            }
        }

        tableView.setItems(filteredList);
    }

    public void loadData()
    {
        listBank.clear();
        try{
            Database connect = new Database();
            connect.stat = connect.conn.createStatement();
            String query = "SELECT * FROM ms_bank";
            connect.result = connect.stat.executeQuery(query);
            while (connect.result.next())
            {
                listBank.add(new Bank(
                        connect.result.getString("id_bank"),
                        connect.result.getString("nama_bank"),
                        connect.result.getInt("suku_bunga"),
                        connect.result.getInt("status") // Added status
                ));
            }
            connect.result.close();
            connect.stat.close();
        }catch (SQLException ex)
        {
            System.out.println("Error: "+ex.getMessage());
        }
        tableView.setItems(listBank);
    }

    public void loadPage(ActionEvent event, String page, Bank data){
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/radianite/prg3javafxsistemmarketingperumahan/" + page + ".fxml"));
            Parent root = loader.load();

            updateBank controller = loader.getController();
            controller.setDataList(data);

            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void onActionAdd(ActionEvent actionEvent) {
        loadPage(actionEvent,"inputBank");
    }
}
