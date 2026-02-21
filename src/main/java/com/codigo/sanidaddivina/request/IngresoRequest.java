package com.codigo.sanidaddivina.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class IngresoRequest {
    private String concepto;
    private BigDecimal monto;
    private String metodoPago;
    private String idPersona;
    private String idMiembro;
}
