package org.radianite.prg3javafxsistemmarketingperumahan.Controller.User;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.radianite.prg3javafxsistemmarketingperumahan.App.Admin.DashboardManageUserController;
import org.radianite.prg3javafxsistemmarketingperumahan.App.Admin.MainDashboardController;
import org.radianite.prg3javafxsistemmarketingperumahan.App.Agent.TransaksiRukoController;
import org.radianite.prg3javafxsistemmarketingperumahan.App.Agent.TransaksiRumahController;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Controller.Developer.Update;
import org.radianite.prg3javafxsistemmarketingperumahan.Methods.Library;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Developer;
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
    @FXML
    private AnchorPane GroupMenu;
    private ObservableList<User> listUser = FXCollections.observableArrayList();

    @FXML
    private Button btnSerach;
    @FXML
    private TextField txtSearch;
    @FXML
    private void btnAdd() {
        setPanes("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/Master/User/inputUser.fxml", null);

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
    private void handleSearch(ActionEvent event) {
        String searchText = txtSearch.getText().toLowerCase();
        ObservableList<User> filteredList = FXCollections.observableArrayList();

        if (!searchText.isEmpty()) {
            for (User user : listUser) {
                if (user.getName().toLowerCase().contains(searchText) ||
                        user.getEmail().toLowerCase().contains(searchText) ||
                        user.getUsn().toLowerCase().contains(searchText) ||
                        user.getRName().toLowerCase().contains(searchText) ||
                        user.getPName().toLowerCase().contains(searchText)
                ) {
                    filteredList.add(user);
                }
            }
            tableView.setItems(filteredList);
        } else {
            settable();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        settable();
    }

    public void settable() {
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
                    setPane("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/Master/User/updateUser.fxml", listUser.get(getIndex()));
                });

                btnDelete.setOnAction(event -> {
                    confirmBox("sp_deleteUser", listUser.get(getIndex()).getUsn(), btnDelete);
                    loadData();
                });
                btnDelete.setStyle("-fx-background-color: red; -fx-text-fill: white;");
                btnDelete.setOnMouseEntered(event -> {
                    btnDelete.setStyle("-fx-background-color: darkred; -fx-text-fill: white;");
                });
                btnDelete.setOnMouseExited(event -> {
                    btnDelete.setStyle("-fx-background-color: red; -fx-text-fill: white;");
                });

                btnDetail.setOnAction(event -> {
                    setPane("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/Master/User/DetailUser.fxml",listUser.get(getIndex()));
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
                    setGraphic(buttons);
                }
            }
        });
    }

    public void loadData() {
        listUser.clear();
        try {
            Database connect = new Database();
            connect.stat = connect.conn.createStatement();
            String query = "EXEC sp_viewUser";
            connect.result = connect.stat.executeQuery(query);
            while (connect.result.next()) {
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
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        tableView.setItems(listUser);
    }

    public void onActionAdd(ActionEvent actionEvent) {
        loadPage(actionEvent, "inputUser");
    }

    public void loadPage(ActionEvent event, String page, User data) {
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

    private void setPane(String fxml,User data) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent pane = loader.load();
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), GroupMenu);
            GroupMenu.setOpacity(1.0);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setOnFinished(eventFadeOut -> {
                GroupMenu.getChildren().setAll(pane);
                if (fxml.equals("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/Master/User/DetailUser.fxml")) {
                  /*  MainDashboardController controller = loader.getController();*/
                    DetailUser controller = loader.getController();
                    controller.setData(data);
                    /*    controller.setDataList(userList.get(0));*/
                } else if (fxml.equals("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/Master/User/updateUser.fxml")) {
                    updateUser controller = loader.getController();
                    controller.setDataList(data);
                }

                FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), GroupMenu);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                ParallelTransition parallelTransition = new ParallelTransition( fadeIn);
                parallelTransition.play();
            });
            fadeOut.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
