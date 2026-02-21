package com.codigo.sanidaddivina.dao;

import com.codigo.sanidaddivina.entities.SesionCultoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SesionCultoRepository extends JpaRepository<SesionCultoEntity, Long> {

    /** Sesión abierta actualmente (debería haber solo una a la vez) */
    Optional<SesionCultoEntity> findFirstByAbiertaTrue();

    /** Todas las sesiones de una sede */
    Page<SesionCultoEntity> findBySedeIdSede(Long sedeId, Pageable pageable);

    /** Sesiones abiertas (para validar duplicados) */
    List<SesionCultoEntity> findByAbiertaTrue();
}
