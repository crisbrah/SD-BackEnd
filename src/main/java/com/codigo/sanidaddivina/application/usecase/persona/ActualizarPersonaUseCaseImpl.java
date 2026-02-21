package com.codigo.sanidaddivina.application.usecase.persona;

import com.codigo.sanidaddivina.domain.model.Persona;
import com.codigo.sanidaddivina.domain.port.in.persona.ActualizarPersonaUseCase;
import com.codigo.sanidaddivina.domain.port.out.PersonaRepositoryPort;
import com.codigo.sanidaddivina.infrastructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ActualizarPersonaUseCaseImpl implements ActualizarPersonaUseCase {

    private final PersonaRepositoryPort personaRepository;

    @Override
    public Persona ejecutar(Long id, Command command) {
        Persona persona = personaRepository.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada con ID: " + id));

        if (command.fechaNacimiento() != null) {
            persona.setFechaNacimiento(command.fechaNacimiento());
        }
        persona.setCelular(command.celular());
        persona.setFechaBautizo(command.fechaBautizo());
        persona.setLugarNacimiento(command.lugarNacimiento());
        persona.setEstadoCivil(command.estadoCivil());
        persona.setNumeroHijos(command.numeroHijos());
        persona.setOcupacion(command.ocupacion());
        persona.setDireccion(command.direccion());
        persona.setDistrito(command.distrito());
        persona.setProvincia(command.provincia());
        persona.setDepartamento(command.departamento());
        persona.setCargo(command.cargo());
        persona.setIglesiaProcedenciaId(command.iglesiaProcedenciaId());

        return personaRepository.guardar(persona);
    }
}
