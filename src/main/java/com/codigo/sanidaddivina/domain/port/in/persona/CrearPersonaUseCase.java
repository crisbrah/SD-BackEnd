package com.codigo.sanidaddivina.domain.port.in.persona;

import com.codigo.sanidaddivina.domain.model.Persona;
import com.codigo.sanidaddivina.domain.model.enums.CargoIglesia;

import java.time.LocalDate;

public interface CrearPersonaUseCase {
    Persona ejecutar(Command command);

    record Command(
        String dni,
        String celular,
        LocalDate fechaNacimiento,
        LocalDate fechaBautizo,
        String lugarNacimiento,
        String estadoCivil,
        Integer numeroHijos,
        String ocupacion,
        String direccion,
        String distrito,
        String provincia,
        String departamento,
        CargoIglesia cargo,
        Long iglesiaProcedenciaId
    ) {}
}
