package com.codigo.sanidaddivina.domain.port.out;

import com.codigo.sanidaddivina.domain.model.Profesor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProfesorRepositoryPort {
    Profesor guardar(Profesor profesor);
    Optional<Profesor> buscarPorId(Long id);
    Optional<Profesor> buscarPorMiembroId(Long miembroId);
    Page<Profesor> buscarTodos(Pageable pageable);
    void eliminar(Long id);
}
