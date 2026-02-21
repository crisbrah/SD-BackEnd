package com.codigo.sanidaddivina.domain.port.out;

import com.codigo.sanidaddivina.domain.model.CursoInscripcion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CursoInscripcionRepositoryPort {
    CursoInscripcion guardar(CursoInscripcion inscripcion);
    Optional<CursoInscripcion> buscarPorId(Long id);
    Page<CursoInscripcion> buscarPorCurso(Long cursoId, Pageable pageable);
    List<CursoInscripcion> buscarPorPersona(Long personaId);
    boolean existeInscripcion(Long cursoId, Long personaId);
    void eliminar(Long id);
}
