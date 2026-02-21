package com.codigo.sanidaddivina.domain.port.in.educacion;

import com.codigo.sanidaddivina.domain.model.AsistenciaCursoPersona;
import com.codigo.sanidaddivina.domain.model.enums.TipoRegistroAsistencia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RegistrarAsistenciaCursoUseCase {
    AsistenciaCursoPersona registrar(Command command);
    Optional<AsistenciaCursoPersona> buscarPorId(Long id);
    Page<AsistenciaCursoPersona> buscarPorCurso(Long cursoId, Pageable pageable);
    Page<AsistenciaCursoPersona> buscarPorPersona(Long personaId, Pageable pageable);
    void eliminar(Long id);

    record Command(Long cursoId, Long personaId, TipoRegistroAsistencia tipoRegistro) {}
}
