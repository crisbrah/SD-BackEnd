package com.codigo.sanidaddivina.domain.port.in.educacion;

import com.codigo.sanidaddivina.domain.model.CursoInscripcion;
import com.codigo.sanidaddivina.domain.model.enums.EstadoCursoPersona;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface RegistrarNotaUseCase {
    CursoInscripcion ejecutar(Long inscripcionId, Command command);

    record Command(BigDecimal nota, EstadoCursoPersona estado, LocalDate fechaFin) {}
}
