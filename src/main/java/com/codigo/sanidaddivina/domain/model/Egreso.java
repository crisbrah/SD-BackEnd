package com.codigo.sanidaddivina.domain.model;

import com.codigo.sanidaddivina.domain.model.enums.MetodoPago;
import com.codigo.sanidaddivina.domain.model.enums.TipoEgreso;
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
public class Egreso {
    private Long id;
    private String concepto;
    private BigDecimal montoSalida;
    private TipoEgreso tipoEgreso;
    private MetodoPago metodoSalida;
    private LocalDate fechaEgreso;
    private Long personaId;
    private Long miembroId;
}
