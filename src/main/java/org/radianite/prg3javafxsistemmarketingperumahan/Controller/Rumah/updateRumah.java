package org.radianite.prg3javafxsistemmarketingperumahan.Controller.Rumah;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.radianite.prg3javafxsistemmarketingperumahan.Methods.Library;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Rumah;

import java.net.URL;
import java.util.ResourceBundle;

public class updateRumah extends Library implements Initializable {
    @FXML
    private TableView<Rumah> tableView;
    @FXML
    private TableColumn<Rumah,String> colId,colResidence,colType;
    @FXML
    private TableColumn<Rumah,Double> colPrice;
    @FXML
    private TableColumn<Rumah,Void> colAction;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onActionAdd(ActionEvent actionEvent) {
    }
}
