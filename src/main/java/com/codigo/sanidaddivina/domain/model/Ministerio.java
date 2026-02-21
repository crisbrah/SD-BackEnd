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
public class Ministerio {
    private Long id;
    private String nombreMinisterio;
    private boolean estadoMinisterio;
}
