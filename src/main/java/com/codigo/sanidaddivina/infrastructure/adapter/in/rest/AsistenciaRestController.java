package com.codigo.sanidaddivina.infrastructure.adapter.in.rest;

import com.codigo.sanidaddivina.domain.port.in.asistencia.BuscarAsistenciaUseCase;
import com.codigo.sanidaddivina.domain.port.in.asistencia.RegistrarAsistenciaHuellaUseCase;
import com.codigo.sanidaddivina.domain.port.in.asistencia.RegistrarAsistenciaManualUseCase;
import com.codigo.sanidaddivina.domain.port.in.asistencia.RegistrarAsistenciaPorDniUseCase;
import com.codigo.sanidaddivina.infrastructure.exception.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/asistencias")
@RequiredArgsConstructor
@Tag(name = "Asistencias", description = "Registro de asistencia a cultos — manual, por código de barras/QR y por huella dactilar")
public class AsistenciaRestController {

    private final RegistrarAsistenciaManualUseCase registrarManualUseCase;
    private final RegistrarAsistenciaHuellaUseCase registrarHuellaUseCase;
    private final RegistrarAsistenciaPorDniUseCase registrarPorDniUseCase;
    private final BuscarAsistenciaUseCase buscarAsistenciaUseCase;

    public record AsistenciaManualRequest(
            @NotNull Long personaId,
            @NotNull Long registradoPorId,
            Long sesionId
    ) {}

    public record AsistenciaHuellaRequest(@NotBlank String fingerprintTemplateBase64) {}

    // ── REGISTRO ──────────────────────────────────────────────────────────────

    @PostMapping("/manual")
    @Operation(summary = "Registrar asistencia manual — selección directa de persona (requiere ENCARGADO)")
    public ResponseEntity<ApiResponse<?>> manual(@Valid @RequestBody AsistenciaManualRequest req) {
        var asistencia = registrarManualUseCase.ejecutar(
                new RegistrarAsistenciaManualUseCase.Command(
                        req.personaId(), req.registradoPorId(), req.sesionId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(asistencia));
    }

    @PostMapping("/barcode/{dni}")
    @Operation(summary = "Registrar asistencia por código de barras/QR — escaneo del DNI",
               description = "El escáner envía el DNI leído. Se vincula automáticamente a la sesión de culto abierta.")
    public ResponseEntity<ApiResponse<?>> porDni(@PathVariable String dni) {
        var asistencia = registrarPorDniUseCase.ejecutar(dni);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(asistencia));
    }

    @PostMapping("/huella")
    @Operation(summary = "Registrar asistencia por huella dactilar (requiere ENCARGADO)",
               description = "Envía el template biométrico en Base64. Se vincula automáticamente a la sesión de culto abierta.")
    public ResponseEntity<ApiResponse<?>> huella(@Valid @RequestBody AsistenciaHuellaRequest req) {
        var asistencia = registrarHuellaUseCase.ejecutar(req.fingerprintTemplateBase64());
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(asistencia));
    }

    // ── CONSULTAS ─────────────────────────────────────────────────────────────

    @GetMapping
    @Operation(summary = "Listar asistencias con paginación")
    public ResponseEntity<ApiResponse<?>> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ApiResponse.ok(
                buscarAsistenciaUseCase.todas(PageRequest.of(page, size))));
    }

    @GetMapping("/persona/{personaId}")
    @Operation(summary = "Asistencias de una persona")
    public ResponseEntity<ApiResponse<?>> porPersona(@PathVariable Long personaId) {
        return ResponseEntity.ok(ApiResponse.ok(buscarAsistenciaUseCase.porPersona(personaId)));
    }

    @GetMapping("/fecha")
    @Operation(summary = "Asistencias por fecha (yyyy-MM-dd)")
    public ResponseEntity<ApiResponse<?>> porFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(ApiResponse.ok(buscarAsistenciaUseCase.porFecha(fecha)));
    }

    @GetMapping("/sesion/{sesionId}")
    @Operation(summary = "Todas las asistencias de una sesión de culto")
    public ResponseEntity<ApiResponse<?>> porSesion(
            @PathVariable Long sesionId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ApiResponse.ok(
                buscarAsistenciaUseCase.porSesion(sesionId, PageRequest.of(page, size))));
    }
}
