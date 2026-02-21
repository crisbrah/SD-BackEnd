package com.codigo.sanidaddivina.application.usecase.evento;

import com.codigo.sanidaddivina.domain.model.EventoCristiano;
import com.codigo.sanidaddivina.domain.port.in.evento.GestionarEventoUseCase;
import com.codigo.sanidaddivina.domain.port.out.EventoCristianoRepositoryPort;
import com.codigo.sanidaddivina.infrastructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class GestionarEventoUseCaseImpl implements GestionarEventoUseCase {

    private final EventoCristianoRepositoryPort eventoCristianoRepository;

    @Override
    public EventoCristiano crear(Command command) {
        EventoCristiano evento = EventoCristiano.builder()
                .nombreEvento(command.nombreEvento())
                .estadoEvento(command.estadoEvento())
                .build();
        return eventoCristianoRepository.guardar(evento);
    }

    @Override
    public Optional<EventoCristiano> buscarPorId(Long id) {
        return eventoCristianoRepository.buscarPorId(id);
    }

    @Override
    public Page<EventoCristiano> listar(Pageable pageable) {
        return eventoCristianoRepository.buscarTodos(pageable);
    }

    @Override
    public EventoCristiano actualizar(Long id, Command command) {
        EventoCristiano evento = eventoCristianoRepository.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado con ID: " + id));
        evento.setNombreEvento(command.nombreEvento());
        evento.setEstadoEvento(command.estadoEvento());
        return eventoCristianoRepository.guardar(evento);
    }

    @Override
    public void eliminar(Long id) {
        eventoCristianoRepository.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado con ID: " + id));
        eventoCristianoRepository.eliminar(id);
    }
}
