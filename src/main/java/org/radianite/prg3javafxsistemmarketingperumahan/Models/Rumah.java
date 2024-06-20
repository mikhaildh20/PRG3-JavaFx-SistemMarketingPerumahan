package org.radianite.prg3javafxsistemmarketingperumahan.Models;

import javafx.scene.image.Image;

public class Rumah {
    private String id,idp;
    private Image foto;
    private String blok;
    private Integer watt;
    private String interior;
    private Integer bed,rest;
    private String idt,desc;
    private Double harga;
    private Integer tahun;
    private String residence,type;

    public Rumah(String id, String idp, Image foto, String blok, Integer watt, String interior, Integer bed, Integer rest, String idt, String desc, Double harga, Integer tahun, String residence, String type) {
        this.id = id;
        this.idp = idp;
        this.foto = foto;
        this.blok = blok;
        this.watt = watt;
        this.interior = interior;
        this.bed = bed;
        this.rest = rest;
        this.idt = idt;
        this.desc = desc;
        this.harga = harga;
        this.tahun = tahun;
        this.residence = residence;
        this.type = type;
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

    public Integer getWatt() {
        return watt;
    }

    public String getInterior() {
        return interior;
    }

    public Integer getBed() {
        return bed;
    }

    public Integer getRest() {
        return rest;
    }

    public String getIdt() {
        return idt;
    }

    public String getDesc() {
        return desc;
    }

    public Double getHarga() {
        return harga;
    }

    public Integer getTahun() {
        return tahun;
    }

    public String getResidence() {
        return residence;
    }

    public String getType() {
        return type;
    }
}
