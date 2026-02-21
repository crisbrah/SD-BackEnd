package com.codigo.sanidaddivina.domain.port.out;

import com.codigo.sanidaddivina.domain.model.Ministerio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MinisterioRepositoryPort {
    Ministerio guardar(Ministerio ministerio);
    Optional<Ministerio> buscarPorId(Long id);
    Page<Ministerio> buscarTodos(Pageable pageable);
    void eliminar(Long id);
}
