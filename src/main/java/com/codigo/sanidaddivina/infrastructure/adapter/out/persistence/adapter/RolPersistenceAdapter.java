package com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.adapter;

import com.codigo.sanidaddivina.dao.RolRepository;
import com.codigo.sanidaddivina.domain.model.Rol;
import com.codigo.sanidaddivina.domain.port.out.RolRepositoryPort;
import com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.mapper.RolMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RolPersistenceAdapter implements RolRepositoryPort {

    private final RolRepository rolRepository;
    private final RolMapper rolMapper;

    @Override
    public Rol guardar(Rol rol) {
        return rolMapper.toDomain(rolRepository.save(rolMapper.toEntity(rol)));
    }

    @Override
    public Optional<Rol> buscarPorId(Long id) {
        return rolRepository.findById(id).map(rolMapper::toDomain);
    }

    @Override
    public Page<Rol> buscarTodos(Pageable pageable) {
        return rolRepository.findAll(pageable).map(rolMapper::toDomain);
    }

    @Override
    public void eliminar(Long id) {
        rolRepository.deleteById(id);
    }
}
