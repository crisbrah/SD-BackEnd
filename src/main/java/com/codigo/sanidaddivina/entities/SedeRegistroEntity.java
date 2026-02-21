package com.codigo.sanidaddivina.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Entity
@Table(name = "sede_registro")
@Getter
@Setter

public class SedeRegistroEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sede_registro")
    private Long idSedeRegistro;
    @Column(name = "estado_registro", nullable = false, length = 100)
    private boolean estadoRegistro;
    @Column(name = "fecha_registro", nullable = false, length = 100)
    private Timestamp fechaRegistro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sedes")
    private SedeEntity sede;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_persona")
    private PersonaEntity persona;
}
