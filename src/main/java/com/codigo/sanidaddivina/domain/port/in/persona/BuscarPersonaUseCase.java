package com.codigo.sanidaddivina.domain.port.in.persona;

import com.codigo.sanidaddivina.domain.model.Persona;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BuscarPersonaUseCase {
    Optional<Persona> porId(Long id);
    Optional<Persona> porDni(String dni);
    Page<Persona> porNombre(String nombre, Pageable pageable);
    Page<Persona> todos(Pageable pageable);
}
