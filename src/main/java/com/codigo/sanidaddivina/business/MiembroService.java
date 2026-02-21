package com.codigo.sanidaddivina.business;


import com.codigo.sanidaddivina.dto.MiembroDTO;
import com.codigo.sanidaddivina.request.MiembroRequest;

import java.util.List;
import java.util.Optional;

public interface MiembroService {
    MiembroDTO crearMiembro(MiembroRequest miembroRequest);
    Optional<MiembroDTO> buscarMiembroxId(Long id);
    List<MiembroDTO> buscarMiembroTodos();
    MiembroDTO actualizarMiembro(Long id, MiembroRequest miembroRequest);
    MiembroDTO deleteMiembro(Long id);
}
