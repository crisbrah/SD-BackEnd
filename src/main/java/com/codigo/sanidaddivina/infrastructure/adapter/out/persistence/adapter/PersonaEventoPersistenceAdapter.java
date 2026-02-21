package com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.adapter;

import com.codigo.sanidaddivina.dao.PersonaEventoRepository;
import com.codigo.sanidaddivina.domain.model.PersonaEvento;
import com.codigo.sanidaddivina.domain.port.out.PersonaEventoRepositoryPort;
import com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.mapper.PersonaEventoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PersonaEventoPersistenceAdapter implements PersonaEventoRepositoryPort {

    private final PersonaEventoRepository personaEventoRepository;
    private final PersonaEventoMapper personaEventoMapper;

    @Override
    public PersonaEvento guardar(PersonaEvento personaEvento) {
        return personaEventoMapper.toDomain(personaEventoRepository.save(personaEventoMapper.toEntity(personaEvento)));
    }

    @Override
    public Optional<PersonaEvento> buscarPorId(Long id) {
        return personaEventoRepository.findById(id).map(personaEventoMapper::toDomain);
    }

    @Override
    public Page<PersonaEvento> buscarTodos(Pageable pageable) {
        return personaEventoRepository.findAll(pageable).map(personaEventoMapper::toDomain);
    }

    @Override
    public Page<PersonaEvento> buscarPorEvento(Long eventoId, Pageable pageable) {
        return personaEventoRepository.findByEventoIdEvento(eventoId, pageable).map(personaEventoMapper::toDomain);
    }

    @Override
    public void eliminar(Long id) {
        personaEventoRepository.deleteById(id);
    }
}
