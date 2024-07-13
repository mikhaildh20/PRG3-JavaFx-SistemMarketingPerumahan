package org.radianite.prg3javafxsistemmarketingperumahan.Controller.TransaksiRumah;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import javafx.util.StringConverter;
import org.radianite.prg3javafxsistemmarketingperumahan.App.Agent.TransaksiRumahController;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Methods.Library;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Bank;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Rumah;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.User;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Pembelian extends Library implements Initializable {
    @FXML
    private TextField txtId, txtDate, txtNIK, txtNama, txtContact, txtRekening, txtTotal, txtMinCicil, txtCicil, txtPinjaman, txtBulanan;
    @FXML
    private ComboBox<Rumah> cbBlok;
    @FXML
    private ComboBox<Bank> cbBank;
    @FXML
    private ComboBox<String> cbPayment;
    @FXML
    private ComboBox<String> cbPeriode;
    @FXML
    private Button btnFile;
    @FXML
    private Label LabFile;
    @FXML
    private Button btnback;
    @FXML
    private AnchorPane mainpane;
    @FXML private TextField txtBlok;

    private ObservableList<Rumah> listRumah = FXCollections.observableArrayList();
    private ObservableList<Bank> listBank = FXCollections.observableArrayList();
    private ObservableList<String> listPayment = FXCollections.observableArrayList("CASH", "CREDIT");
    private ObservableList<String> listPeriode = FXCollections.observableArrayList("5 Year", "10 Year", "15 Year", "20 Year", "25 Year");
    private File file;
    private String id;
    private String idper;
    private ArrayList<User> userlist = new ArrayList<>();
    private ArrayList<Rumah> rumahlist = new ArrayList<>();
    private int selected = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadRumah();
        loadBank();
        cbPayment.setItems(listPayment);
        cbPeriode.setItems(listPeriode);

        txtId.setEditable(false);
        txtDate.setEditable(false);
        cbBank.setEditable(false);
        cbPeriode.setEditable(false);
        txtRekening.setEditable(false);
        txtTotal.setEditable(false);
        txtMinCicil.setEditable(false);
        txtCicil.setEditable(false);
        txtPinjaman.setEditable(false);
        txtBulanan.setEditable(false);
        txtId.setText(generateID("tr_rumah", "TRR", "id_trRumah"));
        txtDate.setText(LocalDate.now().toString());

        txtNIK.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtNIK.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        txtNama.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-Z*")) {
                txtNama.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            }
        });

        txtContact.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtContact.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        txtRekening.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtRekening.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        txtTotal.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtTotal.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        txtMinCicil.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtMinCicil.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        txtCicil.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtCicil.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        txtPinjaman.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtPinjaman.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        txtBulanan.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtBulanan.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        cbBlok.setCellFactory(param -> new javafx.scene.control.ListCell<Rumah>() {
            protected void updateItem(Rumah item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.getBlok());
                }
            }
        });
        cbBlok.setConverter(new StringConverter<Rumah>() {
            @Override
            public String toString(Rumah rumah) {
                return rumah == null ? null : rumah.getBlok();
            }

            @Override
            public Rumah fromString(String s) {
                return null;
            }
        });

        cbBank.setCellFactory(param -> new javafx.scene.control.ListCell<Bank>() {
            protected void updateItem(Bank item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });
        cbBank.setConverter(new StringConverter<Bank>() {
            @Override
            public String toString(Bank bank) {
                return bank == null ? null : bank.getName();
            }

            @Override
            public Bank fromString(String s) {
                return null;
            }
        });

        txtRekening.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // if focus is lost
                if (cbBank.getSelectionModel().getSelectedItem() != null && cbPeriode.getSelectionModel().getSelectedItem() != null) {
                    txtCicil.setDisable(false);
                }
            }
        });

        txtCicil.focusedProperty().addListener((observable, oldValue, newValue) -> {
            try {
                Double firstpay = convertStringDouble(txtCicil.getText());
                Double total = convertStringDouble(txtTotal.getText());
                Double downpay = convertStringDouble(txtMinCicil.getText());
                Integer bunga = cbBank.getSelectionModel().getSelectedItem().getBunga();
                Double finale = total - firstpay;
                Double totalPinjaman;
                Double totalBunga;
                if (!newValue) {
                    if (firstpay >= downpay) {
                        totalBunga = cicilanPerbulan(finale, bunga);
                        totalPinjaman = totalPinjamanBunga(totalBunga);
                        txtPinjaman.setText(convertDoubleString(totalPinjaman));
                        txtBulanan.setText(convertDoubleString(totalBunga));
                    }
                }
            } catch (Exception ignored) {
            }
        });

        // Set cbBlok to index 0 if listRumah is not empty
        if (!listRumah.isEmpty()) {
            cbBlok.getSelectionModel().select(0);
        }
    }

    public void onActionBtnBack(ActionEvent actionEvent) {
        setPane("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Agent/TransaksiRumah.fxml");
    }



    public void setDataList(String id, String idper, User user) {
        this.id = id;
        this.idper = idper;
        listRumah.clear();
        userlist.add(user);

        try {
            Database connect = new Database();
            connect.stat = connect.conn.createStatement();
            String query = "SELECT id_rumah, blok, harga, ketersediaan, status FROM ms_rumah where id_rumah='" + id + "'";
            connect.result = connect.stat.executeQuery(query);
            while (connect.result.next()) {
                listRumah.add(new Rumah(connect.result.getString("id_rumah"),
                        connect.result.getString("blok"),
                        connect.result.getDouble("harga")));
            }
            connect.stat.close();
            connect.result.close();
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        cbBlok.setItems(listRumah);

        if (!listRumah.isEmpty()) {
            cbBlok.getSelectionModel().select(0);
            txtBlok.setText(listRumah.get(0).getBlok());
            Rumah selectedRumah = cbBlok.getSelectionModel().getSelectedItem();
            if (selectedRumah != null) {
                txtTotal.setText(convertDoubleString(selectedRumah.getHarga()));
                txtMinCicil.setText(convertDoubleString(minFirstPayment(selectedRumah.getHarga())));
            }
        }
    }

    private void loadRumah() {
        try {
            Database connect = new Database();
            connect.stat = connect.conn.createStatement();
            String query = "SELECT id_rumah, blok, harga, ketersediaan, status FROM ms_rumah where id_rumah='" + id + "'";
            connect.result = connect.stat.executeQuery(query);
            while (connect.result.next()) {
                listRumah.add(new Rumah(connect.result.getString("id_rumah"),
                        connect.result.getString("blok"),
                        connect.result.getDouble("harga")));
            }
            connect.stat.close();
            connect.result.close();
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public void loadBank() {
        try {
            Database connect = new Database();
            connect.stat = connect.conn.createStatement();
            String query = "SELECT * FROM ms_bank";
            connect.result = connect.stat.executeQuery(query);
            while (connect.result.next()) {
                if (connect.result.getInt("status") == 1) {
                    listBank.add(new Bank(connect.result.getString("id_bank"),
                            connect.result.getString("nama_bank"),
                            connect.result.getInt("suku_bunga")));
                }
            }
            connect.stat.close();
            connect.result.close();
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        cbBank.setItems(listBank);
    }

    public void onActionDocument(ActionEvent actionEvent) {
        try {
            file = imageChooser(btnFile);
            LabFile.setText(file.getName());
        } catch (Exception e) {
            file = null;
        }
    }

    public void onActionSave(ActionEvent actionEvent) {
        try {
            Database connect = new Database();
            String query = "EXEC sp_inputTrRumah ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";
            connect.pstat = connect.conn.prepareStatement(query);

            // Set parameters
            connect.pstat.setString(1, txtId.getText());
            connect.pstat.setString(2, cbBlok.getSelectionModel().getSelectedItem().getId());
            connect.pstat.setString(3, userlist.get(0).getUsn());
            connect.pstat.setString(4, txtNIK.getText());
            connect.pstat.setString(5, txtNama.getText());
            connect.pstat.setString(6, txtContact.getText());
            connect.pstat.setString(7, cbPayment.getSelectionModel().getSelectedItem());

            // Handle payment specific parameters
            if (cbPayment.getSelectionModel().getSelectedIndex() == 1) {
                handlePaymentTypeOne(connect);
            } else {
                handlePaymentTypeZero(connect);
            }

            // Set common parameters
            connect.pstat.setDouble(11, cbPayment.getSelectionModel().getSelectedIndex() == 0 ? convertStringDouble(txtTotal.getText()) : convertStringDouble(txtPinjaman.getText()));
            connect.pstat.setInt(12, cbPayment.getSelectionModel().getSelectedIndex() == 0 ? 1 : 0);
            connect.pstat.setBytes(13, imageToByte(file));

            // Execute the update
            connect.pstat.executeUpdate();
            connect.pstat.close();
            successBox(btnback, "Trasaction Success");
        } catch (SQLException | IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void setPane(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent pane = loader.load();
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), mainpane);
            mainpane.setOpacity(0.0);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setOnFinished(eventFadeOut -> {
                mainpane.getChildren().setAll(pane);
                TransaksiRumahController controller = loader.getController();
                controller.setCard(idper);
                controller.setDataList(userlist.get(0));

                mainpane.setTranslateX(-50);
                TranslateTransition translate = new TranslateTransition(Duration.seconds(0.5), mainpane);
                translate.setToX(0);
                FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), mainpane);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                ParallelTransition parallelTransition = new ParallelTransition(translate, fadeIn);
                parallelTransition.play();
            });
            fadeOut.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handlePaymentTypeOne(Database connect) throws SQLException {
        connect.pstat.setString(8, cbBank.getSelectionModel().getSelectedItem().getId());
        connect.pstat.setString(9, txtRekening.getText());
        connect.pstat.setInt(10, cbBank.getSelectionModel().getSelectedItem().getBunga());
        connect.pstat.setDouble(14, convertStringDouble(txtBulanan.getText()));
        connect.pstat.setInt(15, tenorTahun());
        connect.pstat.setDouble(16, convertStringDouble(txtPinjaman.getText()));
        LocalDate currentDate = LocalDate.now();
        LocalDate futureDate = currentDate.plusMonths(1);
        Date sqlDate = Date.valueOf(futureDate);
        connect.pstat.setDate(17, sqlDate);
        connect.pstat.setNull(18, Types.DATE);
    }

    private void handlePaymentTypeZero(Database connect) throws SQLException {
        connect.pstat.setNull(8, Types.VARCHAR);
        connect.pstat.setNull(9, Types.VARCHAR);
        connect.pstat.setNull(10, Types.INTEGER);
        connect.pstat.setNull(14, Types.DOUBLE);
        connect.pstat.setNull(15, Types.INTEGER);
        connect.pstat.setNull(16, Types.DOUBLE);
        connect.pstat.setNull(17, Types.DATE);
        Date currentDate = new Date(System.currentTimeMillis());
        connect.pstat.setDate(18, currentDate);
    }

    public void onActionCbPay(ActionEvent actionEvent) {
        if (selected == 0) return;
        if (cbPayment.getSelectionModel().isSelected(1)) {
            cbBank.setDisable(false);
            cbPeriode.setDisable(false);
            txtRekening.setDisable(false);
            return;
        }
        txtRekening.setText("");
        cbBank.setDisable(true);
        cbPeriode.setDisable(true);
        txtRekening.setDisable(true);
        txtTotal.setText(convertDoubleString(cbBlok.getSelectionModel().getSelectedItem().getHarga()));
    }

    private Double cicilanPerbulan(Double total, Integer bunga) {
        Integer tenor_bulan = tenorTahun() * 12;
        Integer tenor_tahun = tenorTahun();

        Double persentase = bunga / 100.0;
        Double cicilanPokok = total / tenor_bulan;
        Double cicilanBunga = total * persentase * tenor_tahun / tenor_bulan;

        return cicilanPokok + cicilanBunga;
    }

    private int tenorTahun() {
        if (cbPeriode.getSelectionModel().isSelected(0)) {
            return 5;
        } else if (cbPeriode.getSelectionModel().isSelected(1)) {
            return 10;
        } else if (cbPeriode.getSelectionModel().isSelected(2)) {
            return 15;
        } else if (cbPeriode.getSelectionModel().isSelected(3)) {
            return 20;
        }
        return 25;
    }

    private Double totalPinjamanBunga(Double total) {
        return total * (tenorTahun() * 12);
    }

    private Double minFirstPayment(Double total) {
        return (15 * total) / 100;
    }

    public void onActionPeriode(ActionEvent actionEvent) {
        if (cbBank.getSelectionModel().getSelectedItem() != null && !txtRekening.getText().isEmpty()) {
            txtCicil.setDisable(false);
        }
    }

    public void onActionBlok(ActionEvent actionEvent) {
        selected = 1;
        txtTotal.setText(convertDoubleString(cbBlok.getSelectionModel().getSelectedItem().getHarga()));
        txtMinCicil.setText(convertDoubleString(minFirstPayment(cbBlok.getSelectionModel().getSelectedItem().getHarga())));
        if (cbPayment.getSelectionModel().isSelected(1)) {
            cbBank.setDisable(false);
            cbPeriode.setDisable(false);
            txtRekening.setDisable(false);
        }
    }

    public void onActionBank(ActionEvent actionEvent) {
        if (cbPeriode.getSelectionModel().getSelectedItem() != null || !txtRekening.getText().isEmpty()) {
            txtCicil.setDisable(false);
        }
    }
}
