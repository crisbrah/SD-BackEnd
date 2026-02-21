package com.codigo.sanidaddivina.domain.port.out;

import com.codigo.sanidaddivina.domain.model.MiembroRol;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MiembroRolRepositoryPort {
    MiembroRol guardar(MiembroRol miembroRol);
    Optional<MiembroRol> buscarPorId(Long id);
    Page<MiembroRol> buscarPorMiembro(Long miembroId, Pageable pageable);
    void eliminar(Long id);
}
