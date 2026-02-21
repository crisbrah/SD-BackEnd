package com.codigo.sanidaddivina.domain.port.in.asistencia;

import com.codigo.sanidaddivina.domain.model.Asistencia;

public interface RegistrarAsistenciaManualUseCase {
    Asistencia ejecutar(Command command);

    record Command(Long personaId, Long registradoPorId, Long sesionId) {}
}
