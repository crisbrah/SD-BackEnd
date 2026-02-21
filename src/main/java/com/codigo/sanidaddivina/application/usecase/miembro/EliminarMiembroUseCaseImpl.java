package com.codigo.sanidaddivina.application.usecase.miembro;

import com.codigo.sanidaddivina.domain.port.in.miembro.EliminarMiembroUseCase;
import com.codigo.sanidaddivina.domain.port.out.MiembroRepositoryPort;
import com.codigo.sanidaddivina.infrastructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class EliminarMiembroUseCaseImpl implements EliminarMiembroUseCase {

    private final MiembroRepositoryPort miembroRepository;

    @Override
    public void ejecutar(Long id) {
        miembroRepository.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Miembro no encontrado con ID: " + id));
        miembroRepository.eliminar(id);
    }
}
