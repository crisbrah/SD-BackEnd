package com.codigo.sanidaddivina.dao;

import com.codigo.sanidaddivina.entities.SedeRegistroEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SedeRegistroRepository extends JpaRepository<SedeRegistroEntity, Long> {
    Page<SedeRegistroEntity> findBySedeIdSede(Long sedeId, Pageable pageable);
}
