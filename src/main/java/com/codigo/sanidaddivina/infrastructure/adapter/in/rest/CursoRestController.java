package com.codigo.sanidaddivina.infrastructure.adapter.in.rest;

import com.codigo.sanidaddivina.domain.model.enums.EstadoCursoPersona;
import com.codigo.sanidaddivina.domain.model.enums.MetodoPago;
import com.codigo.sanidaddivina.domain.model.enums.TipoRegistroAsistencia;
import com.codigo.sanidaddivina.domain.port.in.educacion.GestionarCursoUseCase;
import com.codigo.sanidaddivina.domain.port.in.educacion.MatricularPersonaCursoUseCase;
import com.codigo.sanidaddivina.domain.port.in.educacion.RegistrarAsistenciaCursoPorDniUseCase;
import com.codigo.sanidaddivina.domain.port.in.educacion.RegistrarAsistenciaCursoUseCase;
import com.codigo.sanidaddivina.domain.port.in.educacion.RegistrarNotaUseCase;
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
@RequestMapping("/api/v1/cursos")
@RequiredArgsConstructor
@Tag(name = "Cursos", description = "Gestión de cursos y matrículas de educación cristiana")
public class CursoRestController {

    private final GestionarCursoUseCase gestionarCursoUseCase;
    private final MatricularPersonaCursoUseCase matricularUseCase;
    private final RegistrarNotaUseCase registrarNotaUseCase;
    private final RegistrarAsistenciaCursoUseCase registrarAsistenciaCursoUseCase;
    private final RegistrarAsistenciaCursoPorDniUseCase registrarAsistenciaCursoPorDniUseCase;

    public record CursoRequest(
            @NotBlank String nombreCurso,
            LocalDate fechaInicio,
            LocalDate fechaFin,
            boolean esPago,
            BigDecimal costo,
            Long escuelaId,
            Long profesorId
    ) {}

    public record MatricularRequest(
            @NotNull Long personaId,
            @NotNull MetodoPago metodoPago
    ) {}

    public record NotaRequest(
            @NotNull BigDecimal nota,
            @NotNull EstadoCursoPersona estado,
            LocalDate fechaFin
    ) {}

    /** Registro manual: se envía personaId y el tipo de registro */
    public record AsistenciaRequest(
            @NotNull Long personaId,
            @NotNull TipoRegistroAsistencia tipoRegistro
    ) {}

    // ── CRUD CURSO ────────────────────────────────────────────────────────────

    @PostMapping
    @Operation(summary = "Crear curso (requiere MAESTRO, ADMIN o SUPER_ADMIN)")
    public ResponseEntity<ApiResponse<?>> crear(@Valid @RequestBody CursoRequest req) {
        var curso = gestionarCursoUseCase.crear(new GestionarCursoUseCase.Command(
                req.nombreCurso(), req.fechaInicio(), req.fechaFin(),
                req.esPago(), req.costo(), req.escuelaId(), req.profesorId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(curso));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar curso por ID")
    public ResponseEntity<ApiResponse<?>> porId(@PathVariable Long id) {
        var curso = gestionarCursoUseCase.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con ID: " + id));
        return ResponseEntity.ok(ApiResponse.ok(curso));
    }

    @GetMapping
    @Operation(summary = "Listar cursos con paginación")
    public ResponseEntity<ApiResponse<?>> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) Long escuelaId) {
        var pageable = PageRequest.of(page, size);
        var result = (escuelaId != null)
                ? gestionarCursoUseCase.buscarPorEscuela(escuelaId, pageable)
                : gestionarCursoUseCase.buscarTodos(pageable);
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar curso (requiere MAESTRO, ADMIN o SUPER_ADMIN)")
    public ResponseEntity<ApiResponse<?>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody CursoRequest req) {
        var curso = gestionarCursoUseCase.actualizar(id, new GestionarCursoUseCase.Command(
                req.nombreCurso(), req.fechaInicio(), req.fechaFin(),
                req.esPago(), req.costo(), req.escuelaId(), req.profesorId()));
        return ResponseEntity.ok(ApiResponse.ok(curso));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar curso (solo SUPER_ADMIN)")
    public ResponseEntity<ApiResponse<?>> eliminar(@PathVariable Long id) {
        gestionarCursoUseCase.eliminar(id);
        return ResponseEntity.ok(ApiResponse.ok("Curso eliminado"));
    }

    // ── INSCRIPCIONES ─────────────────────────────────────────────────────────

    @PostMapping("/{cursoId}/inscripciones")
    @Operation(summary = "Matricular persona en curso (genera ingreso si tiene costo)")
    public ResponseEntity<ApiResponse<?>> matricular(
            @PathVariable Long cursoId,
            @Valid @RequestBody MatricularRequest req) {
        var inscripcion = matricularUseCase.ejecutar(
                new MatricularPersonaCursoUseCase.Command(cursoId, req.personaId(), req.metodoPago()));
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(inscripcion));
    }

    @PutMapping("/{cursoId}/inscripciones/{inscripcionId}/nota")
    @Operation(summary = "Registrar nota de inscripción (requiere MAESTRO, ADMIN o SUPER_ADMIN)")
    public ResponseEntity<ApiResponse<?>> registrarNota(
            @PathVariable Long cursoId,
            @PathVariable Long inscripcionId,
            @Valid @RequestBody NotaRequest req) {
        var inscripcion = registrarNotaUseCase.ejecutar(inscripcionId,
                new RegistrarNotaUseCase.Command(req.nota(), req.estado(), req.fechaFin()));
        return ResponseEntity.ok(ApiResponse.ok(inscripcion));
    }

    // ── ASISTENCIAS ──────────────────────────────────────────────────────────

    @PostMapping("/{cursoId}/asistencias")
    @Operation(summary = "Registrar asistencia manual — selección directa de persona",
               description = "Enviar personaId y tipoRegistro (MANUAL, CODIGO_QR o HUELLA_DACTILAR).")
    public ResponseEntity<ApiResponse<?>> registrarAsistencia(
            @PathVariable Long cursoId,
            @Valid @RequestBody AsistenciaRequest req) {
        var asistencia = registrarAsistenciaCursoUseCase.registrar(
                new RegistrarAsistenciaCursoUseCase.Command(
                        cursoId, req.personaId(), req.tipoRegistro()));
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(asistencia));
    }

    @PostMapping("/{cursoId}/asistencias/barcode/{dni}")
    @Operation(summary = "Registrar asistencia por código de barras/QR — escaneo del DNI",
               description = "El escáner envía el DNI. Valida que la persona esté inscrita en el curso.")
    public ResponseEntity<ApiResponse<?>> registrarAsistenciaPorDni(
            @PathVariable Long cursoId,
            @PathVariable String dni) {
        var asistencia = registrarAsistenciaCursoPorDniUseCase.ejecutar(cursoId, dni);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(asistencia));
    }

    @GetMapping("/{cursoId}/asistencias")
    @Operation(summary = "Listar asistencias de un curso")
    public ResponseEntity<ApiResponse<?>> listarAsistencias(
            @PathVariable Long cursoId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ApiResponse.ok(
                registrarAsistenciaCursoUseCase.buscarPorCurso(cursoId, PageRequest.of(page, size))));
    }

    @DeleteMapping("/{cursoId}/asistencias/{asistenciaId}")
    @Operation(summary = "Eliminar registro de asistencia")
    public ResponseEntity<ApiResponse<?>> eliminarAsistencia(
            @PathVariable Long cursoId,
            @PathVariable Long asistenciaId) {
        registrarAsistenciaCursoUseCase.eliminar(asistenciaId);
        return ResponseEntity.ok(ApiResponse.ok("Asistencia eliminada"));
    }
}
