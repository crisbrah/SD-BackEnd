package com.codigo.sanidaddivina.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class MiembroRequest {
    private LocalDate fechaConversion;
    private boolean estadoMiembro;
    private String email;
    private String password;
    private Long idPersona;
    private Long idCelula;
}
