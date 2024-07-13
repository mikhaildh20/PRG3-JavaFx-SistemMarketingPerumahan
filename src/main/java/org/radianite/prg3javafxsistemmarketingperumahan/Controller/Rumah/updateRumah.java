package org.radianite.prg3javafxsistemmarketingperumahan.Controller.Rumah;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import javafx.util.StringConverter;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Methods.Library;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.HouseType;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Perumahan;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Rumah;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class updateRumah extends Library implements Initializable {
    @FXML
    private TextField txtId,txtBlok,txtWatt,txtBed,txtRest,txtBuild,txtPrice;
    @FXML
    private TextArea txtDesc;
    @FXML
    private AnchorPane AncoreMaster;
    @FXML
    private ComboBox<Perumahan> cbResidence;
    @FXML
    private ComboBox<HouseType> cbType;
    @FXML
    private ComboBox<String> cbInterior;
    @FXML
    private Button btnFile;
    @FXML
    private Label LabFile;
    private File file = null;
    private Image image;
    private ObservableList<Perumahan> listPerum = FXCollections.observableArrayList();
    private ObservableList<HouseType> listTipe = FXCollections.observableArrayList();
    private ObservableList<String> listInterior = FXCollections.observableArrayList("Furnished","Unfurnished");
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadPerum();
        loadType();
        cbInterior.setItems(listInterior);
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

        cbType.setCellFactory(param->new javafx.scene.control.ListCell<HouseType>(){
            protected void updateItem(HouseType item,boolean empty){
                super.updateItem(item,empty);
                if (item == null || empty){
                    setText(null);
                }else{
                    setText(item.getNama());
                }
            }
        });
        cbType.setConverter(new StringConverter<HouseType>() {
            @Override
            public String toString(HouseType houseType) {
                return houseType == null ? null : houseType.getNama();
            }

            @Override
            public HouseType fromString(String s) {
                return null;
            }
        });
        txtWatt.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtWatt.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        txtBed.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtBed.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        txtBuild.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtBuild.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });


        txtRest.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtRest.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    public void loadPerum()
    {
        try{
            Database connect = new Database();
            connect.stat = connect.conn.createStatement();
            String query = "SELECT * FROM ms_perumahan";
            connect.result = connect.stat.executeQuery(query);
            while (connect.result.next())
            {
                if (connect.result.getInt("status") == 1){
                    listPerum.add(new Perumahan(connect.result.getString("id_perumahan"),
                            connect.result.getString("nama_perumahan")));
                }
            }
            connect.stat.close();
            connect.result.close();
        }catch (SQLException ex)
        {
            System.out.println("Error: "+ex.getMessage());
        }
        cbResidence.setItems(listPerum);
    }

    public void loadType(){
        try{
            Database connect = new Database();
            connect.stat = connect.conn.createStatement();
            String query = "SELECT * FROM ms_tipe_rumah";
            connect.result = connect.stat.executeQuery(query);
            while(connect.result.next())
            {
                if (connect.result.getInt("status") == 1){
                    listTipe.add(new HouseType(connect.result.getString("id_tipe"),connect.result.getString("nama_tipe")));
                }
            }
            connect.stat.close();
            connect.result.close();
        }catch (SQLException ex){
            System.out.println("Error: "+ex.getMessage());
        }
        cbType.setItems(listTipe);
    }

    public void setDataList(Rumah data){
        txtId.setText(data.getId());
        setPSelectedCbBox(data.getIdp());
        setTSelectedCbBox(data.getIdt());
        txtBlok.setText(data.getBlok());
        cbInterior.getSelectionModel().select(data.getInterior());
        txtWatt.setText(data.getWatt().toString());
        txtBed.setText(data.getBed().toString());
        txtRest.setText(data.getRest().toString());
        txtBuild.setText(data.getTahun().toString());
        txtPrice.setText(data.getHarga().toString());
        image = data.getFoto();
        txtDesc.setText(data.getDesc());
    }

    private void setPSelectedCbBox(String data){
        for (Perumahan item : cbResidence.getItems()){
            if (item.getId().equals(data)){
                cbResidence.getSelectionModel().select(item);
            }
        }
    }

    private void setTSelectedCbBox(String data){
        for (HouseType item : cbType.getItems()){
            if (item.getId().equals(data)){
                cbType.getSelectionModel().select(item);
            }
        }
    }

    public void onActionUpdate(ActionEvent actionEvent) {
        if (isEmpty())
        {
            fillBox(btnFile,"Please fill all the field");
            return;
        }
        try{
            Database connect = new Database();
            String query = "EXEC sp_updateRumah ?,?,?,?,?,?,?,?,?,?,?,?";
            connect.pstat = connect.conn.prepareStatement(query);
            connect.pstat.setString(1,txtId.getText());
            connect.pstat.setString(2,cbResidence.getSelectionModel().getSelectedItem().getId());
            connect.pstat.setBytes(3,file != null ? imageToByte(file) : convertImageToBytes(image));
            connect.pstat.setString(4,txtBlok.getText());
            connect.pstat.setInt(5,Integer.parseInt(txtWatt.getText()));
            connect.pstat.setString(6,cbInterior.getSelectionModel().getSelectedItem());
            connect.pstat.setInt(7,Integer.parseInt(txtBed.getText()));
            connect.pstat.setInt(8,Integer.parseInt(txtRest.getText()));
            connect.pstat.setString(9,cbType.getSelectionModel().getSelectedItem().getId());
            connect.pstat.setString(10,txtDesc.getText());
            connect.pstat.setDouble(11,Double.parseDouble(txtPrice.getText()));
            connect.pstat.setInt(12,Integer.parseInt(txtBuild.getText()));
            connect.pstat.executeUpdate();
            connect.pstat.close();
            successBox(btnFile,"Data has been updated");
            clear();
        }catch (SQLException | IOException ex){
            System.out.println("Error: "+ex.getMessage());
        }
    }

    public boolean isEmpty()
    {
        if (txtBlok.getText().isEmpty() || txtWatt.getText().isEmpty() || txtBed.getText().isEmpty() || txtRest.getText().isEmpty() || txtBuild.getText().isEmpty() || txtPrice.getText().isEmpty() || txtDesc.getText().isEmpty())
        {
            return true;
        }
        return false;
    }

    public void onActionFile(ActionEvent actionEvent) {
        file = imageChooser(btnFile);
        LabFile.setText(file.getName());
    }

    public void back() {
        setPane("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/Master/Rumah/viewRumah.fxml");
    }

    private void setPane(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent pane = loader.load(); // Memuat file FXML

            FadeTransition fadeOut = new FadeTransition(Duration.millis(500), AncoreMaster);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setOnFinished(event -> {
                AncoreMaster.getChildren().setAll(pane); // Mengganti seluruh konten di dalam AncoreMaster dengan konten dari pane yang baru dimuat

                FadeTransition fadeIn = new FadeTransition(Duration.millis(500), AncoreMaster);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeIn.play();
            });
            fadeOut.play();
        } catch (Exception e) {
            e.printStackTrace(); // Menangani kesalahan
        }
    }

public void clear() {
    txtId.clear();
    txtBlok.clear();
    txtWatt.clear();
    txtBed.clear();
    txtRest.clear();
    txtBuild.clear();
    txtPrice.clear();
    txtDesc.clear();
    cbResidence.getSelectionModel().clearSelection();
    cbType.getSelectionModel().clearSelection();
    cbInterior.getSelectionModel().clearSelection();
    LabFile.setText("Choose file here");
    file = null;
}
}
