package com.codigo.sanidaddivina.domain.port.in.asistencia;

import com.codigo.sanidaddivina.domain.model.Asistencia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface BuscarAsistenciaUseCase {
    Page<Asistencia> todas(Pageable pageable);
    List<Asistencia> porPersona(Long personaId);
    List<Asistencia> porFecha(LocalDate fecha);
    Page<Asistencia> porSesion(Long sesionId, Pageable pageable);
}
