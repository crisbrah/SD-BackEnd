package com.codigo.sanidaddivina.domain.port.out;

import com.codigo.sanidaddivina.domain.model.AsistenciaCursoPersona;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AsistenciaCursoPersonaRepositoryPort {
    AsistenciaCursoPersona guardar(AsistenciaCursoPersona asistencia);
    Optional<AsistenciaCursoPersona> buscarPorId(Long id);
    Page<AsistenciaCursoPersona> buscarPorCurso(Long cursoId, Pageable pageable);
    Page<AsistenciaCursoPersona> buscarPorPersona(Long personaId, Pageable pageable);
    void eliminar(Long id);
}
