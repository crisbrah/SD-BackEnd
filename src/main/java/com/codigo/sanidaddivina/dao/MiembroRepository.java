package com.codigo.sanidaddivina.dao;


import com.codigo.sanidaddivina.entities.MiembroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MiembroRepository extends JpaRepository<MiembroEntity, Long> {
    Optional<MiembroEntity> findByEmail(String email);
    boolean existsByRoles_NombreRol(String nombreRol);
    boolean existsByEmail(String email);

    // JOIN FETCH evita LazyInitializationException al acceder a persona
    @Query("SELECT m FROM MiembroEntity m LEFT JOIN FETCH m.persona WHERE m.aprobado = false AND m.estadoMiembro = true")
    List<MiembroEntity> findByAprobadoFalseAndEstadoMiembroTrue();
}
