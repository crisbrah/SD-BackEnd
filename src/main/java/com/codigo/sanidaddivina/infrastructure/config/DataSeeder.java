package com.codigo.sanidaddivina.infrastructure.config;

import com.codigo.sanidaddivina.dao.MiembroRepository;
import com.codigo.sanidaddivina.dao.PersonaRepository;
import com.codigo.sanidaddivina.dao.RolRepository;
import com.codigo.sanidaddivina.entities.MiembroEntity;
import com.codigo.sanidaddivina.entities.PersonaEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Paso 1: Crea los 6 roles estándar si no existen (independiente de init.sql).
 * Paso 2: Crea el primer SUPER_ADMIN al arrancar si no existe ninguno.
 * Credenciales iniciales:
 *   Email:    admin@sanidaddivina.com
 *   Password: Admin.2024
 * Cambia la contraseña inmediatamente después del primer ingreso.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements ApplicationRunner {

    private final MiembroRepository miembroRepository;
    private final PersonaRepository personaRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    private static final java.util.List<String> ROLES_ESTANDAR = java.util.List.of(
            "SUPER_ADMIN", "ADMIN", "TESORERO", "ENCARGADO", "MAESTRO", "MIEMBRO"
    );

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        // ── Paso 1: Crear roles si no existen ────────────────────────────────
        ROLES_ESTANDAR.forEach(nombre -> {
            if (rolRepository.findByNombreRol(nombre).isEmpty()) {
                com.codigo.sanidaddivina.entities.RolEntity nuevoRol = new com.codigo.sanidaddivina.entities.RolEntity();
                nuevoRol.setNombreRol(nombre);
                nuevoRol.setEstadoRol(true);
                rolRepository.save(nuevoRol);
                log.info("Rol creado automáticamente: {}", nombre);
            }
        });

        // ── Paso 2: Crear SUPER_ADMIN por defecto si no hay ninguno ──────────
        if (miembroRepository.existsByRoles_NombreRol("SUPER_ADMIN")) {
            log.debug("SUPER_ADMIN ya existe — omitiendo seed inicial");
            return;
        }

        var rol = rolRepository.findByNombreRol("SUPER_ADMIN")
                .orElseThrow(() -> new IllegalStateException("Rol SUPER_ADMIN no pudo crearse"));

        PersonaEntity persona = new PersonaEntity();
        persona.setNombres("Super");
        persona.setApePat("Admin");
        persona.setApeMat("Sistema");
        persona.setDni("00000000");
        persona.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        persona.setEstado(true);
        personaRepository.save(persona);

        MiembroEntity miembro = new MiembroEntity();
        miembro.setEmail("admin@sanidaddivina.com");
        miembro.setPassword(passwordEncoder.encode("Admin.2024"));
        miembro.setPersona(persona);
        miembro.setFechaConversion(LocalDate.now());
        miembro.setEstadoMiembro(true);
        miembro.setAprobado(true);  // Aprobado por defecto (es el admin del sistema)
        miembro.setEsNuevo(false);
        miembro.getRoles().add(rol);
        miembroRepository.save(miembro);

        log.warn("============================================================");
        log.warn("  SUPER_ADMIN creado con credenciales por defecto:");
        log.warn("  Email:    admin@sanidaddivina.com");
        log.warn("  Password: Admin.2024");
        log.warn("  ¡Cambia la contraseña después del primer ingreso!");
        log.warn("============================================================");
    }
}
