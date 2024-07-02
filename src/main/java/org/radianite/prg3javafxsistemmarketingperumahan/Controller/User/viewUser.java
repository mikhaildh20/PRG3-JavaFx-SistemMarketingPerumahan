package org.radianite.prg3javafxsistemmarketingperumahan.Controller.User;

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
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Methods.Library;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class viewUser extends Library implements Initializable {
    @FXML
    private TableView<User> tableView;
    @FXML
    private TableColumn<User, String> colUsn, colResidence, colRole, colName, colEmail;
    @FXML
    private TableColumn<User, String> colStatus; // Ubah tipe data kolom status menjadi String
    @FXML
    private TableColumn<User, Void> colAction;
    private ObservableList<User> listUser = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
        colUsn.setCellValueFactory(new PropertyValueFactory<>("usn"));
        colResidence.setCellValueFactory(new PropertyValueFactory<>("pName"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("rName"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colStatus.setCellValueFactory(cellData -> {
            int status = cellData.getValue().getStatus();
            return new SimpleStringProperty(status == 1 ? "Available" : "Not Available"); // Ubah status menjadi teks
        });

        colStatus.setCellFactory(column -> new TableCell<User, String>() {
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
                    loadPage(event,"updateUser",listUser.get(getIndex()));
                });

                btnDelete.setOnAction(event -> {
                    deleteData("sp_deleteUser",listUser.get(getIndex()).getUsn());
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

    public void loadData(){
        listUser.clear();
        try{
            Database connect = new Database();
            connect.stat = connect.conn.createStatement();
            String query = "EXEC sp_viewUser";
            connect.result = connect.stat.executeQuery(query);
            while(connect.result.next()){
                listUser.add(new User(connect.result.getString("username"),
                        connect.result.getString("password"),
                        connect.result.getString("id_perumahan"),
                        connect.result.getString("id_role"),
                        connect.result.getString("nama_lengkap"),
                        connect.result.getString("email"),
                        connect.result.getString("alamat"),
                        connect.result.getString("jenis_kelamin"),
                        connect.result.getInt("umur"),
                        convertToImage(connect.result.getBytes("photo")),
                        connect.result.getString("nama_perumahan"),
                        connect.result.getString("nama_role"),
                        connect.result.getInt("status") // Tambahkan status
                ));
            }
            connect.result.close();
            connect.stat.close();
        }catch (SQLException ex){
            System.out.println("Error: "+ex.getMessage());
        }
        tableView.setItems(listUser);
    }

    public void onActionAdd(ActionEvent actionEvent) {
        loadPage(actionEvent,"inputUser");
    }

    public void loadPage(ActionEvent event, String page, User data){
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/radianite/prg3javafxsistemmarketingperumahan/" + page + ".fxml"));
            Parent root = loader.load();

            updateUser controller = loader.getController();
            controller.setDataList(data);

            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
