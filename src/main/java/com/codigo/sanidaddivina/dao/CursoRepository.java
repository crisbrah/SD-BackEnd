package com.codigo.sanidaddivina.dao;


import com.codigo.sanidaddivina.entities.CursoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<CursoEntity, Long> {
    Page<CursoEntity> findByEscuelaIdEscuela(Long escuelaId, Pageable pageable);
}
