package org.radianite.prg3javafxsistemmarketingperumahan.Controller.TipeRumah;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Methods.Library;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.TipeRumah;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class viewTipe extends Library implements Initializable {
    @FXML
    private TableView tableView;
    @FXML
    private TableColumn<TipeRumah, String> colID, colType;
    @FXML
    private TableColumn<TipeRumah, Void> colAction;

    private ObservableList<TipeRumah> listData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colType.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAction.setCellFactory(param -> new TableCell<>() {
            private final Button btnUpdate = new Button("Update");
            private final Button btnDelete = new Button("Delete");

            {
                btnUpdate.setOnAction(event -> {
                    loadPage(event,"updateTipe",listData.get(getIndex()));
                });

                btnDelete.setOnAction(event -> {
                    deleteData("sp_deleteTipeRumah",listData.get(getIndex()).getId());
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
                    setGraphic(buttons);
                }
            }
        });
    }

    private void loadData() {
        listData.clear();
        try {
            Database connect = new Database();
            connect.stat = connect.conn.createStatement();
            String query = "SELECT * FROM ms_tipe_rumah";
            connect.result = connect.stat.executeQuery(query);
            while (connect.result.next()) {
                if (connect.result.getInt("status") == 1) {
                    listData.add(new TipeRumah(connect.result.getString("id_tipe"), connect.result.getString("nama_tipe")));
                }
            }
            connect.stat.close();
            connect.result.close();
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        tableView.setItems(listData);
    }


    public void onActionAdd(ActionEvent event) {
        loadPage(event, "inputTipe");
    }

    public void loadPage(ActionEvent event, String page, TipeRumah data){
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/radianite/prg3javafxsistemmarketingperumahan/" + page + ".fxml"));
            Parent root = loader.load();

            updateTipe controller = loader.getController();
            controller.setDataList(data);

            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
