package com.codigo.sanidaddivina.domain.port.out;

import com.codigo.sanidaddivina.domain.model.SedeRegistro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SedeRegistroRepositoryPort {
    SedeRegistro guardar(SedeRegistro sedeRegistro);
    Optional<SedeRegistro> buscarPorId(Long id);
    Page<SedeRegistro> buscarTodos(Pageable pageable);
    Page<SedeRegistro> buscarPorSede(Long sedeId, Pageable pageable);
    void eliminar(Long id);
}
