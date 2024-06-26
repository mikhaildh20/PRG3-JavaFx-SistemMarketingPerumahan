package org.radianite.prg3javafxsistemmarketingperumahan.Models;

import javafx.scene.image.Image;

public class User {
    private String usn,pass,idp,idr,name,email,address,gender;
    private Integer age;
    private Image foto;
    private String PName,RName;

    public User(){}
    public User(String usn, String pass, String idp, String idr, String name, String email, String address, String gender, Integer age, Image photo) {
        this.usn = usn;
        this.pass = pass;
        this.idp = idp;
        this.idr = idr;
        this.name = name;
        this.email = email;
        this.address = address;
        this.gender = gender;
        this.age = age;
        this.foto = photo;
    }

    public User(String usn, String pass, String idp, String idr, String name, String email, String address, String gender, Integer age,Image foto, String pName, String rName) {
        this.usn = usn;
        this.pass = pass;
        this.idp = idp;
        this.idr = idr;
        this.name = name;
        this.email = email;
        this.address = address;
        this.gender = gender;
        this.age = age;
        this.foto = foto;
        this.PName = pName;
        this.RName = rName;
    }

    public String getPName() {
        return PName;
    }

    public String getRName() {
        return RName;
    }

    public String getUsn() {
        return usn;
    }

    public String getPass() {
        return pass;
    }

    public String getIdp() {
        return idp;
    }

    public String getIdr() {
        return idr;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getGender() {
        return gender;
    }

    public Integer getAge() {
        return age;
    }

    public Image getFoto() {
        return foto;
    }
    public User getAll() {
        return new User(usn, pass, idp, idr, name, email, address, gender, age, foto, PName, RName);
    }
}
