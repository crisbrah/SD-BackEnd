package com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.adapter;

import com.codigo.sanidaddivina.dao.SesionCultoRepository;
import com.codigo.sanidaddivina.domain.model.SesionCulto;
import com.codigo.sanidaddivina.domain.port.out.SesionCultoRepositoryPort;
import com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.mapper.SesionCultoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SesionCultoPersistenceAdapter implements SesionCultoRepositoryPort {

    private final SesionCultoRepository sesionCultoRepository;
    private final SesionCultoMapper sesionCultoMapper;

    @Override
    public SesionCulto guardar(SesionCulto sesion) {
        return sesionCultoMapper.toDomain(
                sesionCultoRepository.save(sesionCultoMapper.toEntity(sesion)));
    }

    @Override
    public Optional<SesionCulto> buscarPorId(Long id) {
        return sesionCultoRepository.findById(id).map(sesionCultoMapper::toDomain);
    }

    @Override
    public Optional<SesionCulto> buscarSesionAbierta() {
        return sesionCultoRepository.findFirstByAbiertaTrue().map(sesionCultoMapper::toDomain);
    }

    @Override
    public List<SesionCulto> listarSesionesAbiertas() {
        return sesionCultoRepository.findByAbiertaTrue()
                .stream().map(sesionCultoMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public Page<SesionCulto> buscarTodos(Pageable pageable) {
        return sesionCultoRepository.findAll(pageable).map(sesionCultoMapper::toDomain);
    }

    @Override
    public Page<SesionCulto> buscarPorSede(Long sedeId, Pageable pageable) {
        return sesionCultoRepository.findBySedeIdSede(sedeId, pageable)
                .map(sesionCultoMapper::toDomain);
    }

    @Override
    public void eliminar(Long id) {
        sesionCultoRepository.deleteById(id);
    }
}
