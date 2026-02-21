package com.codigo.sanidaddivina.application.usecase.miembro;

import com.codigo.sanidaddivina.domain.model.Miembro;
import com.codigo.sanidaddivina.domain.model.Profesor;
import com.codigo.sanidaddivina.domain.port.out.MiembroRepositoryPort;
import com.codigo.sanidaddivina.domain.port.out.ProfesorRepositoryPort;
import com.codigo.sanidaddivina.infrastructure.exception.BusinessException;
import com.codigo.sanidaddivina.infrastructure.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AsignarProfesorUseCaseImplTest {

    @Mock private MiembroRepositoryPort miembroRepository;
    @Mock private ProfesorRepositoryPort profesorRepository;

    @InjectMocks
    private AsignarProfesorUseCaseImpl useCase;

    @Test
    void ejecutar_miembroExisteSinProfesor_creaProfesorActivo() {
        // given
        Miembro miembro = Miembro.builder().id(1L).email("juan@iglesia.com").build();
        when(miembroRepository.buscarPorId(1L)).thenReturn(Optional.of(miembro));
        when(profesorRepository.buscarPorMiembroId(1L)).thenReturn(Optional.empty());
        when(profesorRepository.guardar(any())).thenAnswer(inv -> {
            Profesor p = inv.getArgument(0);
            p.setId(10L);
            return p;
        });

        // when
        Profesor resultado = useCase.ejecutar(1L);

        // then
        assertThat(resultado.getId()).isEqualTo(10L);
        assertThat(resultado.isEstadoProfesor()).isTrue();
        assertThat(resultado.getFechaAsignacion()).isNotNull();

        var captor = ArgumentCaptor.forClass(Profesor.class);
        verify(profesorRepository).guardar(captor.capture());
        assertThat(captor.getValue().getMiembroId()).isEqualTo(1L);
    }

    @Test
    void ejecutar_miembroNoExiste_lanzaResourceNotFoundException() {
        // given
        when(miembroRepository.buscarPorId(99L)).thenReturn(Optional.empty());

        // when/then
        assertThatThrownBy(() -> useCase.ejecutar(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");

        verify(profesorRepository, never()).guardar(any());
    }

    @Test
    void ejecutar_miembroYaEsProfesor_lanzaBusinessException() {
        // given
        Miembro miembro = Miembro.builder().id(1L).build();
        Profesor existente = Profesor.builder().id(5L).miembroId(1L).build();

        when(miembroRepository.buscarPorId(1L)).thenReturn(Optional.of(miembro));
        when(profesorRepository.buscarPorMiembroId(1L)).thenReturn(Optional.of(existente));

        // when/then
        assertThatThrownBy(() -> useCase.ejecutar(1L))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("profesor");

        verify(profesorRepository, never()).guardar(any());
    }
}
