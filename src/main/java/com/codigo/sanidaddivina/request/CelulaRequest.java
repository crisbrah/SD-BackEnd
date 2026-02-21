package com.codigo.sanidaddivina.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CelulaRequest {
    @NotBlank(message = "El nombre de la célula es obligatorio")
    @Size(min=3, max=50)
    private String nombreCelula;
}
