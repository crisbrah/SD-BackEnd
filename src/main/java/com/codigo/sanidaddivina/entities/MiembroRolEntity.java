package com.codigo.sanidaddivina.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Entity
@Table(name = "miembro_rol")
@Getter
@Setter

public class MiembroRolEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_miembro_rol")
    private Long idMiembroRol;

    @Column(name = "inicio_rol")
    private Date inicioRol;
    @Column(name = "fin_rol")
    private Date finRol;
    //  @JsonIgnore

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_miembro")
    private MiembroEntity miembro;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rol")
    private RolEntity rol;
}
