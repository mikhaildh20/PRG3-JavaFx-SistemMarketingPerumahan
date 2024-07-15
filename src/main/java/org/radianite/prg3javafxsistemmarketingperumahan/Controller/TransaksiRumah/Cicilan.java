package org.radianite.prg3javafxsistemmarketingperumahan.Controller.TransaksiRumah;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
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
    private TableColumn<org.radianite.prg3javafxsistemmarketingperumahan.Models.Cicilan, String> colNama;

    @FXML
    private TableColumn<org.radianite.prg3javafxsistemmarketingperumahan.Models.Cicilan, String> colId;

    @FXML
    private TableColumn<org.radianite.prg3javafxsistemmarketingperumahan.Models.Cicilan, Double> colTunggakan;

    @FXML
    private TableColumn<org.radianite.prg3javafxsistemmarketingperumahan.Models.Cicilan, Integer> colTelat;

    @FXML
    private TableColumn<org.radianite.prg3javafxsistemmarketingperumahan.Models.Cicilan, Double> colDenda;

    @FXML
    private TableView<org.radianite.prg3javafxsistemmarketingperumahan.Models.Cicilan> tableView;
    private ObservableList<org.radianite.prg3javafxsistemmarketingperumahan.Models.Cicilan> listCicilan = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCicilan();
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colBlok.setCellValueFactory(new PropertyValueFactory<>("blok"));
        colNama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        colTelat.setCellValueFactory(new PropertyValueFactory<>("telat"));
        colDenda.setCellValueFactory(new PropertyValueFactory<>("denda"));
        colTunggakan.setCellValueFactory(new PropertyValueFactory<>("tunggakan"));

        colAction.setCellFactory(param->new TableCell<>(){
            private final Button btnDelete = new Button("Confirm");

            {
                btnDelete.setOnAction(event -> {
                    storedCicilan(listCicilan.get(getIndex()).getId());
                    loadCicilan();
                    tableView.refresh();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttons = new HBox(btnDelete);
                    setGraphic(buttons);
                }
            }
        });
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
                        connect.result.getString("INVOICE"),
                        connect.result.getString("BLOK"),
                        connect.result.getString("NAMA"),
                        connect.result.getInt("TELAT"),
                        connect.result.getDouble("DENDA"),
                        connect.result.getDouble("TUNGGAKAN")
                ));
            }
        }catch (SQLException ex)
        {
            System.out.println("Error: "+ex.getMessage());
        }
        tableView.setItems(listCicilan);
    }

    public void storedCicilan(String idx){
        try{
            Database connect = new Database();
            String query  = "EXEC sp_cicilanRumah ?";
            connect.pstat = connect.conn.prepareStatement(query);
            connect.pstat.setString(1,idx);
            connect.pstat.executeUpdate();
            connect.pstat.close();
        }catch (SQLException ex){
            System.out.println("Error: "+ex.getMessage());
        }
    }
}