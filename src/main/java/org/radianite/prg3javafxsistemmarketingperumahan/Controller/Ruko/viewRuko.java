package org.radianite.prg3javafxsistemmarketingperumahan.Controller.Ruko;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Methods.Library;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Role;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Ruko;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class viewRuko extends Library implements Initializable {
    @FXML
    private TableView tableView;
    @FXML
    private TableColumn<Ruko,String> colId,colResidence,colBlok;
    @FXML
    private TableColumn<Ruko, Image> colPhoto;
    @FXML
    private TableColumn<Ruko, Double> colRent;
    @FXML
    private TableColumn<Ruko, String> colStatus; // Changed to String
    @FXML
    private TableColumn<Ruko, Void> colAction;
    private ObservableList<Ruko> listRuko = FXCollections.observableArrayList();

    @FXML
    private Button btnSerach;
    @FXML
    private TextField txtSearch;

    @FXML
    private void handleSearch() {
        String searchText = txtSearch.getText().toLowerCase();
        ObservableList<Ruko> filteredList = FXCollections.observableArrayList();

        if (searchText.isEmpty()) {
            loadData();
        } else {
            for (Ruko ruko : listRuko) {
                if (ruko.getBlok().toLowerCase().contains(searchText) ||
                    ruko.getNamaperum().toLowerCase().contains(searchText)) {
                    filteredList.add(ruko);
                }
            }
            tableView.setItems(filteredList);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colResidence.setCellValueFactory(new PropertyValueFactory<>("namaperum"));
        colPhoto.setCellValueFactory(new PropertyValueFactory<>("foto"));
        colPhoto.setCellFactory(column -> new TableCell<Ruko, Image>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(Image image, boolean empty) {
                super.updateItem(image, empty);
                if (empty || image == null) {
                    setGraphic(null);
                } else {
                    imageView.setImage(image);
                    imageView.setFitWidth(100);
                    imageView.setFitHeight(100);
                    setGraphic(imageView);
                }
            }
        });
        colBlok.setCellValueFactory(new PropertyValueFactory<>("blok"));
        colRent.setCellValueFactory(new PropertyValueFactory<>("rent"));
        colRent.setCellFactory(column -> new TableCell<Ruko, Double>() {
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
        colStatus.setCellValueFactory(cellData -> {
            int status = cellData.getValue().getStatus();
            return new SimpleStringProperty(status == 1 ? "Available" : "Not Available"); // Changed to display "Available" or "Not Available"
        });

        colStatus.setCellFactory(column -> new TableCell<Ruko, String>() {
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
                    loadPage(event,"updateRuko",listRuko.get(getIndex()));
                });

                btnDelete.setOnAction(event -> {
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
                            deleteData("sp_deleteRuko",listRuko.get(getIndex()).getId());
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

    public void loadData(){
        listRuko.clear();
        try{
            Database connect = new Database();
            connect.stat = connect.conn.createStatement();
            String query = "EXEC sp_viewRuko";
            connect.result = connect.stat.executeQuery(query);
            while (connect.result.next()){
                listRuko.add(new Ruko(connect.result.getString("id_ruko"),
                        connect.result.getString("id_perumahan"),
                        convertToImage(connect.result.getBytes("foto_ruko")),
                        connect.result.getString("blok"),
                        connect.result.getInt("daya_listrik"),
                        connect.result.getInt("jml_kmr_mdn"),
                        connect.result.getString("descrption"),
                        connect.result.getDouble("harga_sewa"),
                        connect.result.getString("nama_perumahan"),
                        connect.result.getInt("status"))); // Added status
            }
            connect.stat.close();
            connect.result.close();
        }catch (SQLException ex){
            System.out.println("Error: "+ex.getMessage());
        }
        tableView.setItems(listRuko);
    }

    public void onActionAdd(ActionEvent actionEvent) {
        loadPage(actionEvent,"inputRuko");
    }

    public void loadPage(ActionEvent event, String page, Ruko data){
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/radianite/prg3javafxsistemmarketingperumahan/" + page + ".fxml"));
            Parent root = loader.load();

            updateRuko controller = loader.getController();
            controller.setDataList(data);

            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
