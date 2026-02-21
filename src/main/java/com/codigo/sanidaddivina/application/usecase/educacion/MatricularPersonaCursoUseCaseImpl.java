package com.codigo.sanidaddivina.application.usecase.educacion;

import com.codigo.sanidaddivina.domain.model.CursoInscripcion;
import com.codigo.sanidaddivina.domain.model.Ingreso;
import com.codigo.sanidaddivina.domain.model.enums.EstadoCursoPersona;
import com.codigo.sanidaddivina.domain.model.enums.TipoIngreso;
import com.codigo.sanidaddivina.domain.port.in.educacion.MatricularPersonaCursoUseCase;
import com.codigo.sanidaddivina.domain.port.out.CursoInscripcionRepositoryPort;
import com.codigo.sanidaddivina.domain.port.out.CursoRepositoryPort;
import com.codigo.sanidaddivina.domain.port.out.IngresoRepositoryPort;
import com.codigo.sanidaddivina.infrastructure.exception.BusinessException;
import com.codigo.sanidaddivina.infrastructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class MatricularPersonaCursoUseCaseImpl implements MatricularPersonaCursoUseCase {

    private final CursoRepositoryPort cursoRepository;
    private final CursoInscripcionRepositoryPort inscripcionRepository;
    private final IngresoRepositoryPort ingresoRepository;

    @Override
    public CursoInscripcion ejecutar(Command command) {
        var curso = cursoRepository.buscarPorId(command.cursoId())
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con ID: " + command.cursoId()));

        if (inscripcionRepository.existeInscripcion(command.cursoId(), command.personaId())) {
            throw new BusinessException("La persona ya está inscrita en este curso");
        }

        CursoInscripcion inscripcion = CursoInscripcion.builder()
                .cursoId(command.cursoId())
                .personaId(command.personaId())
                .fechaInicio(LocalDate.now())
                .estadoInscripcion(EstadoCursoPersona.MATRICULADO)
                .metodoPago(command.metodoPago())
                .build();

        CursoInscripcion guardada = inscripcionRepository.guardar(inscripcion);

        // Si el curso tiene costo, registrar ingreso automáticamente
        if (curso.isEsPago() && curso.getCosto() != null
                && curso.getCosto().compareTo(BigDecimal.ZERO) > 0) {
            Ingreso ingreso = Ingreso.builder()
                    .concepto("Matrícula: " + curso.getNombreCurso())
                    .monto(curso.getCosto())
                    .tipoIngreso(TipoIngreso.OTRO)
                    .metodoPago(command.metodoPago())
                    .fechaIngreso(LocalDate.now())
                    .personaId(command.personaId())
                    .build();
            ingresoRepository.guardar(ingreso);
        }

        return guardada;
    }
}
