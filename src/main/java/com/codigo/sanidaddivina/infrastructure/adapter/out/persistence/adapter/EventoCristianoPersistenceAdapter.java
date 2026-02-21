package com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.adapter;

import com.codigo.sanidaddivina.dao.EventoCristianoRepository;
import com.codigo.sanidaddivina.domain.model.EventoCristiano;
import com.codigo.sanidaddivina.domain.port.out.EventoCristianoRepositoryPort;
import com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.mapper.EventoCristianoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EventoCristianoPersistenceAdapter implements EventoCristianoRepositoryPort {

    private final EventoCristianoRepository eventoCristianoRepository;
    private final EventoCristianoMapper eventoCristianoMapper;

    @Override
    public EventoCristiano guardar(EventoCristiano evento) {
        return eventoCristianoMapper.toDomain(eventoCristianoRepository.save(eventoCristianoMapper.toEntity(evento)));
    }

    @Override
    public Optional<EventoCristiano> buscarPorId(Long id) {
        return eventoCristianoRepository.findById(id).map(eventoCristianoMapper::toDomain);
    }

    @Override
    public Page<EventoCristiano> buscarTodos(Pageable pageable) {
        return eventoCristianoRepository.findAll(pageable).map(eventoCristianoMapper::toDomain);
    }

    @Override
    public void eliminar(Long id) {
        eventoCristianoRepository.deleteById(id);
    }
}
