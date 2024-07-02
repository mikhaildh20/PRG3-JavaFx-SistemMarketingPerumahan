package org.radianite.prg3javafxsistemmarketingperumahan.Controller.Ruko;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.util.StringConverter;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Methods.Library;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Perumahan;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Ruko;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class updateRuko extends Library implements Initializable {
    @FXML
    private TextField txtId,txtBlok,txtElec,txtToilet,txtRent;
    @FXML
    private TextArea txtDesc;
    @FXML
    private ComboBox<Perumahan> cbResidence;
    @FXML
    private Label LabFile;
    @FXML
    private Button btnFile;
    private File file = null;
    private Image image;
    private ObservableList<Perumahan> listPerum = FXCollections.observableArrayList();
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

    public void setDataList(Ruko data){
        txtId.setText(data.getId());
        setPSelectedCbBox(data.getIdp());
        txtBlok.setText(data.getBlok());
        txtElec.setText(data.getElectric().toString());
        txtToilet.setText(data.getToilet().toString());
        txtRent.setText(data.getRent().toString());
        txtDesc.setText(data.getDesc());
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

    public void onActionUpdate(ActionEvent actionEvent) {
        if (isEmpty())
        {
            fillBox();
            return;
        }
        try{
            Database connect = new Database();
            String query = "EXEC sp_updateRuko ?,?,?,?,?,?,?,?";
            connect.pstat = connect.conn.prepareStatement(query);
            connect.pstat.setString(1,txtId.getText());
            connect.pstat.setString(2,cbResidence.getSelectionModel().getSelectedItem().getId());
            connect.pstat.setBytes(3,file != null ? imageToByte(file) : convertImageToBytes(image));
            connect.pstat.setString(4,txtBlok.getText());
            connect.pstat.setInt(5,Integer.parseInt(txtElec.getText()));
            connect.pstat.setInt(6,Integer.parseInt(txtToilet.getText()));
            connect.pstat.setString(7,txtDesc.getText());
            connect.pstat.setDouble(8,Double.parseDouble(txtRent.getText()));
            connect.pstat.executeUpdate();
            connect.pstat.close();
            successBox();
            loadPage(actionEvent,"viewRuko");
        }catch (SQLException | IOException ex){
            System.out.println("Error: "+ex.getMessage());
        }
    }
    public boolean isEmpty(){
        if (txtBlok.getText().isEmpty() || txtElec.getText().isEmpty() || txtToilet.getText().isEmpty() || txtDesc.getText().isEmpty() || txtRent.getText().isEmpty()){
            return true;
        }
        return false;
    }
}
