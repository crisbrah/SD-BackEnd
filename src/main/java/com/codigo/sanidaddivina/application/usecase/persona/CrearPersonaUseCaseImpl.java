package com.codigo.sanidaddivina.application.usecase.persona;

import com.codigo.sanidaddivina.domain.model.Persona;
import com.codigo.sanidaddivina.domain.port.in.persona.CrearPersonaUseCase;
import com.codigo.sanidaddivina.domain.port.out.PersonaRepositoryPort;
import com.codigo.sanidaddivina.domain.port.out.ReniecServicePort;
import com.codigo.sanidaddivina.infrastructure.exception.DuplicateResourceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CrearPersonaUseCaseImpl implements CrearPersonaUseCase {

    private final PersonaRepositoryPort personaRepository;
    private final ReniecServicePort reniecService;

    @Override
    public Persona ejecutar(Command command) {
        if (personaRepository.existePorDni(command.dni())) {
            throw new DuplicateResourceException("Ya existe una persona registrada con DNI: " + command.dni());
        }

        Persona persona = new Persona();
        persona.setDni(command.dni());

        // Enriquecer desde RENIEC si está disponible
        reniecService.consultarPorDni(command.dni()).ifPresent(reniec -> {
            persona.setNombres(reniec.nombres());
            persona.setApellidoPaterno(reniec.apellidoPaterno());
            persona.setApellidoMaterno(reniec.apellidoMaterno());
        });

        persona.setCelular(command.celular());
        persona.setFechaNacimiento(command.fechaNacimiento());
        persona.setFechaBautizo(command.fechaBautizo());
        persona.setLugarNacimiento(command.lugarNacimiento());
        persona.setEstadoCivil(command.estadoCivil());
        persona.setNumeroHijos(command.numeroHijos() != null ? command.numeroHijos() : 0);
        persona.setOcupacion(command.ocupacion());
        persona.setDireccion(command.direccion());
        persona.setDistrito(command.distrito());
        persona.setProvincia(command.provincia());
        persona.setDepartamento(command.departamento());
        persona.setCargo(command.cargo());
        persona.setIglesiaProcedenciaId(command.iglesiaProcedenciaId());
        persona.setActivo(true);

        return personaRepository.guardar(persona);
    }
}
