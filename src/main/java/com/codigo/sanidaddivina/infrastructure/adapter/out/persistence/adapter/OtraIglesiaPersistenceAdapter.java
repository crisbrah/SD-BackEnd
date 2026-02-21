package com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.adapter;

import com.codigo.sanidaddivina.dao.OtraIglesiaRepository;
import com.codigo.sanidaddivina.domain.model.OtraIglesia;
import com.codigo.sanidaddivina.domain.port.out.OtraIglesiaRepositoryPort;
import com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.mapper.OtraIglesiaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OtraIglesiaPersistenceAdapter implements OtraIglesiaRepositoryPort {

    private final OtraIglesiaRepository otraIglesiaRepository;
    private final OtraIglesiaMapper otraIglesiaMapper;

    @Override
    public OtraIglesia guardar(OtraIglesia otraIglesia) {
        return otraIglesiaMapper.toDomain(otraIglesiaRepository.save(otraIglesiaMapper.toEntity(otraIglesia)));
    }

    @Override
    public Optional<OtraIglesia> buscarPorId(Long id) {
        return otraIglesiaRepository.findById(id).map(otraIglesiaMapper::toDomain);
    }

    @Override
    public Page<OtraIglesia> buscarTodos(Pageable pageable) {
        return otraIglesiaRepository.findAll(pageable).map(otraIglesiaMapper::toDomain);
    }

    @Override
    public void eliminar(Long id) {
        otraIglesiaRepository.deleteById(id);
    }
}
