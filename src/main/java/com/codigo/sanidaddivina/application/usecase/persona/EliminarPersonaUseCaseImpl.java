package com.codigo.sanidaddivina.application.usecase.persona;

import com.codigo.sanidaddivina.domain.port.in.persona.EliminarPersonaUseCase;
import com.codigo.sanidaddivina.domain.port.out.PersonaRepositoryPort;
import com.codigo.sanidaddivina.infrastructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class EliminarPersonaUseCaseImpl implements EliminarPersonaUseCase {

    private final PersonaRepositoryPort personaRepository;

    @Override
    public void ejecutar(Long id) {
        personaRepository.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada con ID: " + id));
        personaRepository.eliminar(id);
    }
}
