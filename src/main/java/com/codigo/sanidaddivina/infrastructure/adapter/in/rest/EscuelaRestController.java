package com.codigo.sanidaddivina.infrastructure.adapter.in.rest;

import com.codigo.sanidaddivina.domain.model.enums.FaseEscuela;
import com.codigo.sanidaddivina.domain.port.out.EscuelaRepositoryPort;
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

import com.codigo.sanidaddivina.domain.model.Escuela;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/escuelas")
@RequiredArgsConstructor
@Tag(name = "Escuelas", description = "Gestión de fases de la escuela cristiana")
public class EscuelaRestController {

    private final EscuelaRepositoryPort escuelaRepository;

    public record EscuelaRequest(
        @NotBlank String nombreEscuela,
        FaseEscuela fase
    ) {}

    @PostMapping
    @Operation(summary = "Crear escuela")
    public ResponseEntity<ApiResponse<?>> crear(@Valid @RequestBody EscuelaRequest req) {
        Escuela escuela = Escuela.builder()
                .nombreEscuela(req.nombreEscuela())
                .fase(req.fase())
                .fechaCreacion(LocalDate.now())
                .estadoEscuela(true)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created(escuelaRepository.guardar(escuela)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar escuela por ID")
    public ResponseEntity<ApiResponse<?>> porId(@PathVariable Long id) {
        var escuela = escuelaRepository.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Escuela no encontrada con ID: " + id));
        return ResponseEntity.ok(ApiResponse.ok(escuela));
    }

    @GetMapping
    @Operation(summary = "Listar escuelas con paginación")
    public ResponseEntity<ApiResponse<?>> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ApiResponse.ok(
                escuelaRepository.buscarTodos(PageRequest.of(page, size))));
    }

    @GetMapping("/fase/{fase}")
    @Operation(summary = "Buscar escuelas por fase")
    public ResponseEntity<ApiResponse<?>> porFase(@PathVariable FaseEscuela fase) {
        return ResponseEntity.ok(ApiResponse.ok(escuelaRepository.buscarPorFase(fase)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar escuela")
    public ResponseEntity<ApiResponse<?>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody EscuelaRequest req) {
        Escuela escuela = escuelaRepository.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Escuela no encontrada con ID: " + id));
        escuela.setNombreEscuela(req.nombreEscuela());
        escuela.setFase(req.fase());
        return ResponseEntity.ok(ApiResponse.ok(escuelaRepository.guardar(escuela)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar escuela (solo SUPER_ADMIN)")
    public ResponseEntity<ApiResponse<?>> eliminar(@PathVariable Long id) {
        escuelaRepository.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Escuela no encontrada con ID: " + id));
        escuelaRepository.eliminar(id);
        return ResponseEntity.ok(ApiResponse.ok("Escuela eliminada"));
    }
}
