package com.codigo.sanidaddivina.domain.port.in.educacion;

import com.codigo.sanidaddivina.domain.model.CursoInscripcion;
import com.codigo.sanidaddivina.domain.model.enums.MetodoPago;

public interface MatricularPersonaCursoUseCase {
    CursoInscripcion ejecutar(Command command);

    record Command(Long cursoId, Long personaId, MetodoPago metodoPago) {}
}
