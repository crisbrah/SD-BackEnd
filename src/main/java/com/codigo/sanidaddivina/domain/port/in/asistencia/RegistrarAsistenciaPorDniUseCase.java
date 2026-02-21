package com.codigo.sanidaddivina.domain.port.in.asistencia;

import com.codigo.sanidaddivina.domain.model.Asistencia;

public interface RegistrarAsistenciaPorDniUseCase {
    /**
     * Registra asistencia a partir del DNI leído por el escáner de código de barras/QR.
     * Busca la sesión de culto abierta automáticamente.
     *
     * @param dni DNI de la persona (valor escaneado del código de barras)
     * @return Asistencia registrada
     */
    Asistencia ejecutar(String dni);
}
