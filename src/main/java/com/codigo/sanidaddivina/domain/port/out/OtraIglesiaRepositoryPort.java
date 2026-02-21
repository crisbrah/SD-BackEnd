package com.codigo.sanidaddivina.domain.port.out;

import com.codigo.sanidaddivina.domain.model.OtraIglesia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OtraIglesiaRepositoryPort {
    OtraIglesia guardar(OtraIglesia otraIglesia);
    Optional<OtraIglesia> buscarPorId(Long id);
    Page<OtraIglesia> buscarTodos(Pageable pageable);
    void eliminar(Long id);
}
