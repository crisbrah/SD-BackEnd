package com.codigo.sanidaddivina.domain.port.in.financiero;

import com.codigo.sanidaddivina.domain.model.Egreso;
import com.codigo.sanidaddivina.domain.model.enums.MetodoPago;
import com.codigo.sanidaddivina.domain.model.enums.TipoEgreso;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface RegistrarEgresoUseCase {
    Egreso ejecutar(Command command);

    record Command(
        String concepto,
        BigDecimal montoSalida,
        TipoEgreso tipoEgreso,
        MetodoPago metodoSalida,
        LocalDate fechaEgreso,
        Long personaId,
        Long miembroId
    ) {}
}
