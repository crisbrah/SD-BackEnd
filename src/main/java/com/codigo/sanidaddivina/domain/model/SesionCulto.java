package com.codigo.sanidaddivina.domain.model;

import com.codigo.sanidaddivina.domain.model.enums.TipoCulto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SesionCulto {
    private Long id;
    private String nombreSesion;
    private TipoCulto tipoCulto;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private boolean abierta;
    private Long sedeId;
}
