package com.codigo.sanidaddivina.domain.port.in.evento;

import com.codigo.sanidaddivina.domain.model.PersonaEvento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.Optional;

public interface RegistrarPersonaEventoUseCase {
    PersonaEvento registrar(Command command);
    Optional<PersonaEvento> buscarPorId(Long id);
    Page<PersonaEvento> buscarPorEvento(Long eventoId, Pageable pageable);
    void eliminar(Long id);

    record Command(Long eventoId, Long personaId, Date fechaEvento) {}
}
