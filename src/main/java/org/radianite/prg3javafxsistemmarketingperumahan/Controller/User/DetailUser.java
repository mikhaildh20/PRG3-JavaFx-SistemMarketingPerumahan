package org.radianite.prg3javafxsistemmarketingperumahan.Controller.User;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.User;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;

public class DetailUser {
    @FXML
    private Label labelNama;
    @FXML
    private Label labelEmail;
    @FXML
    private Label labelAlamat;
    @FXML
    private Label labelKelamin;
    @FXML
    private Label labelUmur;
    @FXML
    private Label labelRole;
    @FXML
    private Label labelUsn;
    @FXML
    private Label labelPsw;
    @FXML
    private ImageView imageFoto;


    public void setData(User user){
        labelUsn.setText(user.getUsn());
        labelPsw.setText(user.getPass());
        labelRole.setText(user.getRName());
        labelNama.setText(user.getName());
        labelEmail.setText(user.getEmail());
        labelUmur.setText(user.getAge().toString());
        labelKelamin.setText(user.getGender());
        imageFoto.setImage(user.getFoto());
        labelAlamat.setText(user.getAddress());

    }

}
