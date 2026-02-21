package com.codigo.sanidaddivina.application.usecase.persona;

import com.codigo.sanidaddivina.domain.model.Persona;
import com.codigo.sanidaddivina.domain.port.in.persona.CrearPersonaUseCase;
import com.codigo.sanidaddivina.domain.port.out.PersonaRepositoryPort;
import com.codigo.sanidaddivina.domain.port.out.ReniecServicePort;
import com.codigo.sanidaddivina.infrastructure.exception.DuplicateResourceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CrearPersonaUseCaseImplTest {

    @Mock
    private PersonaRepositoryPort personaRepository;

    @Mock
    private ReniecServicePort reniecService;

    @InjectMocks
    private CrearPersonaUseCaseImpl crearPersonaUseCase;

    private CrearPersonaUseCase.Command command;

    @BeforeEach
    void setUp() {
        command = new CrearPersonaUseCase.Command(
                "12345678", "987654321", LocalDate.of(1990, 5, 15),
                null, "Lima", "Soltero", 0,
                "Ingeniero", "Av. Principal 123", "Miraflores",
                "Lima", "Lima", null, null);
    }

    @Test
    void ejecutar_conDniNuevo_creaPersonaExitosamente() {
        // given
        when(personaRepository.existePorDni("12345678")).thenReturn(false);
        when(reniecService.consultarPorDni("12345678")).thenReturn(Optional.empty());
        when(personaRepository.guardar(any())).thenAnswer(inv -> {
            Persona p = inv.getArgument(0);
            p.setId(1L);
            return p;
        });

        // when
        Persona resultado = crearPersonaUseCase.ejecutar(command);

        // then
        assertThat(resultado).isNotNull();
        assertThat(resultado.getDni()).isEqualTo("12345678");
        assertThat(resultado.isActivo()).isTrue();
        verify(personaRepository).guardar(any(Persona.class));
    }

    @Test
    void ejecutar_conDniDuplicado_lanzaDuplicateResourceException() {
        // given
        when(personaRepository.existePorDni("12345678")).thenReturn(true);

        // when/then
        assertThatThrownBy(() -> crearPersonaUseCase.ejecutar(command))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessageContaining("12345678");

        verify(personaRepository, never()).guardar(any());
    }

    @Test
    void ejecutar_conDatosReniec_enriqueceDatosPersona() {
        // given
        when(personaRepository.existePorDni("12345678")).thenReturn(false);

        var reniecData = new ReniecServicePort.ReniecData("Juan", "Pérez", "Rodríguez");

        when(reniecService.consultarPorDni("12345678")).thenReturn(Optional.of(reniecData));
        when(personaRepository.guardar(any())).thenAnswer(inv -> inv.getArgument(0));

        // when
        Persona resultado = crearPersonaUseCase.ejecutar(command);

        // then
        assertThat(resultado.getNombres()).isEqualTo("Juan");
        assertThat(resultado.getApellidoPaterno()).isEqualTo("Pérez");
        assertThat(resultado.getApellidoMaterno()).isEqualTo("Rodríguez");
    }
}
