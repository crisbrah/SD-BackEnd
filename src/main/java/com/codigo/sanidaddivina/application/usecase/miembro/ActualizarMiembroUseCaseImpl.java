package com.codigo.sanidaddivina.application.usecase.miembro;

import com.codigo.sanidaddivina.domain.model.Miembro;
import com.codigo.sanidaddivina.domain.port.in.miembro.ActualizarMiembroUseCase;
import com.codigo.sanidaddivina.domain.port.out.MiembroRepositoryPort;
import com.codigo.sanidaddivina.infrastructure.exception.DuplicateResourceException;
import com.codigo.sanidaddivina.infrastructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ActualizarMiembroUseCaseImpl implements ActualizarMiembroUseCase {

    private final MiembroRepositoryPort miembroRepository;

    @Override
    public Miembro ejecutar(Long id, Command command) {
        Miembro miembro = miembroRepository.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Miembro no encontrado con ID: " + id));

        if (!miembro.getEmail().equals(command.email())
                && miembroRepository.existePorEmail(command.email())) {
            throw new DuplicateResourceException("Ya existe un miembro con el email: " + command.email());
        }

        miembro.setEmail(command.email());
        miembro.setFechaConversion(command.fechaConversion());
        miembro.setEsNuevo(command.esNuevo());
        miembro.setCelulaId(command.celulaId());

        return miembroRepository.guardar(miembro);
    }
}
