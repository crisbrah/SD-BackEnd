package com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.adapter;

import com.codigo.sanidaddivina.dao.CelulaRepository;
import com.codigo.sanidaddivina.domain.model.Celula;
import com.codigo.sanidaddivina.domain.port.out.CelulaRepositoryPort;
import com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.mapper.CelulaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CelulaPersistenceAdapter implements CelulaRepositoryPort {

    private final CelulaRepository celulaRepository;
    private final CelulaMapper celulaMapper;

    @Override
    public Celula guardar(Celula celula) {
        return celulaMapper.toDomain(celulaRepository.save(celulaMapper.toEntity(celula)));
    }

    @Override
    public Optional<Celula> buscarPorId(Long id) {
        return celulaRepository.findById(id).map(celulaMapper::toDomain);
    }

    @Override
    public Page<Celula> buscarTodos(Pageable pageable) {
        return celulaRepository.findAll(pageable).map(celulaMapper::toDomain);
    }

    @Override
    public void eliminar(Long id) {
        celulaRepository.deleteById(id);
    }
}
