package com.codigo.sanidaddivina.business;


import com.codigo.sanidaddivina.dto.RolDTO;
import com.codigo.sanidaddivina.request.RolRequest;

import java.util.List;
import java.util.Optional;

public interface RolService {
    RolDTO crearRol(RolRequest rolRequest);
    Optional<RolDTO> buscarRolxId(Long id);
    Optional<List<RolDTO>> buscarRolxNombre(String nombre);
    List<RolDTO> buscarRolTodos();
    RolDTO actualizarRol(Long id, RolRequest rolRequest);
    RolDTO deleteRol(Long id);
}
