package com.codigo.sanidaddivina.infrastructure.adapter.in.rest;

import com.codigo.sanidaddivina.dao.MiembroRepository;
import com.codigo.sanidaddivina.entities.MiembroEntity;
import com.codigo.sanidaddivina.infrastructure.exception.ApiResponse;
import com.codigo.sanidaddivina.infrastructure.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/miembros")
@RequiredArgsConstructor
@Tag(name = "Administración", description = "Aprobación de nuevos miembros")
public class AprobacionRestController {

    private final MiembroRepository miembroRepository;

    @GetMapping("/pendientes")
    @PreAuthorize("hasAnyAuthority('ADMIN','SUPER_ADMIN')")
    @Operation(summary = "Listar miembros pendientes de aprobación")
    public ResponseEntity<ApiResponse<?>> listarPendientes() {
        List<MiembroEntity> pendientes = miembroRepository.findByAprobadoFalseAndEstadoMiembroTrue();
        List<Map<String, Object>> result = pendientes.stream().map(m -> {
            Map<String, Object> item = new java.util.LinkedHashMap<>();
            item.put("idMiembro", m.getIdMiembro());
            item.put("email", m.getEmail());
            if (m.getPersona() != null) {
                item.put("nombres", m.getPersona().getNombres());
                item.put("apePat", m.getPersona().getApePat());
                item.put("apeMat", m.getPersona().getApeMat());
                item.put("dni", m.getPersona().getDni());
                item.put("fechaNacimiento", m.getPersona().getFechaNacimiento());
            }
            item.put("fechaConversion", m.getFechaConversion());
            return item;
        }).toList();
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    @PutMapping("/{id}/aprobar")
    @PreAuthorize("hasAnyAuthority('ADMIN','SUPER_ADMIN')")
    @Operation(summary = "Aprobar un miembro pendiente")
    public ResponseEntity<ApiResponse<?>> aprobar(@PathVariable Long id) {
        MiembroEntity miembro = miembroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Miembro no encontrado con id: " + id));
        miembro.setAprobado(true);
        miembroRepository.save(miembro);
        return ResponseEntity.ok(ApiResponse.ok("Miembro aprobado correctamente."));
    }

    @PutMapping("/{id}/rechazar")
    @PreAuthorize("hasAnyAuthority('ADMIN','SUPER_ADMIN')")
    @Operation(summary = "Rechazar un miembro pendiente (desactiva la cuenta)")
    public ResponseEntity<ApiResponse<?>> rechazar(@PathVariable Long id) {
        MiembroEntity miembro = miembroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Miembro no encontrado con id: " + id));
        miembro.setAprobado(false);
        miembro.setEstadoMiembro(false); // Desactiva la cuenta
        miembroRepository.save(miembro);
        return ResponseEntity.ok(ApiResponse.ok("Miembro rechazado."));
    }
}
