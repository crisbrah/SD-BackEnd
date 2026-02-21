package com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.adapter;

import com.codigo.sanidaddivina.dao.AsistenciaRepository;
import com.codigo.sanidaddivina.dao.MiembroRepository;
import com.codigo.sanidaddivina.dao.PersonaRepository;
import com.codigo.sanidaddivina.dao.SesionCultoRepository;
import com.codigo.sanidaddivina.domain.model.Asistencia;
import com.codigo.sanidaddivina.domain.port.out.AsistenciaRepositoryPort;
import com.codigo.sanidaddivina.entities.AsistenciaEntity;
import com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.mapper.AsistenciaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AsistenciaPersistenceAdapter implements AsistenciaRepositoryPort {

    private final AsistenciaRepository asistenciaRepository;
    private final PersonaRepository personaRepository;
    private final MiembroRepository miembroRepository;
    private final SesionCultoRepository sesionCultoRepository;
    private final AsistenciaMapper asistenciaMapper;

    @Override
    public Asistencia guardar(Asistencia asistencia) {
        AsistenciaEntity entity = asistenciaMapper.toEntity(asistencia);

        if (asistencia.getPersonaId() != null) {
            personaRepository.findById(asistencia.getPersonaId()).ifPresent(entity::setPersona);
        }
        if (asistencia.getMiembroId() != null) {
            miembroRepository.findById(asistencia.getMiembroId()).ifPresent(entity::setMiembro);
        }
        if (asistencia.getSesionId() != null) {
            sesionCultoRepository.findById(asistencia.getSesionId()).ifPresent(entity::setSesionCulto);
        }

        return asistenciaMapper.toDomain(asistenciaRepository.save(entity));
    }

    @Override
    public Optional<Asistencia> buscarPorId(Long id) {
        return asistenciaRepository.findById(id).map(asistenciaMapper::toDomain);
    }

    @Override
    public Page<Asistencia> buscarTodos(Pageable pageable) {
        return asistenciaRepository.findAll(pageable).map(asistenciaMapper::toDomain);
    }

    @Override
    public List<Asistencia> buscarPorPersona(Long personaId) {
        return asistenciaRepository.findByPersonaIdPersona(personaId)
                .stream().map(asistenciaMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Asistencia> buscarPorFecha(LocalDate fecha) {
        return asistenciaRepository.findByFecha(fecha)
                .stream().map(asistenciaMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public Page<Asistencia> buscarPorSesion(Long sesionId, Pageable pageable) {
        return asistenciaRepository.findBySesionCultoIdSesion(sesionId, pageable)
                .map(asistenciaMapper::toDomain);
    }

    @Override
    public void eliminar(Long id) {
        asistenciaRepository.deleteById(id);
    }
}
