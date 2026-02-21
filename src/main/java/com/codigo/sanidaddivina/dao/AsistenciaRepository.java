package com.codigo.sanidaddivina.dao;

import com.codigo.sanidaddivina.entities.AsistenciaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AsistenciaRepository extends JpaRepository<AsistenciaEntity, Long> {

    List<AsistenciaEntity> findByPersonaIdPersona(Long personaId);

    @Query("""
            SELECT a FROM AsistenciaEntity a
            WHERE CAST(a.fechaAsistencia AS date) = :fecha
            ORDER BY a.fechaAsistencia
            """)
    List<AsistenciaEntity> findByFecha(@Param("fecha") LocalDate fecha);

    @EntityGraph(attributePaths = {"persona"})
    Page<AsistenciaEntity> findBySesionCultoIdSesion(Long sesionId, Pageable pageable);
}
