package com.codigo.sanidaddivina.domain.port.in.evento;

import com.codigo.sanidaddivina.domain.model.EventoCristiano;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface GestionarEventoUseCase {
    EventoCristiano crear(Command command);
    Optional<EventoCristiano> buscarPorId(Long id);
    Page<EventoCristiano> listar(Pageable pageable);
    EventoCristiano actualizar(Long id, Command command);
    void eliminar(Long id);

    record Command(String nombreEvento, boolean estadoEvento) {}
}
