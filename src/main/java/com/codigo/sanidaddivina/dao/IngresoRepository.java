package com.codigo.sanidaddivina.dao;

import com.codigo.sanidaddivina.domain.model.enums.TipoIngreso;
import com.codigo.sanidaddivina.entities.IngresoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface IngresoRepository extends JpaRepository<IngresoEntity, Long> {

    Page<IngresoEntity> findByTipoIngreso(TipoIngreso tipoIngreso, Pageable pageable);

    List<IngresoEntity> findByFechaIngresoBetween(LocalDate desde, LocalDate hasta);

    @Query("SELECT i FROM IngresoEntity i WHERE MONTH(i.fechaIngreso) = :mes AND YEAR(i.fechaIngreso) = :anio ORDER BY i.fechaIngreso")
    List<IngresoEntity> findByMesYAnio(@Param("mes") int mes, @Param("anio") int anio);
}
