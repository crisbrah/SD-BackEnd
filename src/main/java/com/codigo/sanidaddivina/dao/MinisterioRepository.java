package com.codigo.sanidaddivina.dao;


import com.codigo.sanidaddivina.entities.MinisterioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MinisterioRepository extends JpaRepository<MinisterioEntity, Long> {
    List<MinisterioEntity> findByNombreMinisterioContainingIgnoreCase(String nombreMinisterio);
}
