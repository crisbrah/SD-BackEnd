package com.codigo.sanidaddivina.application.usecase.persona;

import com.codigo.sanidaddivina.domain.model.Persona;
import com.codigo.sanidaddivina.domain.port.in.persona.BuscarPersonaUseCase;
import com.codigo.sanidaddivina.domain.port.out.PersonaRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BuscarPersonaUseCaseImpl implements BuscarPersonaUseCase {

    private final PersonaRepositoryPort personaRepository;

    @Override
    public Optional<Persona> porId(Long id) {
        return personaRepository.buscarPorId(id);
    }

    @Override
    public Optional<Persona> porDni(String dni) {
        return personaRepository.buscarPorDni(dni);
    }

    @Override
    public Page<Persona> porNombre(String nombre, Pageable pageable) {
        return personaRepository.buscarPorNombre(nombre, pageable);
    }

    @Override
    public Page<Persona> todos(Pageable pageable) {
        return personaRepository.buscarTodos(pageable);
    }
}
