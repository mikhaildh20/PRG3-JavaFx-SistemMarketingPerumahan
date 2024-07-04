package org.radianite.prg3javafxsistemmarketingperumahan.App.Agent;

import javafx.fxml.FXML;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Rumah;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.User;

import java.util.ArrayList;

public class RumahController {

    ArrayList<Rumah> rumahlist = new ArrayList<>();

    public void setDataList(Rumah data) {
        rumahlist.add(data);

    }
}
