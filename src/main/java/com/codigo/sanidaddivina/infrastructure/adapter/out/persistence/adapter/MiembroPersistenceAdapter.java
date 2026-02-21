package com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.adapter;

import com.codigo.sanidaddivina.dao.CelulaRepository;
import com.codigo.sanidaddivina.dao.MiembroRepository;
import com.codigo.sanidaddivina.dao.PersonaRepository;
import com.codigo.sanidaddivina.dao.RolRepository;
import com.codigo.sanidaddivina.domain.model.Miembro;
import com.codigo.sanidaddivina.domain.port.out.MiembroRepositoryPort;
import com.codigo.sanidaddivina.entities.MiembroEntity;
import com.codigo.sanidaddivina.entities.RolEntity;
import com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.mapper.MiembroMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MiembroPersistenceAdapter implements MiembroRepositoryPort {

    private final MiembroRepository miembroRepository;
    private final PersonaRepository personaRepository;
    private final CelulaRepository celulaRepository;
    private final RolRepository rolRepository;
    private final MiembroMapper miembroMapper;

    @Override
    public Miembro guardar(Miembro miembro) {
        MiembroEntity entity;

        if (miembro.getId() != null) {
            entity = miembroRepository.findById(miembro.getId())
                    .orElseGet(() -> miembroMapper.toEntity(miembro));
            entity.setEmail(miembro.getEmail());
            entity.setFechaConversion(miembro.getFechaConversion());
            entity.setEstadoMiembro(miembro.isEstadoMiembro());
            entity.setEsNuevo(miembro.isEsNuevo());
        } else {
            entity = miembroMapper.toEntity(miembro);
        }

        // Resolve persona reference
        if (miembro.getPersonaId() != null) {
            personaRepository.findById(miembro.getPersonaId())
                    .ifPresent(entity::setPersona);
        }

        // Resolve celula reference
        if (miembro.getCelulaId() != null) {
            celulaRepository.findById(miembro.getCelulaId())
                    .ifPresent(entity::setCelula);
        }

        // Set encoded password if provided
        if (miembro.getPassword() != null) {
            entity.setPassword(miembro.getPassword());
        }

        // Resolve roles
        if (miembro.getRoles() != null && !miembro.getRoles().isEmpty()) {
            Set<RolEntity> roles = miembro.getRoles().stream()
                    .map(rolRepository::findByNombreRol)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());
            entity.setRoles(roles);
        }

        MiembroEntity saved = miembroRepository.save(entity);
        Miembro result = miembroMapper.toDomain(saved);
        result.setRoles(saved.getRoles().stream()
                .map(RolEntity::getNombreRol)
                .collect(Collectors.toList()));
        return result;
    }

    @Override
    public Optional<Miembro> buscarPorId(Long id) {
        return miembroRepository.findById(id).map(this::toMiembroWithRoles);
    }

    @Override
    public Optional<Miembro> buscarPorEmail(String email) {
        return miembroRepository.findByEmail(email).map(this::toMiembroWithRoles);
    }

    @Override
    public Optional<Miembro> buscarPorPersonaId(Long personaId) {
        return miembroRepository.findAll().stream()
                .filter(m -> m.getPersona() != null
                        && personaId.equals(m.getPersona().getIdPersona()))
                .findFirst()
                .map(this::toMiembroWithRoles);
    }

    @Override
    public Page<Miembro> buscarTodos(Pageable pageable) {
        return miembroRepository.findAll(pageable).map(this::toMiembroWithRoles);
    }

    @Override
    public boolean existePorEmail(String email) {
        return miembroRepository.findByEmail(email).isPresent();
    }

    @Override
    public void eliminar(Long id) {
        miembroRepository.deleteById(id);
    }

    private Miembro toMiembroWithRoles(MiembroEntity entity) {
        Miembro miembro = miembroMapper.toDomain(entity);
        List<String> roles = entity.getRoles().stream()
                .map(RolEntity::getNombreRol)
                .collect(Collectors.toList());
        miembro.setRoles(roles);
        return miembro;
    }
}
