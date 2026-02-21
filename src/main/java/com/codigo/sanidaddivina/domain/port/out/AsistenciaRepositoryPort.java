package com.codigo.sanidaddivina.domain.port.out;

import com.codigo.sanidaddivina.domain.model.Asistencia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AsistenciaRepositoryPort {
    Asistencia guardar(Asistencia asistencia);
    Optional<Asistencia> buscarPorId(Long id);
    Page<Asistencia> buscarTodos(Pageable pageable);
    List<Asistencia> buscarPorPersona(Long personaId);
    List<Asistencia> buscarPorFecha(LocalDate fecha);
    Page<Asistencia> buscarPorSesion(Long sesionId, Pageable pageable);
    void eliminar(Long id);
}
