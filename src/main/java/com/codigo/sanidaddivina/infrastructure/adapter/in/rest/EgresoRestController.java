package com.codigo.sanidaddivina.infrastructure.adapter.in.rest;

import com.codigo.sanidaddivina.domain.model.enums.MetodoPago;
import com.codigo.sanidaddivina.domain.model.enums.TipoEgreso;
import com.codigo.sanidaddivina.domain.port.in.financiero.BuscarFinancieroUseCase;
import com.codigo.sanidaddivina.domain.port.in.financiero.RegistrarEgresoUseCase;
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
@RequestMapping("/api/v1/egresos")
@RequiredArgsConstructor
@Tag(name = "Egresos", description = "Gestión de egresos financieros (pagos, compras, créditos)")
public class EgresoRestController {

    private final RegistrarEgresoUseCase registrarEgresoUseCase;
    private final BuscarFinancieroUseCase buscarFinancieroUseCase;

    public record RegistrarEgresoRequest(
        @NotBlank String concepto,
        @NotNull BigDecimal montoSalida,
        @NotNull TipoEgreso tipoEgreso,
        @NotNull MetodoPago metodoSalida,
        @NotNull LocalDate fechaEgreso,
        Long personaId,
        Long miembroId
    ) {}

    @PostMapping
    @Operation(summary = "Registrar egreso (requiere TESORERO, ADMIN o SUPER_ADMIN)")
    public ResponseEntity<ApiResponse<?>> registrar(@Valid @RequestBody RegistrarEgresoRequest req) {
        var egreso = registrarEgresoUseCase.ejecutar(new RegistrarEgresoUseCase.Command(
                req.concepto(), req.montoSalida(), req.tipoEgreso(),
                req.metodoSalida(), req.fechaEgreso(), req.personaId(), req.miembroId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(egreso));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar egreso por ID")
    public ResponseEntity<ApiResponse<?>> porId(@PathVariable Long id) {
        var egreso = buscarFinancieroUseCase.egresoPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Egreso no encontrado con ID: " + id));
        return ResponseEntity.ok(ApiResponse.ok(egreso));
    }

    @GetMapping
    @Operation(summary = "Listar todos los egresos con paginación")
    public ResponseEntity<ApiResponse<?>> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ApiResponse.ok(
                buscarFinancieroUseCase.egresosTodos(PageRequest.of(page, size))));
    }

    @GetMapping("/tipo/{tipo}")
    @Operation(summary = "Listar egresos por tipo")
    public ResponseEntity<ApiResponse<?>> porTipo(
            @PathVariable TipoEgreso tipo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ApiResponse.ok(
                buscarFinancieroUseCase.egresosPorTipo(tipo, PageRequest.of(page, size))));
    }

    @GetMapping("/mes")
    @Operation(summary = "Listar egresos por mes y año")
    public ResponseEntity<ApiResponse<?>> porMes(
            @RequestParam int mes,
            @RequestParam int anio) {
        return ResponseEntity.ok(ApiResponse.ok(
                buscarFinancieroUseCase.egresosPorMes(mes, anio)));
    }
}
