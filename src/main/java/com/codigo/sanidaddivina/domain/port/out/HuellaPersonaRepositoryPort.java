package com.codigo.sanidaddivina.domain.port.out;

import com.codigo.sanidaddivina.domain.model.HuellaPersona;

import java.util.List;
import java.util.Optional;

public interface HuellaPersonaRepositoryPort {
    HuellaPersona guardar(HuellaPersona huella);
    Optional<HuellaPersona> buscarPorId(Long id);
    List<HuellaPersona> buscarActivasPorPersona(Long personaId);
    List<HuellaPersona> buscarTodasActivas();
    void eliminar(Long id);
}
