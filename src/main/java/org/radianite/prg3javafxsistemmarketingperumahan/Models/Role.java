package org.radianite.prg3javafxsistemmarketingperumahan.Models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Role {
    private final SimpleStringProperty idRole;
    private final SimpleStringProperty namaRole;
    private final SimpleIntegerProperty status;

    public Role(String idRole, String namaRole, int status) {
        this.idRole = new SimpleStringProperty(idRole);
        this.namaRole = new SimpleStringProperty(namaRole);
        this.status = new SimpleIntegerProperty(status);
    }

    public String getIdRole() {
        return idRole.get();
    }

    public void setIdRole(String idRole) {
        this.idRole.set(idRole);
    }

    public String getNamaRole() {
        return namaRole.get();
    }

    public void setNamaRole(String namaRole) {
        this.namaRole.set(namaRole);
    }

    public int getStatus() {
        return status.get();
    }

    public void setStatus(int status) {
        this.status.set(status);
    }

    public SimpleStringProperty idRoleProperty() {
        return idRole;
    }

    public SimpleStringProperty namaRoleProperty() {
        return namaRole;
    }

    public SimpleIntegerProperty statusProperty() {
        return status;
    }
}
