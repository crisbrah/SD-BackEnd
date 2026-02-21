package com.codigo.sanidaddivina.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@MappedSuperclass

public abstract class AuditoriaEntity {
    @Column(name = "usua_crea", length = 45, updatable = false)
    private String usuaCrea;

    @Column(name = "date_create", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp dateCreate;

    @Column(name = "usua_modif", length = 45)
    private String usuaModif;

    @Column(name = "date_modif")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp dateModif;

    @Column(name = "usua_delet", length = 45)
    private String usuaDelet;

    @Column(name = "date_delet")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp dateDelet;

    @PrePersist
    protected void onCreate() {
        dateCreate = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate() {
        dateModif = new Timestamp(System.currentTimeMillis());
    }
}
