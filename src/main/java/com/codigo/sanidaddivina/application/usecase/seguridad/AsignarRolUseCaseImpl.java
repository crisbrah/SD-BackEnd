package com.codigo.sanidaddivina.application.usecase.seguridad;

import com.codigo.sanidaddivina.domain.model.MiembroRol;
import com.codigo.sanidaddivina.domain.port.in.seguridad.AsignarRolUseCase;
import com.codigo.sanidaddivina.domain.port.out.MiembroRepositoryPort;
import com.codigo.sanidaddivina.domain.port.out.MiembroRolRepositoryPort;
import com.codigo.sanidaddivina.domain.port.out.RolRepositoryPort;
import com.codigo.sanidaddivina.infrastructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AsignarRolUseCaseImpl implements AsignarRolUseCase {

    private final MiembroRolRepositoryPort miembroRolRepository;
    private final MiembroRepositoryPort miembroRepository;
    private final RolRepositoryPort rolRepository;

    @Override
    public MiembroRol asignar(Command command) {
        miembroRepository.buscarPorId(command.miembroId())
                .orElseThrow(() -> new ResourceNotFoundException("Miembro no encontrado con ID: " + command.miembroId()));
        rolRepository.buscarPorId(command.rolId())
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado con ID: " + command.rolId()));

        MiembroRol miembroRol = MiembroRol.builder()
                .miembroId(command.miembroId())
                .rolId(command.rolId())
                .inicioRol(command.inicioRol())
                .finRol(command.finRol())
                .build();
        return miembroRolRepository.guardar(miembroRol);
    }

    @Override
    public Optional<MiembroRol> buscarPorId(Long id) {
        return miembroRolRepository.buscarPorId(id);
    }

    @Override
    public Page<MiembroRol> buscarPorMiembro(Long miembroId, Pageable pageable) {
        return miembroRolRepository.buscarPorMiembro(miembroId, pageable);
    }

    @Override
    public void revocar(Long id) {
        miembroRolRepository.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asignación de rol no encontrada con ID: " + id));
        miembroRolRepository.eliminar(id);
    }
}
