package com.codigo.sanidaddivina.domain.port.out;

import com.codigo.sanidaddivina.domain.model.Rol;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RolRepositoryPort {
    Rol guardar(Rol rol);
    Optional<Rol> buscarPorId(Long id);
    Page<Rol> buscarTodos(Pageable pageable);
    void eliminar(Long id);
}
