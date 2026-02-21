package com.codigo.sanidaddivina.infrastructure.adapter.in.rest;

import com.codigo.sanidaddivina.domain.model.enums.TipoCulto;
import com.codigo.sanidaddivina.domain.port.in.asistencia.GestionarSesionCultoUseCase;
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
@RequestMapping("/api/v1/sesiones-culto")
@RequiredArgsConstructor
@Tag(name = "Sesiones de Culto", description = "Gestión de sesiones de culto para registro de asistencia")
public class SesionCultoRestController {

    private final GestionarSesionCultoUseCase gestionarSesionCultoUseCase;

    public record AbrirSesionRequest(
            @NotBlank String nombreSesion,
            @NotNull TipoCulto tipoCulto,
            Long sedeId
    ) {}

    // ── CRUD ──────────────────────────────────────────────────────────────────

    @PostMapping
    @Operation(summary = "Abrir nueva sesión de culto (requiere ENCARGADO, ADMIN o SUPER_ADMIN)")
    public ResponseEntity<ApiResponse<?>> abrir(@Valid @RequestBody AbrirSesionRequest req) {
        var sesion = gestionarSesionCultoUseCase.abrir(
                new GestionarSesionCultoUseCase.Command(
                        req.nombreSesion(), req.tipoCulto(), req.sedeId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(sesion));
    }

    @PutMapping("/{id}/cerrar")
    @Operation(summary = "Cerrar sesión de culto (registra fecha_fin)")
    public ResponseEntity<ApiResponse<?>> cerrar(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(gestionarSesionCultoUseCase.cerrar(id)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar sesión por ID")
    public ResponseEntity<ApiResponse<?>> porId(@PathVariable Long id) {
        var sesion = gestionarSesionCultoUseCase.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Sesión de culto no encontrada con ID: " + id));
        return ResponseEntity.ok(ApiResponse.ok(sesion));
    }

    @GetMapping("/abierta")
    @Operation(summary = "Obtener la sesión actualmente abierta")
    public ResponseEntity<ApiResponse<?>> sesionAbierta() {
        var sesion = gestionarSesionCultoUseCase.buscarSesionAbierta()
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No hay ninguna sesión de culto abierta en este momento."));
        return ResponseEntity.ok(ApiResponse.ok(sesion));
    }

    @GetMapping
    @Operation(summary = "Listar todas las sesiones con paginación")
    public ResponseEntity<ApiResponse<?>> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) Long sedeId) {
        var pageable = PageRequest.of(page, size);
        var result = (sedeId != null)
                ? gestionarSesionCultoUseCase.buscarPorSede(sedeId, pageable)
                : gestionarSesionCultoUseCase.buscarTodos(pageable);
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar sesión cerrada (solo SUPER_ADMIN)")
    public ResponseEntity<ApiResponse<?>> eliminar(@PathVariable Long id) {
        gestionarSesionCultoUseCase.eliminar(id);
        return ResponseEntity.ok(ApiResponse.ok("Sesión eliminada"));
    }
}
