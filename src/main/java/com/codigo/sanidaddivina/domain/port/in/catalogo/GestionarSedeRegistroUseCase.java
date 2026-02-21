package com.codigo.sanidaddivina.domain.port.in.catalogo;

import com.codigo.sanidaddivina.domain.model.SedeRegistro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface GestionarSedeRegistroUseCase {
    SedeRegistro crear(Command command);
    Optional<SedeRegistro> buscarPorId(Long id);
    Page<SedeRegistro> listarPorSede(Long sedeId, Pageable pageable);
    void eliminar(Long id);

    record Command(boolean estadoRegistro, Long sedeId, Long personaId) {}
}
