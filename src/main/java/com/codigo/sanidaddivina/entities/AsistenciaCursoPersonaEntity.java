package com.codigo.sanidaddivina.entities;

import com.codigo.sanidaddivina.domain.model.enums.TipoRegistroAsistencia;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "asistencia_curso_persona")
@Getter
@Setter
public class AsistenciaCursoPersonaEntity extends AuditoriaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asistencia_curso")
    private Long idAsistenciaCurso;

    @Column(name = "fecha_asistencia_Curso")
    private Timestamp fechaAsistenciaCurso;

    @Column(name = "tipo_registro", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private TipoRegistroAsistencia tipoRegistro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_curso")
    private CursoEntity curso;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_persona")
    private PersonaEntity persona;
}
