package com.codigo.sanidaddivina.domain.model;

import com.codigo.sanidaddivina.domain.model.enums.FaseEscuela;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Escuela {
    private Long id;
    private String nombreEscuela;
    private FaseEscuela fase;
    private LocalDate fechaCreacion;
    private boolean estadoEscuela;
}
