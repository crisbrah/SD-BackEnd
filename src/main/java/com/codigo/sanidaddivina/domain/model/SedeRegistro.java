package com.codigo.sanidaddivina.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SedeRegistro {
    private Long id;
    private boolean estadoRegistro;
    private Timestamp fechaRegistro;
    private Long sedeId;
    private Long personaId;
}
