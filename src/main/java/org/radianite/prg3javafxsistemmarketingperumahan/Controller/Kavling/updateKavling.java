package org.radianite.prg3javafxsistemmarketingperumahan.Controller.Kavling;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.util.StringConverter;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Methods.Library;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Kavling;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Perumahan;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class updateKavling extends Library implements Initializable {
    @FXML
    private TextField txtId,txtBlok,txtWide,txtDP,txtPrice;
    @FXML
    private ComboBox<Perumahan> cbResidence;
    @FXML
    private Label LabFile;
    @FXML
    private Button btnFile;
    private ObservableList<Perumahan> listPerum = FXCollections.observableArrayList();
    private File file = null;
    private Image image;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadPerum();
        txtId.setDisable(true);
        cbResidence.setCellFactory(param->new javafx.scene.control.ListCell<Perumahan>(){
            protected void updateItem(Perumahan item,boolean empty){
                super.updateItem(item,empty);
                if (item == null || empty){
                    setText(null);
                }else{
                    setText(item.getName());
                }
            }
        });
        cbResidence.setConverter(new StringConverter<Perumahan>() {
            @Override
            public String toString(Perumahan perumahan) {
                return perumahan == null ? null : perumahan.getName();
            }

            @Override
            public Perumahan fromString(String s) {
                return null;
            }
        });
    }

    public void setDataList(Kavling data){
        txtId.setText(data.getId());
        setPSelectedCbBox(data.getIdp());
        txtBlok.setText(data.getBlok());
        txtWide.setText(data.getLuas());
        txtDP.setText(data.getDp().toString());
        txtPrice.setText(data.getHarga().toString());
        image = data.getFoto();
    }

    private void setPSelectedCbBox(String data){
        for (Perumahan item : cbResidence.getItems()){
            if (item.getId().equals(data)){
                cbResidence.getSelectionModel().select(item);
            }
        }
    }

    public void loadPerum(){
        listPerum.clear();
        try{
            Database connect = new Database();
            connect.stat = connect.conn.createStatement();
            String query = "SELECT  * FROM ms_perumahan";
            connect.result = connect.stat.executeQuery(query);
            while (connect.result.next()){
                if (connect.result.getInt("status")==1){
                    listPerum.add(new Perumahan(connect.result.getString("id_perumahan"),connect.result.getString("nama_perumahan")));
                }
            }
        }catch (SQLException ex){
            System.out.println("Error: "+ex.getMessage());
        }
        cbResidence.setItems(listPerum);
    }

    public void onActionFile(ActionEvent actionEvent) {
        file = imageChooser(btnFile);
        LabFile.setText(file.getName());
    }

    public void onActionUpdate(ActionEvent actionEvent) throws IOException {
        if (isEmpty())
        {
            fillBox();
            return;
        }
        try {
            Database connect = new Database();
            String query = "EXEC sp_updateKavling ?,?,?,?,?,?,?";
            connect.pstat = connect.conn.prepareStatement(query);
            connect.pstat.setString(1,txtId.getText());
            connect.pstat.setString(2,cbResidence.getSelectionModel().getSelectedItem().getId());
            connect.pstat.setBytes(3,file != null ? imageToByte(file) : convertImageToBytes(image));
            connect.pstat.setString(4,txtBlok.getText());
            connect.pstat.setString(5,txtWide.getText());
            connect.pstat.setDouble(6,Double.parseDouble(txtDP.getText()));
            connect.pstat.setDouble(7,Double.parseDouble(txtPrice.getText()));
            connect.pstat.executeUpdate();
            connect.pstat.close();
            successBox();
            loadPage(actionEvent,"viewKavling");
        }catch (SQLException ex){
            System.out.println("Error: "+ex.getMessage());
        }
    }

    public boolean isEmpty(){
        if (txtBlok.getText().isEmpty() || txtWide.getText().isEmpty() || txtDP.getText().isEmpty() || txtPrice.getText().isEmpty()){
            return true;
        }
        return false;
    }
}
