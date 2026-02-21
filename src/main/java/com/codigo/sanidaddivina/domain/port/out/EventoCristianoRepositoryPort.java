package com.codigo.sanidaddivina.domain.port.out;

import com.codigo.sanidaddivina.domain.model.EventoCristiano;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface EventoCristianoRepositoryPort {
    EventoCristiano guardar(EventoCristiano evento);
    Optional<EventoCristiano> buscarPorId(Long id);
    Page<EventoCristiano> buscarTodos(Pageable pageable);
    void eliminar(Long id);
}
