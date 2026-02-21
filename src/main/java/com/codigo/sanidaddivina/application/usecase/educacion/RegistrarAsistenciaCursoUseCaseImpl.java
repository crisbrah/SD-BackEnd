package com.codigo.sanidaddivina.application.usecase.educacion;

import com.codigo.sanidaddivina.domain.model.AsistenciaCursoPersona;
import com.codigo.sanidaddivina.domain.port.in.educacion.RegistrarAsistenciaCursoUseCase;
import com.codigo.sanidaddivina.domain.port.out.AsistenciaCursoPersonaRepositoryPort;
import com.codigo.sanidaddivina.domain.port.out.CursoInscripcionRepositoryPort;
import com.codigo.sanidaddivina.domain.port.out.CursoRepositoryPort;
import com.codigo.sanidaddivina.domain.port.out.PersonaRepositoryPort;
import com.codigo.sanidaddivina.infrastructure.exception.BusinessException;
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
public class RegistrarAsistenciaCursoUseCaseImpl implements RegistrarAsistenciaCursoUseCase {

    private final AsistenciaCursoPersonaRepositoryPort asistenciaCursoRepository;
    private final CursoRepositoryPort cursoRepository;
    private final PersonaRepositoryPort personaRepository;
    private final CursoInscripcionRepositoryPort inscripcionRepository;

    @Override
    public AsistenciaCursoPersona registrar(Command command) {
        cursoRepository.buscarPorId(command.cursoId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Curso no encontrado con ID: " + command.cursoId()));
        personaRepository.buscarPorId(command.personaId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Persona no encontrada con ID: " + command.personaId()));

        if (!inscripcionRepository.existeInscripcion(command.cursoId(), command.personaId())) {
            throw new BusinessException(
                    "La persona no está inscrita en este curso. Matricúlela primero.");
        }

        AsistenciaCursoPersona asistencia = AsistenciaCursoPersona.builder()
                .cursoId(command.cursoId())
                .personaId(command.personaId())
                .fechaAsistenciaCurso(Timestamp.from(Instant.now()))
                .tipoRegistro(command.tipoRegistro())
                .build();
        return asistenciaCursoRepository.guardar(asistencia);
    }

    @Override
    public Optional<AsistenciaCursoPersona> buscarPorId(Long id) {
        return asistenciaCursoRepository.buscarPorId(id);
    }

    @Override
    public Page<AsistenciaCursoPersona> buscarPorCurso(Long cursoId, Pageable pageable) {
        return asistenciaCursoRepository.buscarPorCurso(cursoId, pageable);
    }

    @Override
    public Page<AsistenciaCursoPersona> buscarPorPersona(Long personaId, Pageable pageable) {
        return asistenciaCursoRepository.buscarPorPersona(personaId, pageable);
    }

    @Override
    public void eliminar(Long id) {
        asistenciaCursoRepository.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Asistencia no encontrada con ID: " + id));
        asistenciaCursoRepository.eliminar(id);
    }
}
