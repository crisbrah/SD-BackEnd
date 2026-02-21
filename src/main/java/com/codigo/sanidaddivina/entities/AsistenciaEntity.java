package com.codigo.sanidaddivina.entities;

import com.codigo.sanidaddivina.domain.model.enums.TipoRegistroAsistencia;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Table(name = "asistencia")
@Getter
@Setter
public class AsistenciaEntity extends AuditoriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asistencia")
    private Long idAsistencia;

    @Column(name = "fecha_asistencia")
    private LocalDateTime fechaAsistencia;

    @Column(name = "tipo_registro", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private TipoRegistroAsistencia tipoRegistro;

    @Column(name = "registrado_por_id")
    private Long registradoPorId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_persona")
    private PersonaEntity persona;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_miembro")
    private MiembroEntity miembro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sesion")
    private SesionCultoEntity sesionCulto;
}
