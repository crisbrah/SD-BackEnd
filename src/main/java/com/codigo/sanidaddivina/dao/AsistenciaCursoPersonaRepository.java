package com.codigo.sanidaddivina.dao;

import com.codigo.sanidaddivina.entities.AsistenciaCursoPersonaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AsistenciaCursoPersonaRepository extends JpaRepository<AsistenciaCursoPersonaEntity, Long> {
    Page<AsistenciaCursoPersonaEntity> findByCursoIdCurso(Long cursoId, Pageable pageable);
    Page<AsistenciaCursoPersonaEntity> findByPersonaIdPersona(Long personaId, Pageable pageable);
}
