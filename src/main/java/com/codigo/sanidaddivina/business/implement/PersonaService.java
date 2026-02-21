package com.codigo.sanidaddivina.business.implement;

import com.codigo.sanidaddivina.dto.PersonaDTO;
import com.codigo.sanidaddivina.request.PersonaRequest;

import java.util.List;
import java.util.Optional;

public interface PersonaService {
    PersonaDTO crearPersona(PersonaRequest personaRequest);
    Optional<PersonaDTO> buscarPersonaxId(Long id);
    List<PersonaDTO> buscarPersonaxIdDni(String dni);
    Optional<List<PersonaDTO>> buscarPersonaxNombre(String nombre);
    List<PersonaDTO> buscarPersonaTodos();
    PersonaDTO actualizarPersona(Long id, PersonaRequest personaRequest);
    PersonaDTO deletePersona(Long id);
}
