package com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.adapter;

import com.codigo.sanidaddivina.dao.SedeRegistroRepository;
import com.codigo.sanidaddivina.domain.model.SedeRegistro;
import com.codigo.sanidaddivina.domain.port.out.SedeRegistroRepositoryPort;
import com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.mapper.SedeRegistroMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SedeRegistroPersistenceAdapter implements SedeRegistroRepositoryPort {

    private final SedeRegistroRepository sedeRegistroRepository;
    private final SedeRegistroMapper sedeRegistroMapper;

    @Override
    public SedeRegistro guardar(SedeRegistro sedeRegistro) {
        return sedeRegistroMapper.toDomain(sedeRegistroRepository.save(sedeRegistroMapper.toEntity(sedeRegistro)));
    }

    @Override
    public Optional<SedeRegistro> buscarPorId(Long id) {
        return sedeRegistroRepository.findById(id).map(sedeRegistroMapper::toDomain);
    }

    @Override
    public Page<SedeRegistro> buscarTodos(Pageable pageable) {
        return sedeRegistroRepository.findAll(pageable).map(sedeRegistroMapper::toDomain);
    }

    @Override
    public Page<SedeRegistro> buscarPorSede(Long sedeId, Pageable pageable) {
        return sedeRegistroRepository.findBySedeIdSede(sedeId, pageable).map(sedeRegistroMapper::toDomain);
    }

    @Override
    public void eliminar(Long id) {
        sedeRegistroRepository.deleteById(id);
    }
}
