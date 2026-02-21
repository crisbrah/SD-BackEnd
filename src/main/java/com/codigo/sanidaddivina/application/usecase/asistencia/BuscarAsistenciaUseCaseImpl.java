package com.codigo.sanidaddivina.application.usecase.asistencia;

import com.codigo.sanidaddivina.domain.model.Asistencia;
import com.codigo.sanidaddivina.domain.port.in.asistencia.BuscarAsistenciaUseCase;
import com.codigo.sanidaddivina.domain.port.out.AsistenciaRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BuscarAsistenciaUseCaseImpl implements BuscarAsistenciaUseCase {

    private final AsistenciaRepositoryPort asistenciaRepository;

    @Override
    public Page<Asistencia> todas(Pageable pageable) {
        return asistenciaRepository.buscarTodos(pageable);
    }

    @Override
    public List<Asistencia> porPersona(Long personaId) {
        return asistenciaRepository.buscarPorPersona(personaId);
    }

    @Override
    public List<Asistencia> porFecha(LocalDate fecha) {
        return asistenciaRepository.buscarPorFecha(fecha);
    }

    @Override
    public Page<Asistencia> porSesion(Long sesionId, Pageable pageable) {
        return asistenciaRepository.buscarPorSesion(sesionId, pageable);
    }
}
