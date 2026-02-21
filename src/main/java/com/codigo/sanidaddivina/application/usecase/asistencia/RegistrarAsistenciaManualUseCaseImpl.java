package com.codigo.sanidaddivina.application.usecase.asistencia;

import com.codigo.sanidaddivina.domain.model.Asistencia;
import com.codigo.sanidaddivina.domain.model.enums.TipoRegistroAsistencia;
import com.codigo.sanidaddivina.domain.port.in.asistencia.RegistrarAsistenciaManualUseCase;
import com.codigo.sanidaddivina.domain.port.out.AsistenciaRepositoryPort;
import com.codigo.sanidaddivina.domain.port.out.PersonaRepositoryPort;
import com.codigo.sanidaddivina.infrastructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class RegistrarAsistenciaManualUseCaseImpl implements RegistrarAsistenciaManualUseCase {

    private final AsistenciaRepositoryPort asistenciaRepository;
    private final PersonaRepositoryPort personaRepository;

    @Override
    public Asistencia ejecutar(Command command) {
        personaRepository.buscarPorId(command.personaId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Persona no encontrada con ID: " + command.personaId()));

        Asistencia asistencia = new Asistencia();
        asistencia.setPersonaId(command.personaId());
        asistencia.setFechaAsistencia(LocalDateTime.now());
        asistencia.setTipoRegistro(TipoRegistroAsistencia.MANUAL);
        asistencia.setRegistradoPorId(command.registradoPorId());
        asistencia.setSesionId(command.sesionId());

        return asistenciaRepository.guardar(asistencia);
    }
}
