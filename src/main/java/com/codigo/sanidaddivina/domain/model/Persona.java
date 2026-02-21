package com.codigo.sanidaddivina.domain.model;

import com.codigo.sanidaddivina.domain.model.enums.CargoIglesia;
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
public class Persona {
    private Long id;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String dni;
    private LocalDate fechaNacimiento;
    private LocalDate fechaBautizo;
    private String celular;
    private String lugarNacimiento;
    private String estadoCivil;
    private Integer numeroHijos;
    private String ocupacion;
    private String direccion;
    private String distrito;
    private String provincia;
    private String departamento;
    private CargoIglesia cargo;
    private boolean activo;
    private Long iglesiaProcedenciaId;
    private Long miembroId;
    private String estadoMembresia;
}
