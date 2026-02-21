package com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.adapter;

import com.codigo.sanidaddivina.dao.MinisterioRepository;
import com.codigo.sanidaddivina.domain.model.Ministerio;
import com.codigo.sanidaddivina.domain.port.out.MinisterioRepositoryPort;
import com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.mapper.MinisterioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MinisterioPersistenceAdapter implements MinisterioRepositoryPort {

    private final MinisterioRepository ministerioRepository;
    private final MinisterioMapper ministerioMapper;

    @Override
    public Ministerio guardar(Ministerio ministerio) {
        return ministerioMapper.toDomain(ministerioRepository.save(ministerioMapper.toEntity(ministerio)));
    }

    @Override
    public Optional<Ministerio> buscarPorId(Long id) {
        return ministerioRepository.findById(id).map(ministerioMapper::toDomain);
    }

    @Override
    public Page<Ministerio> buscarTodos(Pageable pageable) {
        return ministerioRepository.findAll(pageable).map(ministerioMapper::toDomain);
    }

    @Override
    public void eliminar(Long id) {
        ministerioRepository.deleteById(id);
    }
}
