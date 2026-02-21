package com.codigo.sanidaddivina.application.usecase.catalogo;

import com.codigo.sanidaddivina.domain.model.SedeRegistro;
import com.codigo.sanidaddivina.domain.port.in.catalogo.GestionarSedeRegistroUseCase;
import com.codigo.sanidaddivina.domain.port.out.PersonaRepositoryPort;
import com.codigo.sanidaddivina.domain.port.out.SedeRegistroRepositoryPort;
import com.codigo.sanidaddivina.domain.port.out.SedeRepositoryPort;
import com.codigo.sanidaddivina.infrastructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class GestionarSedeRegistroUseCaseImpl implements GestionarSedeRegistroUseCase {

    private final SedeRegistroRepositoryPort sedeRegistroRepository;
    private final SedeRepositoryPort sedeRepository;
    private final PersonaRepositoryPort personaRepository;

    @Override
    public SedeRegistro crear(Command command) {
        sedeRepository.buscarPorId(command.sedeId())
                .orElseThrow(() -> new ResourceNotFoundException("Sede no encontrada con ID: " + command.sedeId()));
        personaRepository.buscarPorId(command.personaId())
                .orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada con ID: " + command.personaId()));

        SedeRegistro sedeRegistro = SedeRegistro.builder()
                .estadoRegistro(command.estadoRegistro())
                .fechaRegistro(Timestamp.from(Instant.now()))
                .sedeId(command.sedeId())
                .personaId(command.personaId())
                .build();
        return sedeRegistroRepository.guardar(sedeRegistro);
    }

    @Override
    public Optional<SedeRegistro> buscarPorId(Long id) {
        return sedeRegistroRepository.buscarPorId(id);
    }

    @Override
    public Page<SedeRegistro> listarPorSede(Long sedeId, Pageable pageable) {
        return sedeRegistroRepository.buscarPorSede(sedeId, pageable);
    }

    @Override
    public void eliminar(Long id) {
        sedeRegistroRepository.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("SedeRegistro no encontrado con ID: " + id));
        sedeRegistroRepository.eliminar(id);
    }
}
