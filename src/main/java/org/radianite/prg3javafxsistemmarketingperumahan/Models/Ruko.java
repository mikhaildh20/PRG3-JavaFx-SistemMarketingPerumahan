package org.radianite.prg3javafxsistemmarketingperumahan.Models;

import javafx.scene.image.Image;

public class Ruko {
    private String id,idp;
    private Image foto;
    private String blok;
    private Integer electric,toilet;
    private String desc;
    private Double rent;
    private String namaperum;

    public Ruko(String id, String idp, Image foto, String blok, Integer electric, Integer toilet, String desc, Double rent, String namaperum) {
        this.id = id;
        this.idp = idp;
        this.foto = foto;
        this.blok = blok;
        this.electric = electric;
        this.toilet = toilet;
        this.desc = desc;
        this.rent = rent;
        this.namaperum = namaperum;
    }

    public Ruko(String id,String blok, Double rent) {
        this.id = id;
        this.blok = blok;
        this.rent = rent;
    }

    public String getId() {
        return id;
    }

    public String getIdp() {
        return idp;
    }

    public Image getFoto() {
        return foto;
    }

    public String getBlok() {
        return blok;
    }

    public Integer getElectric() {
        return electric;
    }

    public Integer getToilet() {
        return toilet;
    }

    public String getDesc() {
        return desc;
    }

    public Double getRent() {
        return rent;
    }

    public String getNamaperum() {
        return namaperum;
    }
}
