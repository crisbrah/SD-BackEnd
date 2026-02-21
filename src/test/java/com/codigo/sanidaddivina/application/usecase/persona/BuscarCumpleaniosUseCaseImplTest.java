package com.codigo.sanidaddivina.application.usecase.persona;

import com.codigo.sanidaddivina.domain.model.Persona;
import com.codigo.sanidaddivina.domain.port.out.PersonaRepositoryPort;
import com.codigo.sanidaddivina.infrastructure.exception.BusinessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuscarCumpleaniosUseCaseImplTest {

    @Mock private PersonaRepositoryPort personaRepository;

    @InjectMocks
    private BuscarCumpleaniosUseCaseImpl useCase;

    @Test
    void hoy_delegaAlRepositorio() {
        // given
        var persona = Persona.builder().id(1L).nombres("Ana").build();
        when(personaRepository.buscarCumpleaniosHoy()).thenReturn(List.of(persona));

        // when
        var resultado = useCase.hoy();

        // then
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNombres()).isEqualTo("Ana");
        verify(personaRepository).buscarCumpleaniosHoy();
    }

    @Test
    void porMes_mesValido_delegaAlRepositorio() {
        // given
        when(personaRepository.buscarCumpleaniosPorMes(5)).thenReturn(List.of());

        // when
        var resultado = useCase.porMes(5);

        // then
        assertThat(resultado).isEmpty();
        verify(personaRepository).buscarCumpleaniosPorMes(5);
    }

    @Test
    void porMes_mesInvalido_lanzaBusinessException() {
        assertThatThrownBy(() -> useCase.porMes(0))
                .isInstanceOf(BusinessException.class);
        assertThatThrownBy(() -> useCase.porMes(13))
                .isInstanceOf(BusinessException.class);

        verify(personaRepository, never()).buscarCumpleaniosPorMes(anyInt());
    }

    @Test
    void proximos_diasNegativo_lanzaBusinessException() {
        assertThatThrownBy(() -> useCase.proximos(-1))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    void proximos_diasValidos_delegaAlRepositorio() {
        // given
        when(personaRepository.buscarCumpleaniosProximos(7)).thenReturn(List.of());

        // when
        var resultado = useCase.proximos(7);

        // then
        assertThat(resultado).isEmpty();
        verify(personaRepository).buscarCumpleaniosProximos(7);
    }
}
