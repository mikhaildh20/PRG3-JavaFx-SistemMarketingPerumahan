package org.radianite.prg3javafxsistemmarketingperumahan.Controller.Rumah;

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
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Rumah;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class viewRumah extends Library implements Initializable {
    @FXML
    private TableView<Rumah> tableView;
    @FXML
    private TableColumn<Rumah,String> colId,colResidence,colType;
    @FXML
    private TableColumn<Rumah,Double> colPrice;
    @FXML
    private TableColumn<Rumah,Void> colAction;
    private ObservableList<Rumah> listRumah = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colResidence.setCellValueFactory(new PropertyValueFactory<>("residence"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("harga"));
        colAction.setCellFactory(param->new TableCell<>(){
            private final Button btnUpdate = new Button("Update");
            private final Button btnDelete = new Button("Delete");

            {
                btnUpdate.setOnAction(event -> {
                    loadPage(event,"updateRumah",listRumah.get(getIndex()));
                });

                btnDelete.setOnAction(event -> {
                    deleteData("sp_deleteRumah",listRumah.get(getIndex()).getId());
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
        listRumah.clear();
        try{
            Database connect = new Database();
            connect.stat = connect.conn.createStatement();
            String query = "EXEC sp_viewRumah";
            connect.result = connect.stat.executeQuery(query);
            while (connect.result.next()){
                if (connect.result.getInt("status") == 1){
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
                            connect.result.getString("nama_tipe")));
                }
            }
            connect.stat.close();
            connect.result.close();
        }catch (SQLException ex){
            System.out.println("Error: "+ex.getMessage());
        }
        tableView.setItems(listRumah);
    }

    public void loadPage(ActionEvent event, String page, Rumah data){
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
        loadPage(actionEvent,"inputRumah");
    }
}
