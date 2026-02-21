package com.codigo.sanidaddivina.domain.port.in.miembro;

import com.codigo.sanidaddivina.domain.model.Miembro;

import java.time.LocalDate;

public interface RegistrarMiembroUseCase {
    Miembro ejecutar(Command command);

    record Command(
        Long personaId,
        String email,
        String password,
        LocalDate fechaConversion,
        boolean esNuevo,
        Long celulaId
    ) {}
}
