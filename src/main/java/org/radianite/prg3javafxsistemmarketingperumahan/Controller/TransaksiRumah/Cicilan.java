package org.radianite.prg3javafxsistemmarketingperumahan.Controller.TransaksiRumah;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Cicilan implements Initializable {
    @FXML
    private TableColumn<org.radianite.prg3javafxsistemmarketingperumahan.Models.Cicilan, Void> colAction;

    @FXML
    private TableColumn<org.radianite.prg3javafxsistemmarketingperumahan.Models.Cicilan, String> colBlok;

    @FXML
    private TableColumn<org.radianite.prg3javafxsistemmarketingperumahan.Models.Cicilan, String> colCicilan;

    @FXML
    private TableColumn<org.radianite.prg3javafxsistemmarketingperumahan.Models.Cicilan, String> colId;

    @FXML
    private TableColumn<org.radianite.prg3javafxsistemmarketingperumahan.Models.Cicilan, Double> colTunggakan;

    @FXML
    private TableView<org.radianite.prg3javafxsistemmarketingperumahan.Models.Cicilan> tableView;
    private ObservableList<org.radianite.prg3javafxsistemmarketingperumahan.Models.Cicilan> listCicilan = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCicilan();
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colBlok.setCellValueFactory(new PropertyValueFactory<>("blok"));
        colCicilan.setCellValueFactory(new PropertyValueFactory<>("tgl"));
        colTunggakan.setCellValueFactory(new PropertyValueFactory<>("tunggakan"));
    }

    public void loadCicilan()
    {
        try{
            Database connect = new Database();
            String query = "EXEC sp_viewCicilan";
            connect.stat = connect.conn.createStatement();
            connect.result = connect.stat.executeQuery(query);
            while (connect.result.next()) {
                listCicilan.add(new org.radianite.prg3javafxsistemmarketingperumahan.Models.Cicilan(
                        connect.result.getString("id_trRumah"),
                        connect.result.getString("blok"),
                        connect.result.getString("tgl_cicilan"),
                        connect.result.getDouble("min_cicilan")
                ));
            }
        }catch (SQLException ex)
        {
            System.out.println("Error: "+ex.getMessage());
        }
        tableView.setItems(listCicilan);
    }

    public void refreshCicilan()
    {
        
    }
}
