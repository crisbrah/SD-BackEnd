package com.codigo.sanidaddivina.application.usecase.seguridad;

import com.codigo.sanidaddivina.domain.model.Rol;
import com.codigo.sanidaddivina.domain.port.in.seguridad.GestionarRolUseCase;
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
public class GestionarRolUseCaseImpl implements GestionarRolUseCase {

    private final RolRepositoryPort rolRepository;

    @Override
    public Rol crear(Command command) {
        Rol rol = Rol.builder()
                .nombreRol(command.nombreRol())
                .estadoRol(command.estadoRol())
                .build();
        return rolRepository.guardar(rol);
    }

    @Override
    public Optional<Rol> buscarPorId(Long id) {
        return rolRepository.buscarPorId(id);
    }

    @Override
    public Page<Rol> listar(Pageable pageable) {
        return rolRepository.buscarTodos(pageable);
    }

    @Override
    public Rol actualizar(Long id, Command command) {
        Rol rol = rolRepository.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado con ID: " + id));
        rol.setNombreRol(command.nombreRol());
        rol.setEstadoRol(command.estadoRol());
        return rolRepository.guardar(rol);
    }

    @Override
    public void eliminar(Long id) {
        rolRepository.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado con ID: " + id));
        rolRepository.eliminar(id);
    }
}
