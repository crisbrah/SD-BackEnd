package com.codigo.sanidaddivina.dao;

import com.codigo.sanidaddivina.entities.CelulaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CelulaRepository extends JpaRepository<CelulaEntity, Long> {
    List<CelulaEntity> findByNombreCelulaContainingIgnoreCase(String nombreCelula);
    @Query(value = "SELECT COUNT(*) FROM tu_tabla", nativeQuery = true)
    Long contarRegistros();


}
