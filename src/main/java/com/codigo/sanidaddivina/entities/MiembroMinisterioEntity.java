package com.codigo.sanidaddivina.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Entity
@Table(name = "miembro_ministerio")
@Getter
@Setter

public class MiembroMinisterioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_miembro_ministerio")
    private Long idMiembroMinisterio;

    @Column(name = "fecha_ingreso_ministerio")
    private Date fechaIngresoMinisterio;
    @Column(name = "fecha_salida_ministerio")
    private Date fechaSalidaMinisterio;
    //  @JsonIgnore

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_miembro")
    private MiembroEntity miembro;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ministerio")
    private MinisterioEntity ministerio;
}
