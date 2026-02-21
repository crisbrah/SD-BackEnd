package com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.adapter;

import com.codigo.sanidaddivina.dao.SedeRepository;
import com.codigo.sanidaddivina.domain.model.Sede;
import com.codigo.sanidaddivina.domain.port.out.SedeRepositoryPort;
import com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.mapper.SedeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SedePersistenceAdapter implements SedeRepositoryPort {

    private final SedeRepository sedeRepository;
    private final SedeMapper sedeMapper;

    @Override
    public Sede guardar(Sede sede) {
        return sedeMapper.toDomain(sedeRepository.save(sedeMapper.toEntity(sede)));
    }

    @Override
    public Optional<Sede> buscarPorId(Long id) {
        return sedeRepository.findById(id).map(sedeMapper::toDomain);
    }

    @Override
    public Page<Sede> buscarTodos(Pageable pageable) {
        return sedeRepository.findAll(pageable).map(sedeMapper::toDomain);
    }

    @Override
    public void eliminar(Long id) {
        sedeRepository.deleteById(id);
    }
}
