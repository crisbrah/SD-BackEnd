package com.codigo.sanidaddivina.domain.port.out;

import com.codigo.sanidaddivina.domain.model.Sede;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SedeRepositoryPort {
    Sede guardar(Sede sede);
    Optional<Sede> buscarPorId(Long id);
    Page<Sede> buscarTodos(Pageable pageable);
    void eliminar(Long id);
}
