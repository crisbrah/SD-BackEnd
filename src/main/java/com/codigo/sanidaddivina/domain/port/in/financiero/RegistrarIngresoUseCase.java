package com.codigo.sanidaddivina.domain.port.in.financiero;

import com.codigo.sanidaddivina.domain.model.Ingreso;
import com.codigo.sanidaddivina.domain.model.enums.MetodoPago;
import com.codigo.sanidaddivina.domain.model.enums.TipoIngreso;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface RegistrarIngresoUseCase {
    Ingreso ejecutar(Command command);

    record Command(
        String concepto,
        BigDecimal monto,
        TipoIngreso tipoIngreso,
        MetodoPago metodoPago,
        LocalDate fechaIngreso,
        Long personaId,
        Long miembroId
    ) {}
}
