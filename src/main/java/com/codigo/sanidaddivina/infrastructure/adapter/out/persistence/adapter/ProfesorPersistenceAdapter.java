package com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.adapter;

import com.codigo.sanidaddivina.dao.MiembroRepository;
import com.codigo.sanidaddivina.dao.ProfesorRepository;
import com.codigo.sanidaddivina.domain.model.Profesor;
import com.codigo.sanidaddivina.domain.port.out.ProfesorRepositoryPort;
import com.codigo.sanidaddivina.entities.ProfesorEntity;
import com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.mapper.ProfesorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProfesorPersistenceAdapter implements ProfesorRepositoryPort {

    private final ProfesorRepository profesorRepository;
    private final MiembroRepository miembroRepository;
    private final ProfesorMapper profesorMapper;

    @Override
    public Profesor guardar(Profesor profesor) {
        ProfesorEntity entity = profesorMapper.toEntity(profesor);
        if (profesor.getMiembroId() != null) {
            miembroRepository.findById(profesor.getMiembroId())
                    .ifPresent(entity::setMiembro);
        }
        return profesorMapper.toDomain(profesorRepository.save(entity));
    }

    @Override
    public Optional<Profesor> buscarPorId(Long id) {
        return profesorRepository.findById(id).map(profesorMapper::toDomain);
    }

    @Override
    public Optional<Profesor> buscarPorMiembroId(Long miembroId) {
        return profesorRepository.findByMiembroIdMiembro(miembroId).map(profesorMapper::toDomain);
    }

    @Override
    public Page<Profesor> buscarTodos(Pageable pageable) {
        return profesorRepository.findAll(pageable).map(profesorMapper::toDomain);
    }

    @Override
    public void eliminar(Long id) {
        profesorRepository.deleteById(id);
    }
}
