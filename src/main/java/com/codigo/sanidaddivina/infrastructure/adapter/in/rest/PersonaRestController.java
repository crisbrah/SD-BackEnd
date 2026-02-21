package com.codigo.sanidaddivina.infrastructure.adapter.in.rest;

import com.codigo.sanidaddivina.domain.model.enums.CargoIglesia;
import com.codigo.sanidaddivina.domain.port.in.persona.ActualizarPersonaUseCase;
import com.codigo.sanidaddivina.domain.port.in.persona.BuscarCumpleaniosUseCase;
import com.codigo.sanidaddivina.domain.port.in.persona.BuscarPersonaUseCase;
import com.codigo.sanidaddivina.domain.port.in.persona.CrearPersonaUseCase;
import com.codigo.sanidaddivina.domain.port.in.persona.EliminarPersonaUseCase;
import com.codigo.sanidaddivina.infrastructure.exception.ApiResponse;
import com.codigo.sanidaddivina.infrastructure.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@RequestMapping("/api/v1/personas")
@RequiredArgsConstructor
@Tag(name = "Personas", description = "Gestión de personas — registro, búsqueda y cumpleaños")
public class PersonaRestController {

    private final CrearPersonaUseCase crearPersonaUseCase;
    private final BuscarPersonaUseCase buscarPersonaUseCase;
    private final ActualizarPersonaUseCase actualizarPersonaUseCase;
    private final EliminarPersonaUseCase eliminarPersonaUseCase;
    private final BuscarCumpleaniosUseCase buscarCumpleaniosUseCase;

    public record CrearPersonaRequest(
        @NotBlank String dni,
        String celular,
        @NotNull LocalDate fechaNacimiento,
        LocalDate fechaBautizo,
        String lugarNacimiento,
        String estadoCivil,
        Integer numeroHijos,
        String ocupacion,
        String direccion,
        String distrito,
        String provincia,
        String departamento,
        CargoIglesia cargo,
        Long iglesiaProcedenciaId
    ) {}

    public record ActualizarPersonaRequest(
        LocalDate fechaNacimiento,
        String celular,
        LocalDate fechaBautizo,
        String lugarNacimiento,
        String estadoCivil,
        Integer numeroHijos,
        String ocupacion,
        String direccion,
        String distrito,
        String provincia,
        String departamento,
        CargoIglesia cargo,
        Long iglesiaProcedenciaId
    ) {}

    @PostMapping
    @Operation(summary = "Registrar nueva persona (enriquece con RENIEC)")
    public ResponseEntity<ApiResponse<?>> crear(@Valid @RequestBody CrearPersonaRequest req) {
        var persona = crearPersonaUseCase.ejecutar(new CrearPersonaUseCase.Command(
                req.dni(), req.celular(), req.fechaNacimiento(), req.fechaBautizo(),
                req.lugarNacimiento(), req.estadoCivil(), req.numeroHijos(),
                req.ocupacion(), req.direccion(), req.distrito(),
                req.provincia(), req.departamento(), req.cargo(), req.iglesiaProcedenciaId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(persona));
    }

    @GetMapping("/dni/{dni}")
    @Operation(summary = "Buscar persona por DNI")
    public ResponseEntity<ApiResponse<?>> porDni(@PathVariable String dni) {
        var persona = buscarPersonaUseCase.porDni(dni)
                .orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada con DNI: " + dni));
        return ResponseEntity.ok(ApiResponse.ok(persona));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar persona por ID")
    public ResponseEntity<ApiResponse<?>> porId(@PathVariable Long id) {
        var persona = buscarPersonaUseCase.porId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada con ID: " + id));
        return ResponseEntity.ok(ApiResponse.ok(persona));
    }

    @GetMapping
    @Operation(summary = "Listar personas con paginación y búsqueda por nombre")
    public ResponseEntity<ApiResponse<?>> listar(
            @RequestParam(required = false) String nombre,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "asc") String sort) {
        Sort sortObj = sort.equalsIgnoreCase("desc")
                ? Sort.by(Sort.Direction.DESC, "idPersona")
                : Sort.by(Sort.Direction.ASC, "idPersona");
        var pageable = PageRequest.of(page, size, sortObj);
        var result = (nombre != null && !nombre.isBlank())
                ? buscarPersonaUseCase.porNombre(nombre, pageable)
                : buscarPersonaUseCase.todos(pageable);
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar datos de persona")
    public ResponseEntity<ApiResponse<?>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ActualizarPersonaRequest req) {
        var persona = actualizarPersonaUseCase.ejecutar(id, new ActualizarPersonaUseCase.Command(
                req.fechaNacimiento(), req.celular(), req.fechaBautizo(), req.lugarNacimiento(),
                req.estadoCivil(), req.numeroHijos(), req.ocupacion(),
                req.direccion(), req.distrito(), req.provincia(),
                req.departamento(), req.cargo(), req.iglesiaProcedenciaId()));
        return ResponseEntity.ok(ApiResponse.ok(persona));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar persona (solo SUPER_ADMIN)")
    public ResponseEntity<ApiResponse<?>> eliminar(@PathVariable Long id) {
        eliminarPersonaUseCase.ejecutar(id);
        return ResponseEntity.ok(ApiResponse.ok("Persona eliminada"));
    }

    // --- Cumpleaños ---

    @GetMapping("/cumpleanios/hoy")
    @Operation(summary = "Personas con cumpleaños hoy")
    public ResponseEntity<ApiResponse<?>> hoy() {
        return ResponseEntity.ok(ApiResponse.ok(buscarCumpleaniosUseCase.hoy()));
    }

    @GetMapping("/cumpleanios/mes/{mes}")
    @Operation(summary = "Personas con cumpleaños en el mes indicado (1-12)")
    public ResponseEntity<ApiResponse<?>> porMes(@PathVariable int mes) {
        return ResponseEntity.ok(ApiResponse.ok(buscarCumpleaniosUseCase.porMes(mes)));
    }

    @GetMapping("/cumpleanios")
    @Operation(summary = "Personas con cumpleaños en los próximos N días")
    public ResponseEntity<ApiResponse<?>> proximos(@RequestParam(defaultValue = "7") int dias) {
        return ResponseEntity.ok(ApiResponse.ok(buscarCumpleaniosUseCase.proximos(dias)));
    }
}
