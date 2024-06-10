package org.radianite.prg3javafxsistemmarketingperumahan.Controller.Kavling;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Methods.Library;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Kavling;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class viewKavling extends Library implements Initializable {
    @FXML
    private TableView tableView;
    @FXML
    private TableColumn<Kavling,String> colId,colResidence,colBlok;
    @FXML
    private TableColumn<Kavling,Image> colImage;
    @FXML
    private TableColumn<Kavling,Double>colPrice;
    @FXML
    private TableColumn<Kavling,Void> colAction;

    private ObservableList<Kavling> listKavling = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colResidence.setCellValueFactory(new PropertyValueFactory<>("namaperum"));
        colImage.setCellValueFactory(new PropertyValueFactory<>("foto"));
        colImage.setCellFactory(column -> new TableCell<Kavling, Image>() {
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
        colPrice.setCellValueFactory(new PropertyValueFactory<>("harga"));
        colAction.setCellFactory(param->new TableCell<>(){
            private final Button btnUpdate = new Button("Update");
            private final Button btnDelete = new Button("Delete");

            {
                btnUpdate.setOnAction(event -> {
                    loadPage(event,"updateKavling",listKavling.get(getIndex()));
                });

                btnDelete.setOnAction(event -> {
                    deleteData("sp_deleteKavling",listKavling.get(getIndex()).getId());
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
        listKavling.clear();
        try{
            Database connect = new Database();
            connect.stat = connect.conn.createStatement();
            String query = "EXEC sp_viewKavling";
            connect.result = connect.stat.executeQuery(query);
            while (connect.result.next()){
                if (connect.result.getInt("status")==1){
                    listKavling.add(new Kavling(connect.result.getString("id_kavling"),
                            connect.result.getString("id_perumahan"),
                            convertToImage(connect.result.getBytes("foto_sekitar")),
                            connect.result.getString("blok"),
                            connect.result.getString("luas_tanah"),
                            connect.result.getDouble("uang_muka"),
                            connect.result.getDouble("harga"),
                            connect.result.getString("nama_perumahan")));
                }
            }
            connect.stat.close();
            connect.result.close();
        }catch (SQLException ex){
            System.out.println("Error: "+ex.getMessage());
        }
        tableView.setItems(listKavling);
    }

    public void onClickAdd(ActionEvent actionEvent) {
        loadPage(actionEvent, "inputKavling");
    }

    public void loadPage(ActionEvent event, String page, Kavling data){
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/radianite/prg3javafxsistemmarketingperumahan/" + page + ".fxml"));
            Parent root = loader.load();

            updateKavling controller = loader.getController();
            controller.setDataList(data);

            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
