package com.codigo.sanidaddivina.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sede {
    private Long id;
    private String nombreSede;
    private String direccion;
    private String distrito;
    private String ciudad;
    private String provincia;
    private String pais;
}
