package com.codigo.sanidaddivina.domain.port.out;

import com.codigo.sanidaddivina.domain.model.Celula;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CelulaRepositoryPort {
    Celula guardar(Celula celula);
    Optional<Celula> buscarPorId(Long id);
    Page<Celula> buscarTodos(Pageable pageable);
    void eliminar(Long id);
}
