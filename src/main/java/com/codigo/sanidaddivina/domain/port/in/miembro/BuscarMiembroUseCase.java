package com.codigo.sanidaddivina.domain.port.in.miembro;

import com.codigo.sanidaddivina.domain.model.Miembro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BuscarMiembroUseCase {
    Optional<Miembro> porId(Long id);
    Page<Miembro> todos(Pageable pageable);
}
