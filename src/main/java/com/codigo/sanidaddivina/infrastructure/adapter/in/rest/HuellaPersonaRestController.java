package com.codigo.sanidaddivina.infrastructure.adapter.in.rest;

import com.codigo.sanidaddivina.domain.model.enums.DedoHuella;
import com.codigo.sanidaddivina.domain.port.in.asistencia.GestionarHuellaPersonaUseCase;
import com.codigo.sanidaddivina.infrastructure.exception.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
@RequestMapping("/api/v1/huellas")
@RequiredArgsConstructor
@Tag(name = "Huellas Dactilares", description = "Registro y gestión de templates biométricos por persona")
public class HuellaPersonaRestController {

    private final GestionarHuellaPersonaUseCase gestionarHuellaUseCase;

    /**
     * El template se envía en Base64 para compatibilidad con JSON.
     * El backend lo decodifica a bytes antes de persistirlo.
     */
    public record RegistrarHuellaRequest(
            @NotNull Long personaId,
            @NotNull String templateBase64,
            @NotNull DedoHuella dedo
    ) {}

    @PostMapping
    @Operation(summary = "Registrar huella dactilar de una persona (requiere ENCARGADO o ADMIN)",
               description = "Enviar el template biométrico en Base64. Se almacena como bytes en BD.")
    public ResponseEntity<ApiResponse<?>> registrar(@Valid @RequestBody RegistrarHuellaRequest req) {
        byte[] template = Base64.getDecoder().decode(req.templateBase64());
        var huella = gestionarHuellaUseCase.registrar(
                new GestionarHuellaPersonaUseCase.Command(req.personaId(), template, req.dedo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(huella));
    }

    @GetMapping("/persona/{personaId}")
    @Operation(summary = "Listar huellas activas de una persona")
    public ResponseEntity<ApiResponse<?>> porPersona(@PathVariable Long personaId) {
        return ResponseEntity.ok(ApiResponse.ok(
                gestionarHuellaUseCase.listarActivasPorPersona(personaId)));
    }

    @PutMapping("/{id}/desactivar")
    @Operation(summary = "Desactivar una huella (no la elimina físicamente)")
    public ResponseEntity<ApiResponse<?>> desactivar(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(gestionarHuellaUseCase.desactivar(id)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar físicamente una huella desactivada (solo SUPER_ADMIN)")
    public ResponseEntity<ApiResponse<?>> eliminar(@PathVariable Long id) {
        gestionarHuellaUseCase.eliminar(id);
        return ResponseEntity.ok(ApiResponse.ok("Huella eliminada"));
    }
}
