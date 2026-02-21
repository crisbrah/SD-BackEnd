package com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.adapter;

import com.codigo.sanidaddivina.dao.CursoRepository;
import com.codigo.sanidaddivina.dao.EscuelaRepository;
import com.codigo.sanidaddivina.dao.ProfesorRepository;
import com.codigo.sanidaddivina.domain.model.Curso;
import com.codigo.sanidaddivina.domain.port.out.CursoRepositoryPort;
import com.codigo.sanidaddivina.entities.CursoEntity;
import com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.mapper.CursoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CursoPersistenceAdapter implements CursoRepositoryPort {

    private final CursoRepository cursoRepository;
    private final EscuelaRepository escuelaRepository;
    private final ProfesorRepository profesorRepository;
    private final CursoMapper cursoMapper;

    @Override
    public Curso guardar(Curso curso) {
        CursoEntity entity = cursoMapper.toEntity(curso);
        if (curso.getEscuelaId() != null) {
            escuelaRepository.findById(curso.getEscuelaId()).ifPresent(entity::setEscuela);
        }
        if (curso.getProfesorId() != null) {
            profesorRepository.findById(curso.getProfesorId()).ifPresent(entity::setProfesor);
        }
        return cursoMapper.toDomain(cursoRepository.save(entity));
    }

    @Override
    public Optional<Curso> buscarPorId(Long id) {
        return cursoRepository.findById(id).map(cursoMapper::toDomain);
    }

    @Override
    public Page<Curso> buscarTodos(Pageable pageable) {
        return cursoRepository.findAll(pageable).map(cursoMapper::toDomain);
    }

    @Override
    public Page<Curso> buscarPorEscuela(Long escuelaId, Pageable pageable) {
        return cursoRepository.findByEscuelaIdEscuela(escuelaId, pageable).map(cursoMapper::toDomain);
    }

    @Override
    public void eliminar(Long id) {
        cursoRepository.deleteById(id);
    }
}
