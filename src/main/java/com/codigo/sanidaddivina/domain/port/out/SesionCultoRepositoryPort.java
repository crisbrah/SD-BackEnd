package com.codigo.sanidaddivina.domain.port.out;

import com.codigo.sanidaddivina.domain.model.SesionCulto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SesionCultoRepositoryPort {
    SesionCulto guardar(SesionCulto sesion);
    Optional<SesionCulto> buscarPorId(Long id);
    Optional<SesionCulto> buscarSesionAbierta();
    List<SesionCulto> listarSesionesAbiertas();
    Page<SesionCulto> buscarTodos(Pageable pageable);
    Page<SesionCulto> buscarPorSede(Long sedeId, Pageable pageable);
    void eliminar(Long id);
}
