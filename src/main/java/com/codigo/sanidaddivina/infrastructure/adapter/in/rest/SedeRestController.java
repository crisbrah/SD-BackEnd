package com.codigo.sanidaddivina.infrastructure.adapter.in.rest;

import com.codigo.sanidaddivina.domain.port.in.catalogo.GestionarSedeRegistroUseCase;
import com.codigo.sanidaddivina.domain.port.in.catalogo.GestionarSedeUseCase;
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

@RestController
@RequestMapping("/api/v1/sedes")
@RequiredArgsConstructor
@Tag(name = "Sedes", description = "Gestión de sedes de iglesia y registros de asistentes")
public class SedeRestController {

    private final GestionarSedeUseCase gestionarSedeUseCase;
    private final GestionarSedeRegistroUseCase gestionarSedeRegistroUseCase;

    public record SedeRequest(
        @NotBlank String nombreSede,
        String direccion,
        String distrito,
        String ciudad,
        String pais
    ) {}

    public record SedeRegistroRequest(
        @NotNull Long personaId,
        boolean estadoRegistro
    ) {}

    @PostMapping
    @Operation(summary = "Crear sede")
    public ResponseEntity<ApiResponse<?>> crear(@Valid @RequestBody SedeRequest req) {
        var sede = gestionarSedeUseCase.crear(new GestionarSedeUseCase.Command(
                req.nombreSede(), req.direccion(), req.distrito(), req.ciudad(), req.pais()));
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(sede));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar sede por ID")
    public ResponseEntity<ApiResponse<?>> porId(@PathVariable Long id) {
        var sede = gestionarSedeUseCase.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sede no encontrada con ID: " + id));
        return ResponseEntity.ok(ApiResponse.ok(sede));
    }

    @GetMapping
    @Operation(summary = "Listar sedes con paginación")
    public ResponseEntity<ApiResponse<?>> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ApiResponse.ok(
                gestionarSedeUseCase.listar(PageRequest.of(page, size))));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar sede")
    public ResponseEntity<ApiResponse<?>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody SedeRequest req) {
        var sede = gestionarSedeUseCase.actualizar(id, new GestionarSedeUseCase.Command(
                req.nombreSede(), req.direccion(), req.distrito(), req.ciudad(), req.pais()));
        return ResponseEntity.ok(ApiResponse.ok(sede));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar sede")
    public ResponseEntity<ApiResponse<?>> eliminar(@PathVariable Long id) {
        gestionarSedeUseCase.eliminar(id);
        return ResponseEntity.ok(ApiResponse.ok("Sede eliminada"));
    }

    @PostMapping("/{sedeId}/registros")
    @Operation(summary = "Registrar persona en sede")
    public ResponseEntity<ApiResponse<?>> registrarPersona(
            @PathVariable Long sedeId,
            @Valid @RequestBody SedeRegistroRequest req) {
        var registro = gestionarSedeRegistroUseCase.crear(new GestionarSedeRegistroUseCase.Command(
                req.estadoRegistro(), sedeId, req.personaId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(registro));
    }

    @GetMapping("/{sedeId}/registros")
    @Operation(summary = "Listar registros de una sede")
    public ResponseEntity<ApiResponse<?>> listarRegistros(
            @PathVariable Long sedeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ApiResponse.ok(
                gestionarSedeRegistroUseCase.listarPorSede(sedeId, PageRequest.of(page, size))));
    }

    @DeleteMapping("/{sedeId}/registros/{registroId}")
    @Operation(summary = "Eliminar registro de sede")
    public ResponseEntity<ApiResponse<?>> eliminarRegistro(
            @PathVariable Long sedeId,
            @PathVariable Long registroId) {
        gestionarSedeRegistroUseCase.eliminar(registroId);
        return ResponseEntity.ok(ApiResponse.ok("Registro eliminado"));
    }
}
