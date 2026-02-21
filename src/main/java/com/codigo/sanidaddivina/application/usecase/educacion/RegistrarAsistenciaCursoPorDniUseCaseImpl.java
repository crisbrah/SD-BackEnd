package com.codigo.sanidaddivina.application.usecase.educacion;

import com.codigo.sanidaddivina.domain.model.AsistenciaCursoPersona;
import com.codigo.sanidaddivina.domain.model.enums.TipoRegistroAsistencia;
import com.codigo.sanidaddivina.domain.port.in.educacion.RegistrarAsistenciaCursoPorDniUseCase;
import com.codigo.sanidaddivina.domain.port.in.educacion.RegistrarAsistenciaCursoUseCase;
import com.codigo.sanidaddivina.domain.port.out.PersonaRepositoryPort;
import com.codigo.sanidaddivina.infrastructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RegistrarAsistenciaCursoPorDniUseCaseImpl implements RegistrarAsistenciaCursoPorDniUseCase {

    private final PersonaRepositoryPort personaRepository;
    private final RegistrarAsistenciaCursoUseCase registrarAsistenciaCursoUseCase;

    @Override
    public AsistenciaCursoPersona ejecutar(Long cursoId, String dni) {
        Long personaId = personaRepository.buscarPorDni(dni)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se encontró persona con DNI: " + dni))
                .getId();

        return registrarAsistenciaCursoUseCase.registrar(
                new RegistrarAsistenciaCursoUseCase.Command(
                        cursoId, personaId, TipoRegistroAsistencia.CODIGO_QR));
    }
}
