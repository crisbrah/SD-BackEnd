package com.codigo.sanidaddivina.dao;

import com.codigo.sanidaddivina.entities.ProfesorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfesorRepository extends JpaRepository<ProfesorEntity, Long> {
    Optional<ProfesorEntity> findByMiembroIdMiembro(Long miembroId);
    boolean existsByMiembroIdMiembro(Long miembroId);
}
