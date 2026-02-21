package com.codigo.sanidaddivina.dao;

import com.codigo.sanidaddivina.entities.CursoPersonaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CursoPersonaEntityRepository extends JpaRepository<CursoPersonaEntity, Long> {

    Page<CursoPersonaEntity> findByCursoIdCurso(Long cursoId, Pageable pageable);

    List<CursoPersonaEntity> findByPersonaIdPersona(Long personaId);

    boolean existsByCursoIdCursoAndPersonaIdPersona(Long cursoId, Long personaId);
}
