package com.codigo.sanidaddivina.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Miembro {
    private Long id;
    private String email;
    private String password;
    private LocalDate fechaConversion;
    private boolean estadoMiembro;
    private boolean esNuevo;
    private Long personaId;
    private Long celulaId;
    private List<String> roles;
}
