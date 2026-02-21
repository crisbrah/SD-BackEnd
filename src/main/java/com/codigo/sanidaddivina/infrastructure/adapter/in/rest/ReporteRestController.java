package com.codigo.sanidaddivina.infrastructure.adapter.in.rest;

import com.codigo.sanidaddivina.domain.port.in.asistencia.BuscarAsistenciaUseCase;
import com.codigo.sanidaddivina.domain.port.in.financiero.BuscarFinancieroUseCase;
import com.codigo.sanidaddivina.infrastructure.exception.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/reportes")
@RequiredArgsConstructor
@Tag(name = "Reportes", description = "Reportes financieros y de asistencia (requiere TESORERO, ADMIN o SUPER_ADMIN)")
public class ReporteRestController {

    private final BuscarFinancieroUseCase buscarFinancieroUseCase;
    private final BuscarAsistenciaUseCase buscarAsistenciaUseCase;

    @GetMapping("/financiero/mensual")
    @Operation(summary = "Reporte financiero mensual — ingresos y egresos")
    public ResponseEntity<ApiResponse<?>> financieroMensual(
            @RequestParam int mes,
            @RequestParam int anio) {
        var ingresos = buscarFinancieroUseCase.ingresosPorMes(mes, anio);
        var egresos = buscarFinancieroUseCase.egresosPorMes(mes, anio);
        var totalIngresos = ingresos.stream()
                .mapToDouble(i -> i.getMonto().doubleValue()).sum();
        var totalEgresos = egresos.stream()
                .mapToDouble(e -> e.getMontoSalida().doubleValue()).sum();

        Map<String, Object> reporte = new LinkedHashMap<>();
        reporte.put("mes", mes);
        reporte.put("anio", anio);
        reporte.put("ingresos", ingresos);
        reporte.put("totalIngresos", totalIngresos);
        reporte.put("egresos", egresos);
        reporte.put("totalEgresos", totalEgresos);
        reporte.put("balance", totalIngresos - totalEgresos);

        return ResponseEntity.ok(ApiResponse.ok(reporte));
    }

    @GetMapping("/asistencia/mensual")
    @Operation(summary = "Reporte de asistencia por rango de fechas")
    public ResponseEntity<ApiResponse<?>> asistenciaMensual(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        var asistencias = buscarAsistenciaUseCase.porFecha(desde);

        Map<String, Object> reporte = new LinkedHashMap<>();
        reporte.put("desde", desde);
        reporte.put("hasta", hasta);
        reporte.put("asistencias", asistencias);
        reporte.put("total", asistencias.size());

        return ResponseEntity.ok(ApiResponse.ok(reporte));
    }
}
