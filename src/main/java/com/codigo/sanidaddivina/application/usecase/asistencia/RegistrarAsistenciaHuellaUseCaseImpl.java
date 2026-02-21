package com.codigo.sanidaddivina.application.usecase.asistencia;

import com.codigo.sanidaddivina.domain.model.Asistencia;
import com.codigo.sanidaddivina.domain.model.enums.TipoRegistroAsistencia;
import com.codigo.sanidaddivina.domain.port.in.asistencia.RegistrarAsistenciaHuellaUseCase;
import com.codigo.sanidaddivina.domain.port.out.AsistenciaRepositoryPort;
import com.codigo.sanidaddivina.domain.port.out.FingerprintServicePort;
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
public class RegistrarAsistenciaHuellaUseCaseImpl implements RegistrarAsistenciaHuellaUseCase {

    private final AsistenciaRepositoryPort asistenciaRepository;
    private final PersonaRepositoryPort personaRepository;
    private final FingerprintServicePort fingerprintService;
    private final SesionCultoRepositoryPort sesionCultoRepository;

    @Override
    public Asistencia ejecutar(String fingerprintTemplateBase64) {
        String dni = fingerprintService.identificarPorHuella(fingerprintTemplateBase64)
                .orElseThrow(() -> new BusinessException(
                        "Huella dactilar no reconocida. Registre la asistencia manualmente."));

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
        asistencia.setTipoRegistro(TipoRegistroAsistencia.HUELLA_DACTILAR);
        asistencia.setSesionId(sesionId);

        return asistenciaRepository.guardar(asistencia);
    }
}
