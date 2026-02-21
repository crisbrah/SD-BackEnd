package com.codigo.sanidaddivina.dao;


import com.codigo.sanidaddivina.entities.RolEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RolRepository extends JpaRepository<RolEntity, Long> {
    List<RolEntity> findByNombreRolContainingIgnoreCase(String nombreRol);

    Optional<RolEntity> findByNombreRol(String name);
}
