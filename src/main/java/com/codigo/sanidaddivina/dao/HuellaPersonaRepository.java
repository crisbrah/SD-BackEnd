package com.codigo.sanidaddivina.dao;

import com.codigo.sanidaddivina.entities.HuellaPersonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HuellaPersonaRepository extends JpaRepository<HuellaPersonaEntity, Long> {

    /** Huellas activas de una persona */
    List<HuellaPersonaEntity> findByPersonaIdPersonaAndActivoTrue(Long personaId);

    /** Todas las huellas activas (para identificación por huella) */
    List<HuellaPersonaEntity> findByActivoTrue();
}
