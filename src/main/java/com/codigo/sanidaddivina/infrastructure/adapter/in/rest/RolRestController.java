package com.codigo.sanidaddivina.infrastructure.adapter.in.rest;

import com.codigo.sanidaddivina.domain.port.in.seguridad.AsignarRolUseCase;
import com.codigo.sanidaddivina.domain.port.in.seguridad.GestionarRolUseCase;
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
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
@Tag(name = "Roles", description = "Gestión de roles y asignación a miembros")
public class RolRestController {

    private final GestionarRolUseCase gestionarRolUseCase;
    private final AsignarRolUseCase asignarRolUseCase;

    public record RolRequest(
        @NotBlank String nombreRol,
        boolean estadoRol
    ) {}

    public record AsignarRolRequest(
        @NotNull Long miembroId,
        @NotNull Long rolId,
        Date inicioRol,
        Date finRol
    ) {}

    @PostMapping
    @Operation(summary = "Crear rol")
    public ResponseEntity<ApiResponse<?>> crear(@Valid @RequestBody RolRequest req) {
        var rol = gestionarRolUseCase.crear(new GestionarRolUseCase.Command(
                req.nombreRol(), req.estadoRol()));
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(rol));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar rol por ID")
    public ResponseEntity<ApiResponse<?>> porId(@PathVariable Long id) {
        var rol = gestionarRolUseCase.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado con ID: " + id));
        return ResponseEntity.ok(ApiResponse.ok(rol));
    }

    @GetMapping
    @Operation(summary = "Listar roles con paginación")
    public ResponseEntity<ApiResponse<?>> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ApiResponse.ok(
                gestionarRolUseCase.listar(PageRequest.of(page, size))));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar rol")
    public ResponseEntity<ApiResponse<?>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody RolRequest req) {
        var rol = gestionarRolUseCase.actualizar(id, new GestionarRolUseCase.Command(
                req.nombreRol(), req.estadoRol()));
        return ResponseEntity.ok(ApiResponse.ok(rol));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar rol")
    public ResponseEntity<ApiResponse<?>> eliminar(@PathVariable Long id) {
        gestionarRolUseCase.eliminar(id);
        return ResponseEntity.ok(ApiResponse.ok("Rol eliminado"));
    }

    @PostMapping("/asignaciones")
    @Operation(summary = "Asignar rol a miembro")
    public ResponseEntity<ApiResponse<?>> asignar(@Valid @RequestBody AsignarRolRequest req) {
        var miembroRol = asignarRolUseCase.asignar(new AsignarRolUseCase.Command(
                req.miembroId(), req.rolId(), req.inicioRol(), req.finRol()));
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(miembroRol));
    }

    @GetMapping("/asignaciones/miembro/{miembroId}")
    @Operation(summary = "Listar roles de un miembro")
    public ResponseEntity<ApiResponse<?>> rolesPorMiembro(
            @PathVariable Long miembroId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ApiResponse.ok(
                asignarRolUseCase.buscarPorMiembro(miembroId, PageRequest.of(page, size))));
    }

    @DeleteMapping("/asignaciones/{id}")
    @Operation(summary = "Revocar rol de miembro")
    public ResponseEntity<ApiResponse<?>> revocar(@PathVariable Long id) {
        asignarRolUseCase.revocar(id);
        return ResponseEntity.ok(ApiResponse.ok("Rol revocado"));
    }
}
