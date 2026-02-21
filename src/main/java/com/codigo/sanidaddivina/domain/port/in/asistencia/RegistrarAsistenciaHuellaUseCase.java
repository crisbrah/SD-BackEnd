package com.codigo.sanidaddivina.domain.port.in.asistencia;

import com.codigo.sanidaddivina.domain.model.Asistencia;

public interface RegistrarAsistenciaHuellaUseCase {
    Asistencia ejecutar(String fingerprintTemplateBase64);
}
