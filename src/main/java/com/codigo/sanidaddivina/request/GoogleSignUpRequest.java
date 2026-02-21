package com.codigo.sanidaddivina.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class GoogleSignUpRequest {

    /** ID Token de Google para re-verificar la identidad */
    @NotBlank
    private String idToken;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String nombres;

    @NotBlank
    private String apePat;

    @NotBlank
    private String apeMat;

    @NotBlank
    @Size(min = 8, max = 8)
    private String dni;

    @NotNull
    @Past
    private LocalDate fechaNacimiento;
}
