package org.radianite.prg3javafxsistemmarketingperumahan.Models;

import javafx.scene.image.Image;

public class Kavling {
    private String id,idp;
    private Image foto;
    private String blok,luas;
    private Double dp,harga;
    private String namaperum;

    public Kavling(String id, String idp, Image foto, String blok, String luas, Double dp, Double harga,String namaperum) {
        this.id = id;
        this.idp = idp;
        this.foto = foto;
        this.blok = blok;
        this.luas = luas;
        this.dp = dp;
        this.harga = harga;
        this.namaperum = namaperum;
    }

    public String getNamaperum() {
        return namaperum;
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

    public String getLuas() {
        return luas;
    }

    public Double getDp() {
        return dp;
    }

    public Double getHarga() {
        return harga;
    }
}
