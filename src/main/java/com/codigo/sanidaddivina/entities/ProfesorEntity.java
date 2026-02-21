package com.codigo.sanidaddivina.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "profesor")
@Getter
@Setter
public class ProfesorEntity extends AuditoriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_profesor")
    private Long idProfesor;

    @Column(name = "estado_profesor")
    private boolean estadoProfesor;

    @Column(name = "fecha_asignacion")
    private LocalDate fechaAsignacion;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_miembro", nullable = false, unique = true)
    private MiembroEntity miembro;
}
