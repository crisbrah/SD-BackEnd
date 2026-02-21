package com.codigo.sanidaddivina.application.usecase.asistencia;

import com.codigo.sanidaddivina.domain.model.Asistencia;
import com.codigo.sanidaddivina.domain.model.enums.TipoRegistroAsistencia;
import com.codigo.sanidaddivina.domain.port.in.asistencia.RegistrarAsistenciaPorDniUseCase;
import com.codigo.sanidaddivina.domain.port.out.AsistenciaRepositoryPort;
import com.codigo.sanidaddivina.domain.port.out.PersonaRepositoryPort;
import com.codigo.sanidaddivina.domain.port.out.SesionCultoRepositoryPort;
import com.codigo.sanidaddivina.infrastructure.exception.BusinessException;
import com.codigo.sanidaddivina.infrastructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class RegistrarAsistenciaPorDniUseCaseImpl implements RegistrarAsistenciaPorDniUseCase {

    private final AsistenciaRepositoryPort asistenciaRepository;
    private final PersonaRepositoryPort personaRepository;
    private final SesionCultoRepositoryPort sesionCultoRepository;

    @Override
    public Asistencia ejecutar(String dni) {
        Long personaId = personaRepository.buscarPorDni(dni)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se encontró persona con DNI: " + dni))
                .getId();

        Long sesionId = sesionCultoRepository.buscarSesionAbierta()
                .orElseThrow(() -> new BusinessException(
                        "No hay sesión de culto abierta. Abra una sesión antes de registrar asistencia."))
                .getId();

        Asistencia asistencia = new Asistencia();
        asistencia.setPersonaId(personaId);
        asistencia.setFechaAsistencia(LocalDateTime.now());
        asistencia.setTipoRegistro(TipoRegistroAsistencia.CODIGO_QR);
        asistencia.setSesionId(sesionId);

        return asistenciaRepository.guardar(asistencia);
    }
}
