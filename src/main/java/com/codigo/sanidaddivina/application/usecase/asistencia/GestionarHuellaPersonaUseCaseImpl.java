package com.codigo.sanidaddivina.application.usecase.asistencia;

import com.codigo.sanidaddivina.domain.model.HuellaPersona;
import com.codigo.sanidaddivina.domain.port.in.asistencia.GestionarHuellaPersonaUseCase;
import com.codigo.sanidaddivina.domain.port.out.HuellaPersonaRepositoryPort;
import com.codigo.sanidaddivina.domain.port.out.PersonaRepositoryPort;
import com.codigo.sanidaddivina.infrastructure.exception.BusinessException;
import com.codigo.sanidaddivina.infrastructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GestionarHuellaPersonaUseCaseImpl implements GestionarHuellaPersonaUseCase {

    private final HuellaPersonaRepositoryPort huellaRepository;
    private final PersonaRepositoryPort personaRepository;

    @Override
    public HuellaPersona registrar(Command command) {
        personaRepository.buscarPorId(command.personaId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Persona no encontrada con ID: " + command.personaId()));

        HuellaPersona huella = HuellaPersona.builder()
                .personaId(command.personaId())
                .templateHuella(command.templateHuella())
                .dedo(command.dedo())
                .activo(true)
                .fechaRegistro(LocalDateTime.now())
                .build();

        return huellaRepository.guardar(huella);
    }

    @Override
    public HuellaPersona desactivar(Long id) {
        HuellaPersona huella = huellaRepository.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Huella no encontrada con ID: " + id));

        if (!huella.isActivo()) {
            throw new BusinessException("La huella ya está desactivada.");
        }

        huella.setActivo(false);
        return huellaRepository.guardar(huella);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HuellaPersona> listarActivasPorPersona(Long personaId) {
        return huellaRepository.buscarActivasPorPersona(personaId);
    }

    @Override
    public void eliminar(Long id) {
        HuellaPersona huella = huellaRepository.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Huella no encontrada con ID: " + id));

        if (huella.isActivo()) {
            throw new BusinessException(
                    "No se puede eliminar una huella activa. Desactívela primero.");
        }

        huellaRepository.eliminar(id);
    }
}
