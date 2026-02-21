package com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.adapter;

import com.codigo.sanidaddivina.dao.MiembroRolRepository;
import com.codigo.sanidaddivina.domain.model.MiembroRol;
import com.codigo.sanidaddivina.domain.port.out.MiembroRolRepositoryPort;
import com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.mapper.MiembroRolMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MiembroRolPersistenceAdapter implements MiembroRolRepositoryPort {

    private final MiembroRolRepository miembroRolRepository;
    private final MiembroRolMapper miembroRolMapper;

    @Override
    public MiembroRol guardar(MiembroRol miembroRol) {
        return miembroRolMapper.toDomain(miembroRolRepository.save(miembroRolMapper.toEntity(miembroRol)));
    }

    @Override
    public Optional<MiembroRol> buscarPorId(Long id) {
        return miembroRolRepository.findById(id).map(miembroRolMapper::toDomain);
    }

    @Override
    public Page<MiembroRol> buscarPorMiembro(Long miembroId, Pageable pageable) {
        return miembroRolRepository.findByMiembroIdMiembro(miembroId, pageable).map(miembroRolMapper::toDomain);
    }

    @Override
    public void eliminar(Long id) {
        miembroRolRepository.deleteById(id);
    }
}
