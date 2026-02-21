package com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.adapter;

import com.codigo.sanidaddivina.dao.EscuelaRepository;
import com.codigo.sanidaddivina.domain.model.Escuela;
import com.codigo.sanidaddivina.domain.model.enums.FaseEscuela;
import com.codigo.sanidaddivina.domain.port.out.EscuelaRepositoryPort;
import com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.mapper.EscuelaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EscuelaPersistenceAdapter implements EscuelaRepositoryPort {

    private final EscuelaRepository escuelaRepository;
    private final EscuelaMapper escuelaMapper;

    @Override
    public Escuela guardar(Escuela escuela) {
        return escuelaMapper.toDomain(escuelaRepository.save(escuelaMapper.toEntity(escuela)));
    }

    @Override
    public Optional<Escuela> buscarPorId(Long id) {
        return escuelaRepository.findById(id).map(escuelaMapper::toDomain);
    }

    @Override
    public Page<Escuela> buscarTodos(Pageable pageable) {
        return escuelaRepository.findAll(pageable).map(escuelaMapper::toDomain);
    }

    @Override
    public List<Escuela> buscarPorFase(FaseEscuela fase) {
        return escuelaRepository.findByFase(fase)
                .stream().map(escuelaMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public void eliminar(Long id) {
        escuelaRepository.deleteById(id);
    }
}
