package com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.adapter;

import com.codigo.sanidaddivina.dao.AsistenciaCursoPersonaRepository;
import com.codigo.sanidaddivina.domain.model.AsistenciaCursoPersona;
import com.codigo.sanidaddivina.domain.port.out.AsistenciaCursoPersonaRepositoryPort;
import com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.mapper.AsistenciaCursoPersonaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AsistenciaCursoPersonaPersistenceAdapter implements AsistenciaCursoPersonaRepositoryPort {

    private final AsistenciaCursoPersonaRepository asistenciaCursoPersonaRepository;
    private final AsistenciaCursoPersonaMapper asistenciaCursoPersonaMapper;

    @Override
    public AsistenciaCursoPersona guardar(AsistenciaCursoPersona asistencia) {
        return asistenciaCursoPersonaMapper.toDomain(
                asistenciaCursoPersonaRepository.save(asistenciaCursoPersonaMapper.toEntity(asistencia)));
    }

    @Override
    public Optional<AsistenciaCursoPersona> buscarPorId(Long id) {
        return asistenciaCursoPersonaRepository.findById(id).map(asistenciaCursoPersonaMapper::toDomain);
    }

    @Override
    public Page<AsistenciaCursoPersona> buscarPorCurso(Long cursoId, Pageable pageable) {
        return asistenciaCursoPersonaRepository.findByCursoIdCurso(cursoId, pageable)
                .map(asistenciaCursoPersonaMapper::toDomain);
    }

    @Override
    public Page<AsistenciaCursoPersona> buscarPorPersona(Long personaId, Pageable pageable) {
        return asistenciaCursoPersonaRepository.findByPersonaIdPersona(personaId, pageable)
                .map(asistenciaCursoPersonaMapper::toDomain);
    }

    @Override
    public void eliminar(Long id) {
        asistenciaCursoPersonaRepository.deleteById(id);
    }
}
