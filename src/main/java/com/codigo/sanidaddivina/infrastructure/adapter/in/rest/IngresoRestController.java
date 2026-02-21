package com.codigo.sanidaddivina.infrastructure.adapter.in.rest;

import com.codigo.sanidaddivina.domain.model.enums.MetodoPago;
import com.codigo.sanidaddivina.domain.model.enums.TipoIngreso;
import com.codigo.sanidaddivina.domain.port.in.financiero.BuscarFinancieroUseCase;
import com.codigo.sanidaddivina.domain.port.in.financiero.RegistrarIngresoUseCase;
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

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/ingresos")
@RequiredArgsConstructor
@Tag(name = "Ingresos", description = "Gestión de ingresos financieros (diezmos, ofrendas, primicias)")
public class IngresoRestController {

    private final RegistrarIngresoUseCase registrarIngresoUseCase;
    private final BuscarFinancieroUseCase buscarFinancieroUseCase;

    public record RegistrarIngresoRequest(
        @NotBlank String concepto,
        @NotNull BigDecimal monto,
        @NotNull TipoIngreso tipoIngreso,
        @NotNull MetodoPago metodoPago,
        @NotNull LocalDate fechaIngreso,
        Long personaId,
        Long miembroId
    ) {}

    @PostMapping
    @Operation(summary = "Registrar ingreso (requiere TESORERO, ADMIN o SUPER_ADMIN)")
    public ResponseEntity<ApiResponse<?>> registrar(@Valid @RequestBody RegistrarIngresoRequest req) {
        var ingreso = registrarIngresoUseCase.ejecutar(new RegistrarIngresoUseCase.Command(
                req.concepto(), req.monto(), req.tipoIngreso(),
                req.metodoPago(), req.fechaIngreso(), req.personaId(), req.miembroId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(ingreso));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar ingreso por ID")
    public ResponseEntity<ApiResponse<?>> porId(@PathVariable Long id) {
        var ingreso = buscarFinancieroUseCase.ingresoPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingreso no encontrado con ID: " + id));
        return ResponseEntity.ok(ApiResponse.ok(ingreso));
    }

    @GetMapping
    @Operation(summary = "Listar todos los ingresos con paginación")
    public ResponseEntity<ApiResponse<?>> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ApiResponse.ok(
                buscarFinancieroUseCase.ingresosTodos(PageRequest.of(page, size))));
    }

    @GetMapping("/tipo/{tipo}")
    @Operation(summary = "Listar ingresos por tipo (DIEZMO, OFRENDA, PRIMICIA, OTRO)")
    public ResponseEntity<ApiResponse<?>> porTipo(
            @PathVariable TipoIngreso tipo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ApiResponse.ok(
                buscarFinancieroUseCase.ingresosPorTipo(tipo, PageRequest.of(page, size))));
    }

    @GetMapping("/mes")
    @Operation(summary = "Listar ingresos por mes y año")
    public ResponseEntity<ApiResponse<?>> porMes(
            @RequestParam int mes,
            @RequestParam int anio) {
        return ResponseEntity.ok(ApiResponse.ok(
                buscarFinancieroUseCase.ingresosPorMes(mes, anio)));
    }
}
