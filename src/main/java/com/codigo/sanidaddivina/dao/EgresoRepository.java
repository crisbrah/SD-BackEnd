package com.codigo.sanidaddivina.dao;

import com.codigo.sanidaddivina.domain.model.enums.TipoEgreso;
import com.codigo.sanidaddivina.entities.EgresoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface EgresoRepository extends JpaRepository<EgresoEntity, Long> {

    Page<EgresoEntity> findByTipoEgreso(TipoEgreso tipoEgreso, Pageable pageable);

    List<EgresoEntity> findByFechaEgresoBetween(LocalDate desde, LocalDate hasta);

    @Query("SELECT e FROM EgresoEntity e WHERE MONTH(e.fechaEgreso) = :mes AND YEAR(e.fechaEgreso) = :anio ORDER BY e.fechaEgreso")
    List<EgresoEntity> findByMesYAnio(@Param("mes") int mes, @Param("anio") int anio);
}
