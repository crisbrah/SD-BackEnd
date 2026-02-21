package com.codigo.sanidaddivina.application.usecase.educacion;

import com.codigo.sanidaddivina.domain.model.CursoInscripcion;
import com.codigo.sanidaddivina.domain.port.in.educacion.RegistrarNotaUseCase;
import com.codigo.sanidaddivina.domain.port.out.CursoInscripcionRepositoryPort;
import com.codigo.sanidaddivina.infrastructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RegistrarNotaUseCaseImpl implements RegistrarNotaUseCase {

    private final CursoInscripcionRepositoryPort inscripcionRepository;

    @Override
    public CursoInscripcion ejecutar(Long inscripcionId, Command command) {
        CursoInscripcion inscripcion = inscripcionRepository.buscarPorId(inscripcionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Inscripción no encontrada con ID: " + inscripcionId));

        inscripcion.setNota(command.nota());
        inscripcion.setEstadoInscripcion(command.estado());
        inscripcion.setFechaFin(command.fechaFin());

        return inscripcionRepository.guardar(inscripcion);
    }
}
