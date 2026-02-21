package com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.adapter;

import com.codigo.sanidaddivina.dao.CursoPersonaEntityRepository;
import com.codigo.sanidaddivina.domain.model.CursoInscripcion;
import com.codigo.sanidaddivina.domain.port.out.CursoInscripcionRepositoryPort;
import com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.mapper.CursoInscripcionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CursoInscripcionPersistenceAdapter implements CursoInscripcionRepositoryPort {

    private final CursoPersonaEntityRepository cursoPersonaRepository;
    private final CursoInscripcionMapper mapper;

    @Override
    public CursoInscripcion guardar(CursoInscripcion inscripcion) {
        return mapper.toDomain(cursoPersonaRepository.save(mapper.toEntity(inscripcion)));
    }

    @Override
    public Optional<CursoInscripcion> buscarPorId(Long id) {
        return cursoPersonaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Page<CursoInscripcion> buscarPorCurso(Long cursoId, Pageable pageable) {
        return cursoPersonaRepository.findByCursoIdCurso(cursoId, pageable).map(mapper::toDomain);
    }

    @Override
    public List<CursoInscripcion> buscarPorPersona(Long personaId) {
        return cursoPersonaRepository.findByPersonaIdPersona(personaId)
                .stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public boolean existeInscripcion(Long cursoId, Long personaId) {
        return cursoPersonaRepository.existsByCursoIdCursoAndPersonaIdPersona(cursoId, personaId);
    }

    @Override
    public void eliminar(Long id) {
        cursoPersonaRepository.deleteById(id);
    }
}
