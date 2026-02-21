package com.codigo.sanidaddivina.dao;

import com.codigo.sanidaddivina.entities.MiembroRolEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MiembroRolRepository extends JpaRepository<MiembroRolEntity, Long> {
    Page<MiembroRolEntity> findByMiembroIdMiembro(Long miembroId, Pageable pageable);
}
