package org.radianite.prg3javafxsistemmarketingperumahan.App.Manager;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Methods.Library;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Rumah;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.User;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MainDashboardControllerManager extends Library {
    private List<User> users;
    private ArrayList<User> userList = new ArrayList<>();

    @FXML private AnchorPane GroupMenu;
    @FXML private Text tglLabel;
    @FXML private Text txtHalo;
    @FXML private Text txtTransaction;
    @FXML private Text txtIncome;
    @FXML private Label lblBlok;
    @FXML private Label lblHarga;
    @FXML private ImageView fotoRumah;
    private ObservableList<Rumah> listRumah = FXCollections.observableArrayList();

    private Database connection;

    public void initialize() {

        connection = new Database();
        startClock();

    }
    public void setDataList(User data) {

        userList.add(data);
            txtHalo.setText("Halo, " + userList.get(0).getName());
            txtTransaction.setText(String.valueOf(callUDFTotal(data.getUsn())));
            float pnedapatan = (float) callUDFPnedapatan(data.getUsn());
            if (pnedapatan >= 1000000000) {
                txtIncome.setText("Rp " + String.format("%,.0f", pnedapatan / 1000000000).replace(",", ".") + "jt");
            } else if (pnedapatan >= 1000000) {
                txtIncome.setText("Rp " + String.format("%,.0f", pnedapatan / 1000000).replace(",", ".") + "jt");
            } else {
                txtIncome.setText("Rp " + String.format("%,.0f", pnedapatan).replace(",", "."));
            }
        loadRumah();
        System.out.println(listRumah.get(0).getHarga());
/*
        lblBlok.setText(listRumah.get(0).getBlok());
        lblHarga.setText("Rp " + String.format("%,.0f", listRumah.get(0).getHarga()).replace(",", "."));*/


/*        fotoRumah.setImage(listRumah.get(0).getFoto());
        fotoRumah.setFitHeight(679);
        fotoRumah.setFitWidth(706);
        fotoRumah.setPreserveRatio(false);*/
    }

    private int callUDFTotal(String usn) {
        int count = 0;
        try {
            String udfQuery = "{? = CALL  fn_CountTotalRumahDanRukoByUsername(?)}";
            CallableStatement callableStatement = connection.conn.prepareCall(udfQuery);
            callableStatement.registerOutParameter(1, Types.INTEGER);
            callableStatement.setString(2, usn);
            callableStatement.execute();
            count = callableStatement.getInt(1);
            callableStatement.close();
        } catch (SQLException e) {
            showAlert("Error", "Error calling UDF", e.getMessage());
        }
        return count;
    }
    private int callUDFPnedapatan(String usn) {
        int count = 0;
        try {
            String udfQuery = "{? = CALL dbo.fn_TotalPembayaranByUsername(?)}";
            CallableStatement callableStatement = connection.conn.prepareCall(udfQuery);
            callableStatement.registerOutParameter(1, Types.FLOAT);
            callableStatement.setString(2,usn );
            callableStatement.execute();
            count = callableStatement.getInt(1);
            callableStatement.close();
        } catch (SQLException e) {
            showAlert("Error", "Error calling UDF", e.getMessage());
        }
        return count;
    }


    private void startClock() {
        Timeline clock = new Timeline(
                new KeyFrame(Duration.ZERO, e -> {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    tglLabel.setText(LocalDateTime.now().format(formatter));
                }),
                new KeyFrame(Duration.seconds(1))
        );

        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();
    }
    private void showAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    private void loadRumah() {
        try {
            Database connect = new Database();
            connect.stat = connect.conn.createStatement();
            String query = "SELECT * FROM ms_rumah " +
                    "WHERE id_perumahan = '"+userList.get(0).getIdp()+"' " +
                    "AND status = 1 " +
                    "AND ketersediaan = 1 " +
                    "AND harga = (SELECT MAX(harga) " +
                    "FROM ms_rumah " +
                    "WHERE id_perumahan = '"+userList.get(0).getIdp()+"' " +
                    "AND status = 1 " +
                    "AND ketersediaan = 1)";
            connect.result = connect.stat.executeQuery(query);
            while (connect.result.next()) {
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
                        null,null,
                        connect.result.getInt("status"),
                        connect.result.getInt("ketersediaan"))); // Added status
            }
            connect.stat.close();
            connect.result.close();
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
