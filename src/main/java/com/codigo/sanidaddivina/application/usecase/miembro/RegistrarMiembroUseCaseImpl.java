package com.codigo.sanidaddivina.application.usecase.miembro;

import com.codigo.sanidaddivina.domain.model.Miembro;
import com.codigo.sanidaddivina.domain.port.in.miembro.RegistrarMiembroUseCase;
import com.codigo.sanidaddivina.domain.port.out.MiembroRepositoryPort;
import com.codigo.sanidaddivina.domain.port.out.PersonaRepositoryPort;
import com.codigo.sanidaddivina.infrastructure.exception.BusinessException;
import com.codigo.sanidaddivina.infrastructure.exception.DuplicateResourceException;
import com.codigo.sanidaddivina.infrastructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RegistrarMiembroUseCaseImpl implements RegistrarMiembroUseCase {

    private final MiembroRepositoryPort miembroRepository;
    private final PersonaRepositoryPort personaRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Miembro ejecutar(Command command) {
        personaRepository.buscarPorId(command.personaId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Persona no encontrada con ID: " + command.personaId()));

        if (miembroRepository.existePorEmail(command.email())) {
            throw new DuplicateResourceException("Ya existe un miembro con el email: " + command.email());
        }

        if (miembroRepository.buscarPorPersonaId(command.personaId()).isPresent()) {
            throw new BusinessException("La persona ya está registrada como miembro");
        }

        Miembro miembro = Miembro.builder()
                .personaId(command.personaId())
                .email(command.email())
                .fechaConversion(command.fechaConversion())
                .estadoMiembro(true)
                .esNuevo(command.esNuevo())
                .celulaId(command.celulaId())
                .roles(List.of("MIEMBRO"))
                .build();

        // Password is encoded here at application layer boundary
        String encodedPassword = passwordEncoder.encode(command.password());
        miembro.setPassword(encodedPassword);

        return miembroRepository.guardar(miembro);
    }
}
