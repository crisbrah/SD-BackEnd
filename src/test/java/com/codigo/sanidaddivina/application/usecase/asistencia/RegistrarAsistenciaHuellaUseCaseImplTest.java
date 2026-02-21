package com.codigo.sanidaddivina.application.usecase.asistencia;

import com.codigo.sanidaddivina.domain.model.Asistencia;
import com.codigo.sanidaddivina.domain.model.enums.TipoRegistroAsistencia;
import com.codigo.sanidaddivina.domain.port.out.AsistenciaRepositoryPort;
import com.codigo.sanidaddivina.domain.port.out.FingerprintServicePort;
import com.codigo.sanidaddivina.domain.port.out.PersonaRepositoryPort;
import com.codigo.sanidaddivina.infrastructure.exception.BusinessException;
import com.codigo.sanidaddivina.infrastructure.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistrarAsistenciaHuellaUseCaseImplTest {

    @Mock private FingerprintServicePort fingerprintService;
    @Mock private PersonaRepositoryPort personaRepository;
    @Mock private AsistenciaRepositoryPort asistenciaRepository;

    @InjectMocks
    private RegistrarAsistenciaHuellaUseCaseImpl useCase;

    private static final String TEMPLATE_B64 = "dGVzdC10ZW1wbGF0ZQ==";

    @Test
    void ejecutar_huellaReconocida_registraAsistenciaConHuella() {
        // given
        when(fingerprintService.identificarPorHuella(TEMPLATE_B64))
                .thenReturn(Optional.of("12345678"));
        when(personaRepository.buscarPorDni("12345678"))
                .thenReturn(Optional.of(
                        com.codigo.sanidaddivina.domain.model.Persona.builder()
                                .id(5L).dni("12345678").build()));
        when(asistenciaRepository.guardar(any())).thenAnswer(inv -> {
            Asistencia a = inv.getArgument(0);
            a.setId(1L);
            return a;
        });

        // when
        Asistencia resultado = useCase.ejecutar(TEMPLATE_B64);

        // then
        assertThat(resultado.getTipoRegistro()).isEqualTo(TipoRegistroAsistencia.HUELLA_DACTILAR);
        assertThat(resultado.getPersonaId()).isEqualTo(5L);
        verify(asistenciaRepository).guardar(any());
    }

    @Test
    void ejecutar_huellaNoReconocida_lanzaBusinessException() {
        // given
        when(fingerprintService.identificarPorHuella(TEMPLATE_B64))
                .thenReturn(Optional.empty());

        // when/then
        assertThatThrownBy(() -> useCase.ejecutar(TEMPLATE_B64))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("huella");

        verify(asistenciaRepository, never()).guardar(any());
    }

    @Test
    void ejecutar_huellaReconocidaPersonaNoExiste_lanzaResourceNotFoundException() {
        // given
        when(fingerprintService.identificarPorHuella(TEMPLATE_B64))
                .thenReturn(Optional.of("99999999"));
        when(personaRepository.buscarPorDni("99999999"))
                .thenReturn(Optional.empty());

        // when/then
        assertThatThrownBy(() -> useCase.ejecutar(TEMPLATE_B64))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(asistenciaRepository, never()).guardar(any());
    }
}
