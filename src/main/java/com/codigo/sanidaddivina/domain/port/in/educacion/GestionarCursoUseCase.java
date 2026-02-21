package com.codigo.sanidaddivina.domain.port.in.educacion;

import com.codigo.sanidaddivina.domain.model.Curso;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public interface GestionarCursoUseCase {

    Curso crear(Command command);
    Curso actualizar(Long id, Command command);
    Optional<Curso> buscarPorId(Long id);
    Page<Curso> buscarTodos(Pageable pageable);
    Page<Curso> buscarPorEscuela(Long escuelaId, Pageable pageable);
    void eliminar(Long id);

    record Command(
        String nombreCurso,
        LocalDate fechaInicio,
        LocalDate fechaFin,
        boolean esPago,
        BigDecimal costo,
        Long escuelaId,
        Long profesorId
    ) {}
}
