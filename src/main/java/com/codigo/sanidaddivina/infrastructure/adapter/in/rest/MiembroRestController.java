package com.codigo.sanidaddivina.infrastructure.adapter.in.rest;

import com.codigo.sanidaddivina.domain.port.in.miembro.ActualizarMiembroUseCase;
import com.codigo.sanidaddivina.domain.port.in.miembro.AsignarProfesorUseCase;
import com.codigo.sanidaddivina.domain.port.in.miembro.BuscarMiembroUseCase;
import com.codigo.sanidaddivina.domain.port.in.miembro.EliminarMiembroUseCase;
import com.codigo.sanidaddivina.domain.port.in.miembro.RegistrarMiembroUseCase;
import com.codigo.sanidaddivina.infrastructure.exception.ApiResponse;
import com.codigo.sanidaddivina.infrastructure.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/miembros")
@RequiredArgsConstructor
@Tag(name = "Miembros", description = "Gestión de miembros de iglesia")
public class MiembroRestController {

    private final RegistrarMiembroUseCase registrarMiembroUseCase;
    private final BuscarMiembroUseCase buscarMiembroUseCase;
    private final ActualizarMiembroUseCase actualizarMiembroUseCase;
    private final EliminarMiembroUseCase eliminarMiembroUseCase;
    private final AsignarProfesorUseCase asignarProfesorUseCase;

    public record RegistrarMiembroRequest(
        @NotNull Long personaId,
        @NotBlank @Email String email,
        @NotBlank String password,
        @NotNull LocalDate fechaConversion,
        boolean esNuevo,
        Long celulaId
    ) {}

    public record ActualizarMiembroRequest(
        @NotBlank @Email String email,
        LocalDate fechaConversion,
        boolean esNuevo,
        Long celulaId
    ) {}

    @PostMapping
    @Operation(summary = "Registrar nuevo miembro")
    public ResponseEntity<ApiResponse<?>> registrar(@Valid @RequestBody RegistrarMiembroRequest req) {
        var miembro = registrarMiembroUseCase.ejecutar(new RegistrarMiembroUseCase.Command(
                req.personaId(), req.email(), req.password(),
                req.fechaConversion(), req.esNuevo(), req.celulaId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(miembro));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar miembro por ID")
    public ResponseEntity<ApiResponse<?>> porId(@PathVariable Long id) {
        var miembro = buscarMiembroUseCase.porId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Miembro no encontrado con ID: " + id));
        return ResponseEntity.ok(ApiResponse.ok(miembro));
    }

    @GetMapping
    @Operation(summary = "Listar todos los miembros con paginación")
    public ResponseEntity<ApiResponse<?>> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "idMiembro"));
        return ResponseEntity.ok(ApiResponse.ok(buscarMiembroUseCase.todos(pageable)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar datos de miembro")
    public ResponseEntity<ApiResponse<?>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ActualizarMiembroRequest req) {
        var miembro = actualizarMiembroUseCase.ejecutar(id, new ActualizarMiembroUseCase.Command(
                req.email(), req.fechaConversion(), req.esNuevo(), req.celulaId()));
        return ResponseEntity.ok(ApiResponse.ok(miembro));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar miembro (solo SUPER_ADMIN)")
    public ResponseEntity<ApiResponse<?>> eliminar(@PathVariable Long id) {
        eliminarMiembroUseCase.ejecutar(id);
        return ResponseEntity.ok(ApiResponse.ok("Miembro eliminado"));
    }

    @PostMapping("/{miembroId}/profesor")
    @Operation(summary = "Designar miembro como profesor")
    public ResponseEntity<ApiResponse<?>> asignarProfesor(@PathVariable Long miembroId) {
        var profesor = asignarProfesorUseCase.ejecutar(miembroId);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(profesor));
    }
}
