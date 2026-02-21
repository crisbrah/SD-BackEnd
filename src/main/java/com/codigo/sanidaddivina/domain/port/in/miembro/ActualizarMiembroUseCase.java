package com.codigo.sanidaddivina.domain.port.in.miembro;

import com.codigo.sanidaddivina.domain.model.Miembro;

import java.time.LocalDate;

public interface ActualizarMiembroUseCase {
    Miembro ejecutar(Long id, Command command);

    record Command(
        String email,
        LocalDate fechaConversion,
        boolean esNuevo,
        Long celulaId
    ) {}
}
