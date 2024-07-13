package org.radianite.prg3javafxsistemmarketingperumahan.Controller.Developer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Methods.Library;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Developer;

import java.sql.Connection;
import java.sql.SQLException;

public class Input extends Library {
    @FXML
    private TextField txtIdDeveloper;
    @FXML
    private TextField txtNamaDeveloper;
    @FXML
    private Button btnSave;
    @FXML
    private AnchorPane GroupMenu;
    @FXML
    private Button batalButton;
    @FXML
    private Label messageLabel;

    private String nextIdDeveloper;
    String idDeveloper, namaDeveloper;
    int status;
    Database connection = new Database();
    private ObservableList<Developer> developerList = FXCollections.observableArrayList();

    public void loadData()
    {
        try {
            connection.stat = connection.conn.createStatement();
            String query = "SELECT * FROM ms_developer"; // Ganti dari ms_role menjadi ms_developer
            connection.result = connection.stat.executeQuery(query);

            while (connection.result.next()) {
                developerList.add(new Developer(connection.result.getString("id_developer"), // Ganti dari id_role menjadi id_developer
                        connection.result.getString("nama_developer"), // Ganti dari nama_role menjadi nama_developer
                        connection.result.getInt("status")));
            }
            connection.stat.close();
            connection.result.close();
        } catch (Exception ex) {
            System.out.println("Terjadi error saat load data: " + ex);
        }

    }



    @FXML
    public void initialize() {
        txtIdDeveloper.setText(generateID("ms_developer","DVL","id_developer"));
        if (txtNamaDeveloper != null) {
            txtNamaDeveloper.addEventFilter(KeyEvent.KEY_TYPED, (KeyEvent e) -> {
                if (!"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".contains(e.getCharacter())) {
                    e.consume();
                }
            });
        }
        loadData();

    }

    @FXML
    protected void onBtnSimpanClick() {
        idDeveloper = txtIdDeveloper.getText();
        namaDeveloper = txtNamaDeveloper.getText();

        if (txtNamaDeveloper.getText().isEmpty())
        {
            fillBox(btnSave, "Please enter Developer name");
            return;
        }

        txtNamaDeveloper.addEventFilter(KeyEvent.KEY_TYPED, (KeyEvent e) -> {
            if (!"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".contains(e.getCharacter())) {
                e.consume();
            }
        });

        // Mengecek apakah sudah ada data dengan nama yang sama
        boolean isDuplicate = false;
        for (Developer developer : developerList) {
            if (namaDeveloper.equals(developer.getNamaDeveloper())) {
                isDuplicate = true;
                break;
            }
        }

        if (isDuplicate) {
            errorBox(btnSave, "Developer name already exists");
            return;
        }

        try {
            // Menggunakan stored procedure sp_inputDeveloper
            String query = "{call sp_inputDeveloper(?, ?)}";
            connection.pstat = connection.conn.prepareCall(query);
            connection.pstat.setString(1, idDeveloper);
            connection.pstat.setString(2, namaDeveloper);

            connection.pstat.executeUpdate();
            connection.pstat.close();
            successBox(btnSave, "Developer inserted successfully");
            setPane("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/Master/Developer/View.fxml", null,GroupMenu);
            clear();
        } catch (SQLException ex) {
            System.out.println("Terjadi error saat insert data developer: " + ex);
        }
        try {
            // Mengambil id_developer terakhir dari tabel Developer
            String queryGetLastId = "SELECT id_developer FROM ms_developer ORDER BY id_developer DESC";
            connection.pstat = connection.conn.prepareStatement(queryGetLastId);
            connection.result = connection.pstat.executeQuery();
            connection.result.close();
            connection.pstat.close();
            txtIdDeveloper.setText(generateID("ms_developer","DVL","id_developer"));
        } catch (SQLException ex) {
            System.out.println("Terjadi error saat mengambil id_developer terakhir: " + ex);
        }
    }

    private void clear() {
        txtIdDeveloper.clear();
        txtNamaDeveloper.clear();
    }

    @FXML
    protected void onBatalSimpanClick() {
        System.exit(0);
    }
}

