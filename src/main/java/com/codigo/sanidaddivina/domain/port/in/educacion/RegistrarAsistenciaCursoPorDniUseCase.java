package com.codigo.sanidaddivina.domain.port.in.educacion;

import com.codigo.sanidaddivina.domain.model.AsistenciaCursoPersona;

public interface RegistrarAsistenciaCursoPorDniUseCase {
    /**
     * Registra asistencia a un curso a partir del DNI leído por el escáner de código de barras/QR.
     *
     * @param cursoId ID del curso activo
     * @param dni     DNI de la persona (valor escaneado del código de barras)
     * @return Asistencia registrada
     */
    AsistenciaCursoPersona ejecutar(Long cursoId, String dni);
}
