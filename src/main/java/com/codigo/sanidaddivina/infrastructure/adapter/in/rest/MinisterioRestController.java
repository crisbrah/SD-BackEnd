package com.codigo.sanidaddivina.infrastructure.adapter.in.rest;

import com.codigo.sanidaddivina.domain.model.Ministerio;
import com.codigo.sanidaddivina.domain.port.in.miembro.AsignarMinisterioUseCase;
import com.codigo.sanidaddivina.domain.port.out.MinisterioRepositoryPort;
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
@RequestMapping("/api/v1/ministerios")
@RequiredArgsConstructor
@Tag(name = "Ministerios", description = "Gestión de ministerios de iglesia")
public class MinisterioRestController {

    private final MinisterioRepositoryPort ministerioRepository;
    private final AsignarMinisterioUseCase asignarMinisterioUseCase;

    public record MinisterioRequest(@NotBlank String nombreMinisterio) {}

    public record AsignarMiembroRequest(
        @NotNull Long miembroId,
        Date fechaIngreso,
        Date fechaSalida
    ) {}

    @PostMapping
    @Operation(summary = "Crear ministerio")
    public ResponseEntity<ApiResponse<?>> crear(@Valid @RequestBody MinisterioRequest req) {
        Ministerio ministerio = Ministerio.builder()
                .nombreMinisterio(req.nombreMinisterio())
                .estadoMinisterio(true)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created(ministerioRepository.guardar(ministerio)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar ministerio por ID")
    public ResponseEntity<ApiResponse<?>> porId(@PathVariable Long id) {
        var ministerio = ministerioRepository.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ministerio no encontrado con ID: " + id));
        return ResponseEntity.ok(ApiResponse.ok(ministerio));
    }

    @GetMapping
    @Operation(summary = "Listar ministerios con paginación")
    public ResponseEntity<ApiResponse<?>> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ApiResponse.ok(
                ministerioRepository.buscarTodos(PageRequest.of(page, size))));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar ministerio")
    public ResponseEntity<ApiResponse<?>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody MinisterioRequest req) {
        Ministerio ministerio = ministerioRepository.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ministerio no encontrado con ID: " + id));
        ministerio.setNombreMinisterio(req.nombreMinisterio());
        return ResponseEntity.ok(ApiResponse.ok(ministerioRepository.guardar(ministerio)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar ministerio (solo SUPER_ADMIN)")
    public ResponseEntity<ApiResponse<?>> eliminar(@PathVariable Long id) {
        ministerioRepository.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ministerio no encontrado con ID: " + id));
        ministerioRepository.eliminar(id);
        return ResponseEntity.ok(ApiResponse.ok("Ministerio eliminado"));
    }

    @PostMapping("/{ministerioId}/miembros")
    @Operation(summary = "Asignar miembro a ministerio")
    public ResponseEntity<ApiResponse<?>> asignarMiembro(
            @PathVariable Long ministerioId,
            @Valid @RequestBody AsignarMiembroRequest req) {
        var asignacion = asignarMinisterioUseCase.asignar(new AsignarMinisterioUseCase.Command(
                req.miembroId(), ministerioId, req.fechaIngreso(), req.fechaSalida()));
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(asignacion));
    }

    @GetMapping("/{ministerioId}/miembros")
    @Operation(summary = "Listar miembros de un ministerio")
    public ResponseEntity<ApiResponse<?>> listarMiembros(
            @PathVariable Long ministerioId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ApiResponse.ok(
                asignarMinisterioUseCase.buscarPorMinisterio(ministerioId, PageRequest.of(page, size))));
    }

    @DeleteMapping("/{ministerioId}/miembros/{id}")
    @Operation(summary = "Quitar miembro de ministerio")
    public ResponseEntity<ApiResponse<?>> removerMiembro(
            @PathVariable Long ministerioId,
            @PathVariable Long id) {
        asignarMinisterioUseCase.remover(id);
        return ResponseEntity.ok(ApiResponse.ok("Miembro removido del ministerio"));
    }
}
