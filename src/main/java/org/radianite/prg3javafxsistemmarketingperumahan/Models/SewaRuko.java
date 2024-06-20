package org.radianite.prg3javafxsistemmarketingperumahan.Models;

public class SewaRuko {
    private String id,nama,blok;
    private Integer status;
    public SewaRuko(String id, String nama, String blok, Integer status) {
        this.id = id;
        this.nama = nama;
        this.blok = blok;
        this.status = status;
    }
    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getBlok() {
        return blok;
    }

    public Integer getStatus() {
        return status;
    }
}
