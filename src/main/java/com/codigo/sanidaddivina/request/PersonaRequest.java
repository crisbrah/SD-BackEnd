package com.codigo.sanidaddivina.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PersonaRequest {
    private LocalDate fechaNacimiento;
    private String lugarNacimiento;
    private String esCivil;
    private int numHijos;
    private String dni;
    private String ocupacion;
    private String direccion;
    private String distrito;
    private String provincia;
    private String departamento;
}
