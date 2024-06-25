package org.radianite.prg3javafxsistemmarketingperumahan.App.Agen;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.User;

import java.util.ArrayList;
import java.util.List;

public class DashbordAgenController {
    ArrayList<User> userList = new ArrayList<>();

    public void setDataList(User data){
        userList.add(data);
    }
    @FXML
    public void initialize() {
        // Inisialisasi lainnya jika diperlukan
    }
}
