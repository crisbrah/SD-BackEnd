package com.codigo.sanidaddivina.domain.port.in.asistencia;

import com.codigo.sanidaddivina.domain.model.SesionCulto;
import com.codigo.sanidaddivina.domain.model.enums.TipoCulto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface GestionarSesionCultoUseCase {

    /** Abre una nueva sesión de culto */
    SesionCulto abrir(Command command);

    /** Cierra la sesión abierta (registra fecha_fin) */
    SesionCulto cerrar(Long id);

    Optional<SesionCulto> buscarPorId(Long id);

    /** Devuelve la sesión actualmente abierta, si existe */
    Optional<SesionCulto> buscarSesionAbierta();

    List<SesionCulto> listarSesionesAbiertas();

    Page<SesionCulto> buscarTodos(Pageable pageable);

    Page<SesionCulto> buscarPorSede(Long sedeId, Pageable pageable);

    void eliminar(Long id);

    record Command(
            String nombreSesion,
            TipoCulto tipoCulto,
            Long sedeId
    ) {}
}
