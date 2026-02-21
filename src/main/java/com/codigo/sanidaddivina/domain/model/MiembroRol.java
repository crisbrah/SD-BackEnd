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
public class MiembroRol {
    private Long id;
    private Date inicioRol;
    private Date finRol;
    private Long miembroId;
    private Long rolId;
}
