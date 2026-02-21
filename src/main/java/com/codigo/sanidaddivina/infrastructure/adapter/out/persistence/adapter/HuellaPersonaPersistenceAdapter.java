package com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.adapter;

import com.codigo.sanidaddivina.dao.HuellaPersonaRepository;
import com.codigo.sanidaddivina.dao.PersonaRepository;
import com.codigo.sanidaddivina.domain.model.HuellaPersona;
import com.codigo.sanidaddivina.domain.port.out.HuellaPersonaRepositoryPort;
import com.codigo.sanidaddivina.entities.HuellaPersonaEntity;
import com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.mapper.HuellaPersonaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class HuellaPersonaPersistenceAdapter implements HuellaPersonaRepositoryPort {

    private final HuellaPersonaRepository huellaPersonaRepository;
    private final PersonaRepository personaRepository;
    private final HuellaPersonaMapper huellaPersonaMapper;

    @Override
    public HuellaPersona guardar(HuellaPersona huella) {
        HuellaPersonaEntity entity = huellaPersonaMapper.toEntity(huella);
        if (huella.getPersonaId() != null) {
            personaRepository.findById(huella.getPersonaId()).ifPresent(entity::setPersona);
        }
        return huellaPersonaMapper.toDomain(huellaPersonaRepository.save(entity));
    }

    @Override
    public Optional<HuellaPersona> buscarPorId(Long id) {
        return huellaPersonaRepository.findById(id).map(huellaPersonaMapper::toDomain);
    }

    @Override
    public List<HuellaPersona> buscarActivasPorPersona(Long personaId) {
        return huellaPersonaRepository.findByPersonaIdPersonaAndActivoTrue(personaId)
                .stream().map(huellaPersonaMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<HuellaPersona> buscarTodasActivas() {
        return huellaPersonaRepository.findByActivoTrue()
                .stream().map(huellaPersonaMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public void eliminar(Long id) {
        huellaPersonaRepository.deleteById(id);
    }
}
