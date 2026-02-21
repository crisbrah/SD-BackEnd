package com.codigo.sanidaddivina.domain.model;

import com.codigo.sanidaddivina.domain.model.enums.TipoRegistroAsistencia;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Asistencia {
    private Long id;
    private LocalDateTime fechaAsistencia;
    private TipoRegistroAsistencia tipoRegistro;
    private Long personaId;
    private String personaNombres;
    private String personaDni;
    private Long miembroId;
    private Long registradoPorId;
    private Long sesionId;
}
