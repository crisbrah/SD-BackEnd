package com.codigo.sanidaddivina.application.usecase.evento;

import com.codigo.sanidaddivina.domain.model.PersonaEvento;
import com.codigo.sanidaddivina.domain.port.in.evento.RegistrarPersonaEventoUseCase;
import com.codigo.sanidaddivina.domain.port.out.EventoCristianoRepositoryPort;
import com.codigo.sanidaddivina.domain.port.out.PersonaEventoRepositoryPort;
import com.codigo.sanidaddivina.domain.port.out.PersonaRepositoryPort;
import com.codigo.sanidaddivina.infrastructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RegistrarPersonaEventoUseCaseImpl implements RegistrarPersonaEventoUseCase {

    private final PersonaEventoRepositoryPort personaEventoRepository;
    private final EventoCristianoRepositoryPort eventoCristianoRepository;
    private final PersonaRepositoryPort personaRepository;

    @Override
    public PersonaEvento registrar(Command command) {
        eventoCristianoRepository.buscarPorId(command.eventoId())
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado con ID: " + command.eventoId()));
        personaRepository.buscarPorId(command.personaId())
                .orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada con ID: " + command.personaId()));

        PersonaEvento personaEvento = PersonaEvento.builder()
                .eventoId(command.eventoId())
                .personaId(command.personaId())
                .fechaEvento(command.fechaEvento() != null ? command.fechaEvento() : new Date())
                .build();
        return personaEventoRepository.guardar(personaEvento);
    }

    @Override
    public Optional<PersonaEvento> buscarPorId(Long id) {
        return personaEventoRepository.buscarPorId(id);
    }

    @Override
    public Page<PersonaEvento> buscarPorEvento(Long eventoId, Pageable pageable) {
        return personaEventoRepository.buscarPorEvento(eventoId, pageable);
    }

    @Override
    public void eliminar(Long id) {
        personaEventoRepository.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro de persona en evento no encontrado con ID: " + id));
        personaEventoRepository.eliminar(id);
    }
}
