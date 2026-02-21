package com.codigo.sanidaddivina.application.usecase.educacion;

import com.codigo.sanidaddivina.domain.model.Curso;
import com.codigo.sanidaddivina.domain.model.CursoInscripcion;
import com.codigo.sanidaddivina.domain.model.enums.EstadoCursoPersona;
import com.codigo.sanidaddivina.domain.model.enums.MetodoPago;
import com.codigo.sanidaddivina.domain.port.in.educacion.MatricularPersonaCursoUseCase;
import com.codigo.sanidaddivina.domain.port.out.CursoInscripcionRepositoryPort;
import com.codigo.sanidaddivina.domain.port.out.CursoRepositoryPort;
import com.codigo.sanidaddivina.domain.port.out.IngresoRepositoryPort;
import com.codigo.sanidaddivina.infrastructure.exception.BusinessException;
import com.codigo.sanidaddivina.infrastructure.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MatricularPersonaCursoUseCaseImplTest {

    @Mock private CursoRepositoryPort cursoRepository;
    @Mock private CursoInscripcionRepositoryPort inscripcionRepository;
    @Mock private IngresoRepositoryPort ingresoRepository;

    @InjectMocks
    private MatricularPersonaCursoUseCaseImpl useCase;

    private final MatricularPersonaCursoUseCase.Command command =
            new MatricularPersonaCursoUseCase.Command(1L, 10L, MetodoPago.EFECTIVO);

    @Test
    void ejecutar_cursoGratuito_noGeneraIngreso() {
        // given
        Curso curso = Curso.builder().id(1L).nombreCurso("Consolidación 1")
                .esPago(false).costo(BigDecimal.ZERO).build();
        CursoInscripcion inscripcion = CursoInscripcion.builder()
                .id(1L).cursoId(1L).personaId(10L)
                .estadoInscripcion(EstadoCursoPersona.MATRICULADO).build();

        when(cursoRepository.buscarPorId(1L)).thenReturn(Optional.of(curso));
        when(inscripcionRepository.existeInscripcion(1L, 10L)).thenReturn(false);
        when(inscripcionRepository.guardar(any())).thenReturn(inscripcion);

        // when
        CursoInscripcion resultado = useCase.ejecutar(command);

        // then
        assertThat(resultado.getEstadoInscripcion()).isEqualTo(EstadoCursoPersona.MATRICULADO);
        verify(ingresoRepository, never()).guardar(any());
    }

    @Test
    void ejecutar_cursoConCosto_creaIngresoAutomaticamente() {
        // given
        Curso curso = Curso.builder().id(1L).nombreCurso("IBISEP")
                .esPago(true).costo(new BigDecimal("150.00")).build();
        CursoInscripcion inscripcion = CursoInscripcion.builder().id(1L).build();

        when(cursoRepository.buscarPorId(1L)).thenReturn(Optional.of(curso));
        when(inscripcionRepository.existeInscripcion(1L, 10L)).thenReturn(false);
        when(inscripcionRepository.guardar(any())).thenReturn(inscripcion);

        // when
        useCase.ejecutar(command);

        // then
        verify(ingresoRepository, times(1)).guardar(any());
    }

    @Test
    void ejecutar_personaYaInscrita_lanzaBusinessException() {
        // given
        Curso curso = Curso.builder().id(1L).esPago(false).build();
        when(cursoRepository.buscarPorId(1L)).thenReturn(Optional.of(curso));
        when(inscripcionRepository.existeInscripcion(1L, 10L)).thenReturn(true);

        // when/then
        assertThatThrownBy(() -> useCase.ejecutar(command))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("inscrita");

        verify(inscripcionRepository, never()).guardar(any());
    }

    @Test
    void ejecutar_cursoNoExiste_lanzaResourceNotFoundException() {
        // given
        when(cursoRepository.buscarPorId(1L)).thenReturn(Optional.empty());

        // when/then
        assertThatThrownBy(() -> useCase.ejecutar(command))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void ejecutar_cursoConCosto_ingresoTieneDatosCorrectos() {
        // given
        Curso curso = Curso.builder().id(1L).nombreCurso("IBISEP")
                .esPago(true).costo(new BigDecimal("200.00")).build();
        when(cursoRepository.buscarPorId(1L)).thenReturn(Optional.of(curso));
        when(inscripcionRepository.existeInscripcion(1L, 10L)).thenReturn(false);
        when(inscripcionRepository.guardar(any())).thenReturn(CursoInscripcion.builder().id(1L).build());

        var ingresoCaptor = ArgumentCaptor.forClass(com.codigo.sanidaddivina.domain.model.Ingreso.class);

        // when
        useCase.ejecutar(command);

        // then
        verify(ingresoRepository).guardar(ingresoCaptor.capture());
        assertThat(ingresoCaptor.getValue().getMonto()).isEqualByComparingTo("200.00");
        assertThat(ingresoCaptor.getValue().getConcepto()).contains("IBISEP");
        assertThat(ingresoCaptor.getValue().getPersonaId()).isEqualTo(10L);
    }
}
