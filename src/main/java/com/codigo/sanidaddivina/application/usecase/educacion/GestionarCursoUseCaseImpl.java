package com.codigo.sanidaddivina.application.usecase.educacion;

import com.codigo.sanidaddivina.domain.model.Curso;
import com.codigo.sanidaddivina.domain.port.in.educacion.GestionarCursoUseCase;
import com.codigo.sanidaddivina.domain.port.out.CursoRepositoryPort;
import com.codigo.sanidaddivina.infrastructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class GestionarCursoUseCaseImpl implements GestionarCursoUseCase {

    private final CursoRepositoryPort cursoRepository;

    @Override
    public Curso crear(Command command) {
        Curso curso = Curso.builder()
                .nombreCurso(command.nombreCurso())
                .fechaInicio(command.fechaInicio())
                .fechaFin(command.fechaFin())
                .fechaCreacion(LocalDate.now())
                .estadoCurso(true)
                .esPago(command.esPago())
                .costo(command.costo())
                .escuelaId(command.escuelaId())
                .profesorId(command.profesorId())
                .build();
        return cursoRepository.guardar(curso);
    }

    @Override
    public Curso actualizar(Long id, Command command) {
        Curso curso = cursoRepository.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con ID: " + id));
        curso.setNombreCurso(command.nombreCurso());
        curso.setFechaInicio(command.fechaInicio());
        curso.setFechaFin(command.fechaFin());
        curso.setEsPago(command.esPago());
        curso.setCosto(command.costo());
        curso.setEscuelaId(command.escuelaId());
        curso.setProfesorId(command.profesorId());
        return cursoRepository.guardar(curso);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> buscarPorId(Long id) {
        return cursoRepository.buscarPorId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Curso> buscarTodos(Pageable pageable) {
        return cursoRepository.buscarTodos(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Curso> buscarPorEscuela(Long escuelaId, Pageable pageable) {
        return cursoRepository.buscarPorEscuela(escuelaId, pageable);
    }

    @Override
    public void eliminar(Long id) {
        cursoRepository.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con ID: " + id));
        cursoRepository.eliminar(id);
    }
}
