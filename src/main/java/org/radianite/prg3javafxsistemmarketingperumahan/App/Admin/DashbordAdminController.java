package org.radianite.prg3javafxsistemmarketingperumahan.App.Admin;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.User;

import java.util.List;

public class DashbordAdminController {
    private List<User> users;

    @FXML
    private Text texttest; // Label di FXML untuk menampilkan nama pengguna

    public void setUsersAdmin(List<User> users) {
        this.users = users;
        if (users != null && !users.isEmpty()) {
            texttest.setText(users.get(0).getName()+" (Admin)"); // Misal menampilkan nama user pertama
        }
    }

    @FXML
    public void initialize() {
        // Inisialisasi lainnya jika diperlukan
    }
}
