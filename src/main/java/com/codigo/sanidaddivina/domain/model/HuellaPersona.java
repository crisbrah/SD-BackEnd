package com.codigo.sanidaddivina.domain.model;

import com.codigo.sanidaddivina.domain.model.enums.DedoHuella;
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
public class HuellaPersona {
    private Long id;
    private byte[] templateHuella;
    private DedoHuella dedo;
    private boolean activo;
    private LocalDateTime fechaRegistro;
    private Long personaId;
}
