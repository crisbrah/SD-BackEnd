package com.codigo.sanidaddivina.infrastructure.adapter.in.rest;

import com.codigo.sanidaddivina.domain.port.in.evento.GestionarEventoUseCase;
import com.codigo.sanidaddivina.domain.port.in.evento.RegistrarPersonaEventoUseCase;
import com.codigo.sanidaddivina.infrastructure.exception.ApiResponse;
import com.codigo.sanidaddivina.infrastructure.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/eventos")
@RequiredArgsConstructor
@Tag(name = "Eventos Cristianos", description = "Gestión de eventos y registro de participantes")
public class EventoCristianoRestController {

    private final GestionarEventoUseCase gestionarEventoUseCase;
    private final RegistrarPersonaEventoUseCase registrarPersonaEventoUseCase;

    public record EventoRequest(
        @NotBlank String nombreEvento,
        boolean estadoEvento
    ) {}

    public record PersonaEventoRequest(
        @NotNull Long personaId,
        Date fechaEvento
    ) {}

    @PostMapping
    @Operation(summary = "Crear evento cristiano")
    public ResponseEntity<ApiResponse<?>> crear(@Valid @RequestBody EventoRequest req) {
        var evento = gestionarEventoUseCase.crear(new GestionarEventoUseCase.Command(
                req.nombreEvento(), req.estadoEvento()));
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(evento));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar evento por ID")
    public ResponseEntity<ApiResponse<?>> porId(@PathVariable Long id) {
        var evento = gestionarEventoUseCase.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado con ID: " + id));
        return ResponseEntity.ok(ApiResponse.ok(evento));
    }

    @GetMapping
    @Operation(summary = "Listar eventos con paginación")
    public ResponseEntity<ApiResponse<?>> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ApiResponse.ok(
                gestionarEventoUseCase.listar(PageRequest.of(page, size))));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar evento")
    public ResponseEntity<ApiResponse<?>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody EventoRequest req) {
        var evento = gestionarEventoUseCase.actualizar(id, new GestionarEventoUseCase.Command(
                req.nombreEvento(), req.estadoEvento()));
        return ResponseEntity.ok(ApiResponse.ok(evento));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar evento")
    public ResponseEntity<ApiResponse<?>> eliminar(@PathVariable Long id) {
        gestionarEventoUseCase.eliminar(id);
        return ResponseEntity.ok(ApiResponse.ok("Evento eliminado"));
    }

    @PostMapping("/{eventoId}/personas")
    @Operation(summary = "Registrar persona en evento")
    public ResponseEntity<ApiResponse<?>> registrarPersona(
            @PathVariable Long eventoId,
            @Valid @RequestBody PersonaEventoRequest req) {
        var personaEvento = registrarPersonaEventoUseCase.registrar(
                new RegistrarPersonaEventoUseCase.Command(eventoId, req.personaId(), req.fechaEvento()));
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(personaEvento));
    }

    @GetMapping("/{eventoId}/personas")
    @Operation(summary = "Listar personas registradas en evento")
    public ResponseEntity<ApiResponse<?>> listarPersonas(
            @PathVariable Long eventoId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ApiResponse.ok(
                registrarPersonaEventoUseCase.buscarPorEvento(eventoId, PageRequest.of(page, size))));
    }

    @DeleteMapping("/{eventoId}/personas/{personaEventoId}")
    @Operation(summary = "Quitar persona del evento")
    public ResponseEntity<ApiResponse<?>> eliminarPersona(
            @PathVariable Long eventoId,
            @PathVariable Long personaEventoId) {
        registrarPersonaEventoUseCase.eliminar(personaEventoId);
        return ResponseEntity.ok(ApiResponse.ok("Persona removida del evento"));
    }
}
