package com.codigo.sanidaddivina.domain.model;

import com.codigo.sanidaddivina.domain.model.enums.TipoRegistroAsistencia;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AsistenciaCursoPersona {
    private Long id;
    private Timestamp fechaAsistenciaCurso;
    private TipoRegistroAsistencia tipoRegistro;
    private Long cursoId;
    private Long personaId;
}
