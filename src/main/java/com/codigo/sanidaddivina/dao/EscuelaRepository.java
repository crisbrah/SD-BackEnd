package com.codigo.sanidaddivina.dao;

import com.codigo.sanidaddivina.domain.model.enums.FaseEscuela;
import com.codigo.sanidaddivina.entities.EscuelaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EscuelaRepository extends JpaRepository<EscuelaEntity, Long> {
    List<EscuelaEntity> findByFase(FaseEscuela fase);
}
