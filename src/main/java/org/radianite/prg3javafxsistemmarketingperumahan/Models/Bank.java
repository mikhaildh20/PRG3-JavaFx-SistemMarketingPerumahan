package org.radianite.prg3javafxsistemmarketingperumahan.Models;

public class Bank {
    private String id,name;
    private Integer bunga;

    public Bank(String id, String name, Integer bunga) {
        this.id = id;
        this.name = name;
        this.bunga = bunga;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getBunga() {
        return bunga;
    }
}
