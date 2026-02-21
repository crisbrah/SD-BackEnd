package com.codigo.sanidaddivina.dao;

import com.codigo.sanidaddivina.entities.PersonaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PersonaRepository extends JpaRepository<PersonaEntity, Long> {

    boolean existsByDni(String dni);

    Optional<PersonaEntity> findByDni(String dni);

    List<PersonaEntity> findByNombresContainingIgnoreCase(String nombres);

    Page<PersonaEntity> findByNombresContainingIgnoreCaseOrApePatContainingIgnoreCaseOrApeMatContainingIgnoreCase(
            String nombres, String apePat, String apeMat, Pageable pageable);

    Page<PersonaEntity> findByEstado(boolean estado, Pageable pageable);

    @Query("SELECT p FROM PersonaEntity p WHERE p.estado = true AND MONTH(p.fechaNacimiento) = :mes AND DAY(p.fechaNacimiento) = :dia ORDER BY p.nombres")
    List<PersonaEntity> findByBirthday(@Param("mes") int mes, @Param("dia") int dia);

    @Query("SELECT p FROM PersonaEntity p WHERE p.estado = true AND MONTH(p.fechaNacimiento) = :mes ORDER BY DAY(p.fechaNacimiento)")
    List<PersonaEntity> findByBirthdayMonth(@Param("mes") int mes);

    @Query("""
            SELECT p FROM PersonaEntity p
            WHERE p.estado = true
            AND (
                MONTH(p.fechaNacimiento) > :mesInicio
                OR (MONTH(p.fechaNacimiento) = :mesInicio
                    AND DAY(p.fechaNacimiento) >= :diaInicio)
            )
            AND (
                MONTH(p.fechaNacimiento) < :mesFin
                OR (MONTH(p.fechaNacimiento) = :mesFin
                    AND DAY(p.fechaNacimiento) <= :diaFin)
            )
            ORDER BY MONTH(p.fechaNacimiento), DAY(p.fechaNacimiento)
            """)
    List<PersonaEntity> findBirthdaysInRange(
            @Param("mesInicio") int mesInicio,
            @Param("diaInicio") int diaInicio,
            @Param("mesFin") int mesFin,
            @Param("diaFin") int diaFin);
}
