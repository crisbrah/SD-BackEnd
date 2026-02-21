package com.codigo.sanidaddivina.entities;

import com.codigo.sanidaddivina.domain.model.enums.TipoCulto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "sesion_culto")
@Getter
@Setter
public class SesionCultoEntity extends AuditoriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sesion")
    private Long idSesion;

    @Column(name = "nombre_sesion", nullable = false, length = 150)
    private String nombreSesion;

    @Column(name = "tipo_culto", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private TipoCulto tipoCulto;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDateTime fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDateTime fechaFin;

    @Column(name = "abierta", nullable = false)
    private boolean abierta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sede")
    private SedeEntity sede;
}
