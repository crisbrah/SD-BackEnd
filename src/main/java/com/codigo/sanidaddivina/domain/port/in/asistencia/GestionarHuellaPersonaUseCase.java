package com.codigo.sanidaddivina.domain.port.in.asistencia;

import com.codigo.sanidaddivina.domain.model.HuellaPersona;
import com.codigo.sanidaddivina.domain.model.enums.DedoHuella;

import java.util.List;

public interface GestionarHuellaPersonaUseCase {

    /** Registra un nuevo template de huella para una persona */
    HuellaPersona registrar(Command command);

    /** Desactiva una huella (no la elimina físicamente) */
    HuellaPersona desactivar(Long id);

    /** Lista las huellas activas de una persona */
    List<HuellaPersona> listarActivasPorPersona(Long personaId);

    /** Elimina físicamente una huella (solo si está desactivada) */
    void eliminar(Long id);

    record Command(
            Long personaId,
            byte[] templateHuella,
            DedoHuella dedo
    ) {}
}
