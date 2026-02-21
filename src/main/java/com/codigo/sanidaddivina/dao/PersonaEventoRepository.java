package com.codigo.sanidaddivina.dao;

import com.codigo.sanidaddivina.entities.PersonaEventoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaEventoRepository extends JpaRepository<PersonaEventoEntity, Long> {
    Page<PersonaEventoEntity> findByEventoIdEvento(Long eventoId, Pageable pageable);
}
