package com.codigo.sanidaddivina.domain.port.out;

import com.codigo.sanidaddivina.domain.model.Miembro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MiembroRepositoryPort {
    Miembro guardar(Miembro miembro);
    Optional<Miembro> buscarPorId(Long id);
    Optional<Miembro> buscarPorEmail(String email);
    Optional<Miembro> buscarPorPersonaId(Long personaId);
    Page<Miembro> buscarTodos(Pageable pageable);
    boolean existePorEmail(String email);
    void eliminar(Long id);
}
