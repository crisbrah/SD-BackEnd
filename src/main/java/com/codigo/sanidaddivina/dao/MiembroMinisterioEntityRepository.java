package com.codigo.sanidaddivina.dao;

import com.codigo.sanidaddivina.entities.MiembroMinisterioEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MiembroMinisterioEntityRepository extends JpaRepository<MiembroMinisterioEntity, Long> {
    Page<MiembroMinisterioEntity> findByMinisterioIdMinisterio(Long ministerioId, Pageable pageable);
    Page<MiembroMinisterioEntity> findByMiembroIdMiembro(Long miembroId, Pageable pageable);
}
