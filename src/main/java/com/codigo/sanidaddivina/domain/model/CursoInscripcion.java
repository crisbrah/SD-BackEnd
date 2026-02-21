package com.codigo.sanidaddivina.domain.model;

import com.codigo.sanidaddivina.domain.model.enums.EstadoCursoPersona;
import com.codigo.sanidaddivina.domain.model.enums.MetodoPago;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CursoInscripcion {
    private Long id;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private BigDecimal nota;
    private EstadoCursoPersona estadoInscripcion;
    private Long cursoId;
    private Long personaId;
    private MetodoPago metodoPago;
}
