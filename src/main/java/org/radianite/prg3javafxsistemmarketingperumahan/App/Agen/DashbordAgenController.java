package org.radianite.prg3javafxsistemmarketingperumahan.App.Agen;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.User;

import java.util.List;

public class DashbordAgenController {
    private List<User> users;

    @FXML
    private Text texttest; // Label di FXML untuk menampilkan nama pengguna

    public void setUsersAgen(List<User> users) {
        this.users = users;
        if (users != null && !users.isEmpty()) {
            texttest.setText(users.get(0).getName()+" (Agen)"); // Misal menampilkan nama user pertama
        }
    }

    @FXML
    public void initialize() {
        // Inisialisasi lainnya jika diperlukan
    }
}
