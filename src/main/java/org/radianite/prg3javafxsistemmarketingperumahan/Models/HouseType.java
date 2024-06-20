package org.radianite.prg3javafxsistemmarketingperumahan.Models;

public class HouseType {
    private String id,nama;

    public HouseType(String id, String nama) {
        this.id = id;
        this.nama = nama;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }
}
