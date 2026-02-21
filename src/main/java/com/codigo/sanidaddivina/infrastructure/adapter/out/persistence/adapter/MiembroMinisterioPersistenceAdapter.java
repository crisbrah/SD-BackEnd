package com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.adapter;

import com.codigo.sanidaddivina.dao.MiembroMinisterioEntityRepository;
import com.codigo.sanidaddivina.domain.model.MiembroMinisterio;
import com.codigo.sanidaddivina.domain.port.out.MiembroMinisterioRepositoryPort;
import com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.mapper.MiembroMinisterioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MiembroMinisterioPersistenceAdapter implements MiembroMinisterioRepositoryPort {

    private final MiembroMinisterioEntityRepository miembroMinisterioRepository;
    private final MiembroMinisterioMapper miembroMinisterioMapper;

    @Override
    public MiembroMinisterio guardar(MiembroMinisterio miembroMinisterio) {
        return miembroMinisterioMapper.toDomain(
                miembroMinisterioRepository.save(miembroMinisterioMapper.toEntity(miembroMinisterio)));
    }

    @Override
    public Optional<MiembroMinisterio> buscarPorId(Long id) {
        return miembroMinisterioRepository.findById(id).map(miembroMinisterioMapper::toDomain);
    }

    @Override
    public Page<MiembroMinisterio> buscarPorMinisterio(Long ministerioId, Pageable pageable) {
        return miembroMinisterioRepository.findByMinisterioIdMinisterio(ministerioId, pageable)
                .map(miembroMinisterioMapper::toDomain);
    }

    @Override
    public Page<MiembroMinisterio> buscarPorMiembro(Long miembroId, Pageable pageable) {
        return miembroMinisterioRepository.findByMiembroIdMiembro(miembroId, pageable)
                .map(miembroMinisterioMapper::toDomain);
    }

    @Override
    public void eliminar(Long id) {
        miembroMinisterioRepository.deleteById(id);
    }
}
