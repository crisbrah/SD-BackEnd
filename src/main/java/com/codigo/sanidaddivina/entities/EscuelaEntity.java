package com.codigo.sanidaddivina.entities;

import com.codigo.sanidaddivina.domain.model.enums.FaseEscuela;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "escuela")
@Getter
@Setter
public class EscuelaEntity extends AuditoriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_escuela")
    private Long idEscuela;

    @Column(name = "nombre_escuela", nullable = false, length = 150)
    private String nombreEscuela;

    @Column(name = "fase")
    @Enumerated(EnumType.STRING)
    private FaseEscuela fase;

    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion;

    @Column(name = "estado_escuela")
    private boolean estadoEscuela;
}
