package com.codigo.sanidaddivina.domain.port.out;

import com.codigo.sanidaddivina.domain.model.MiembroMinisterio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MiembroMinisterioRepositoryPort {
    MiembroMinisterio guardar(MiembroMinisterio miembroMinisterio);
    Optional<MiembroMinisterio> buscarPorId(Long id);
    Page<MiembroMinisterio> buscarPorMinisterio(Long ministerioId, Pageable pageable);
    Page<MiembroMinisterio> buscarPorMiembro(Long miembroId, Pageable pageable);
    void eliminar(Long id);
}
