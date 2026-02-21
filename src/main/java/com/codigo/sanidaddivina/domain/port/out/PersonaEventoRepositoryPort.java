package com.codigo.sanidaddivina.domain.port.out;

import com.codigo.sanidaddivina.domain.model.PersonaEvento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PersonaEventoRepositoryPort {
    PersonaEvento guardar(PersonaEvento personaEvento);
    Optional<PersonaEvento> buscarPorId(Long id);
    Page<PersonaEvento> buscarTodos(Pageable pageable);
    Page<PersonaEvento> buscarPorEvento(Long eventoId, Pageable pageable);
    void eliminar(Long id);
}
