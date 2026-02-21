package com.codigo.sanidaddivina.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MiembroMinisterio {
    private Long id;
    private Date fechaIngresoMinisterio;
    private Date fechaSalidaMinisterio;
    private Long miembroId;
    private Long ministerioId;
}
