package com.codigo.sanidaddivina.domain.port.in.seguridad;

import com.codigo.sanidaddivina.domain.model.Rol;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface GestionarRolUseCase {
    Rol crear(Command command);
    Optional<Rol> buscarPorId(Long id);
    Page<Rol> listar(Pageable pageable);
    Rol actualizar(Long id, Command command);
    void eliminar(Long id);

    record Command(String nombreRol, boolean estadoRol) {}
}
