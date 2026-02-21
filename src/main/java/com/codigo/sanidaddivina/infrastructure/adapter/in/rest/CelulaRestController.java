package com.codigo.sanidaddivina.infrastructure.adapter.in.rest;

import com.codigo.sanidaddivina.domain.model.Celula;
import com.codigo.sanidaddivina.domain.model.enums.TipoCelula;
import com.codigo.sanidaddivina.domain.port.out.CelulaRepositoryPort;
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
@RequestMapping("/api/v1/celulas")
@RequiredArgsConstructor
@Tag(name = "Células", description = "Gestión de células de iglesia (Damas, Matrimonio, Jóvenes, Adolescentes)")
public class CelulaRestController {

    private final CelulaRepositoryPort celulaRepository;

    public record CelulaRequest(
        @NotBlank String nombreCelula,
        TipoCelula tipoCelula
    ) {}

    @PostMapping
    @Operation(summary = "Crear célula")
    public ResponseEntity<ApiResponse<?>> crear(@Valid @RequestBody CelulaRequest req) {
        Celula celula = Celula.builder()
                .nombreCelula(req.nombreCelula())
                .tipoCelula(req.tipoCelula())
                .estadoCelula(true)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created(celulaRepository.guardar(celula)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar célula por ID")
    public ResponseEntity<ApiResponse<?>> porId(@PathVariable Long id) {
        var celula = celulaRepository.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Célula no encontrada con ID: " + id));
        return ResponseEntity.ok(ApiResponse.ok(celula));
    }

    @GetMapping
    @Operation(summary = "Listar células con paginación")
    public ResponseEntity<ApiResponse<?>> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ApiResponse.ok(
                celulaRepository.buscarTodos(PageRequest.of(page, size))));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar célula")
    public ResponseEntity<ApiResponse<?>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody CelulaRequest req) {
        Celula celula = celulaRepository.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Célula no encontrada con ID: " + id));
        celula.setNombreCelula(req.nombreCelula());
        celula.setTipoCelula(req.tipoCelula());
        return ResponseEntity.ok(ApiResponse.ok(celulaRepository.guardar(celula)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar célula (solo SUPER_ADMIN)")
    public ResponseEntity<ApiResponse<?>> eliminar(@PathVariable Long id) {
        celulaRepository.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Célula no encontrada con ID: " + id));
        celulaRepository.eliminar(id);
        return ResponseEntity.ok(ApiResponse.ok("Célula eliminada"));
    }
}
