package com.codigo.sanidaddivina.domain.port.out;

import com.codigo.sanidaddivina.domain.model.Curso;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CursoRepositoryPort {
    Curso guardar(Curso curso);
    Optional<Curso> buscarPorId(Long id);
    Page<Curso> buscarTodos(Pageable pageable);
    Page<Curso> buscarPorEscuela(Long escuelaId, Pageable pageable);
    void eliminar(Long id);
}
