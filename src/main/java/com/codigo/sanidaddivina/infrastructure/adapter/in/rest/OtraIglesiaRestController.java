package com.codigo.sanidaddivina.infrastructure.adapter.in.rest;

import com.codigo.sanidaddivina.domain.port.in.catalogo.GestionarOtraIglesiaUseCase;
import com.codigo.sanidaddivina.infrastructure.exception.ApiResponse;
import com.codigo.sanidaddivina.infrastructure.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/otras-iglesias")
@RequiredArgsConstructor
@Tag(name = "Otras Iglesias", description = "Gestión de otras iglesias y denominaciones")
public class OtraIglesiaRestController {

    private final GestionarOtraIglesiaUseCase gestionarOtraIglesiaUseCase;

    public record OtraIglesiaRequest(
        @NotBlank String nombreIglesia,
        @NotBlank String denominacion,
        String direccion
    ) {}

    @PostMapping
    @Operation(summary = "Crear otra iglesia")
    public ResponseEntity<ApiResponse<?>> crear(@Valid @RequestBody OtraIglesiaRequest req) {
        var iglesia = gestionarOtraIglesiaUseCase.crear(new GestionarOtraIglesiaUseCase.Command(
                req.nombreIglesia(), req.direccion(), req.denominacion()));
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(iglesia));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar iglesia por ID")
    public ResponseEntity<ApiResponse<?>> porId(@PathVariable Long id) {
        var iglesia = gestionarOtraIglesiaUseCase.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Iglesia no encontrada con ID: " + id));
        return ResponseEntity.ok(ApiResponse.ok(iglesia));
    }

    @GetMapping
    @Operation(summary = "Listar iglesias con paginación")
    public ResponseEntity<ApiResponse<?>> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ApiResponse.ok(
                gestionarOtraIglesiaUseCase.listar(PageRequest.of(page, size))));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar iglesia")
    public ResponseEntity<ApiResponse<?>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody OtraIglesiaRequest req) {
        var iglesia = gestionarOtraIglesiaUseCase.actualizar(id, new GestionarOtraIglesiaUseCase.Command(
                req.nombreIglesia(), req.direccion(), req.denominacion()));
        return ResponseEntity.ok(ApiResponse.ok(iglesia));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar iglesia")
    public ResponseEntity<ApiResponse<?>> eliminar(@PathVariable Long id) {
        gestionarOtraIglesiaUseCase.eliminar(id);
        return ResponseEntity.ok(ApiResponse.ok("Iglesia eliminada"));
    }
}
