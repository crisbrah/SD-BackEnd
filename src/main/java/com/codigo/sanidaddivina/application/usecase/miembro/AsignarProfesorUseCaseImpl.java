package com.codigo.sanidaddivina.application.usecase.miembro;

import com.codigo.sanidaddivina.domain.model.Profesor;
import com.codigo.sanidaddivina.domain.port.in.miembro.AsignarProfesorUseCase;
import com.codigo.sanidaddivina.domain.port.out.MiembroRepositoryPort;
import com.codigo.sanidaddivina.domain.port.out.ProfesorRepositoryPort;
import com.codigo.sanidaddivina.infrastructure.exception.BusinessException;
import com.codigo.sanidaddivina.infrastructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class AsignarProfesorUseCaseImpl implements AsignarProfesorUseCase {

    private final MiembroRepositoryPort miembroRepository;
    private final ProfesorRepositoryPort profesorRepository;

    @Override
    public Profesor ejecutar(Long miembroId) {
        miembroRepository.buscarPorId(miembroId)
                .orElseThrow(() -> new ResourceNotFoundException("Miembro no encontrado con ID: " + miembroId));

        if (profesorRepository.buscarPorMiembroId(miembroId).isPresent()) {
            throw new BusinessException("El miembro ya está asignado como profesor");
        }

        Profesor profesor = Profesor.builder()
                .miembroId(miembroId)
                .estadoProfesor(true)
                .fechaAsignacion(LocalDate.now())
                .build();

        return profesorRepository.guardar(profesor);
    }
}
