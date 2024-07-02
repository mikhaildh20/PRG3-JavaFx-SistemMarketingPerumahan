package org.radianite.prg3javafxsistemmarketingperumahan.App.Manager;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.User;

import java.util.List;

public class DashbordManagerController {
    private List<User> userList;

    @FXML
    private Text texttest; // Label di FXML untuk menampilkan nama pengguna

    public void setDataList(User data){
        userList.add(data);
    }

    @FXML
    public void initialize() {
        // Inisialisasi lainnya jika diperlukan
    }
}
