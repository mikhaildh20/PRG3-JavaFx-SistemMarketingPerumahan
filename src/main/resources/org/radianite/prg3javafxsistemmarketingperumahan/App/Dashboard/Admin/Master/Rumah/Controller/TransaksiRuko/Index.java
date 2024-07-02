package org.radianite.prg3javafxsistemmarketingperumahan.Controller.TransaksiRuko;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Methods.Library;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.SewaRuko;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Index extends Library implements Initializable {
    @FXML
    private TableView<SewaRuko> tableView;
    @FXML
    private TableColumn<SewaRuko,String> colRent,colResidence,colBlok;
    @FXML
    private TableColumn<SewaRuko,Integer> colContract;
    private ObservableList<SewaRuko> listSewa = FXCollections.observableArrayList();
    private ObservableList<String> listStatus = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();

        colRent.setCellValueFactory(new PropertyValueFactory<>("id"));
        colResidence.setCellValueFactory(new PropertyValueFactory<>("nama"));
        colBlok.setCellValueFactory(new PropertyValueFactory<>("blok"));
        colContract.setCellValueFactory(new PropertyValueFactory<>("status"));

        colContract.setCellFactory(column -> {
            return new TableCell<SewaRuko, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                    } else {
                        switch (item) {
                            case 0:
                                setText("Expired");
                                break;
                            case 1:
                                setText("Active");
                                break;
                        }
                    }
                }
            };
        });
    }

    public void loadData(){
        try{
            Database connect = new Database();
            connect.stat = connect.conn.createStatement();
            String query = "EXEC sp_viewTrRuko";
            connect.result = connect.stat.executeQuery(query);
            while (connect.result.next())
            {
                listSewa.add(new SewaRuko(connect.result.getString("id_trRuko"),
                        connect.result.getString("nama_perumahan"),
                        connect.result.getString("blok"),
                        connect.result.getInt("status_kontrak")));
            }
        }catch (SQLException ex)
        {
            System.out.println("Error: "+ex.getMessage());
        }
        tableView.setItems(listSewa);
    }
}
