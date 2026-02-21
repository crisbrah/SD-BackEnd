package com.codigo.sanidaddivina.domain.model;

import com.codigo.sanidaddivina.domain.model.enums.MetodoPago;
import com.codigo.sanidaddivina.domain.model.enums.TipoIngreso;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ingreso {
    private Long id;
    private String concepto;
    private BigDecimal monto;
    private TipoIngreso tipoIngreso;
    private MetodoPago metodoPago;
    private LocalDate fechaIngreso;
    private Long personaId;
    private Long miembroId;
}
