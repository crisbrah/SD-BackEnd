package com.codigo.sanidaddivina.business;


import com.codigo.sanidaddivina.dto.IngresoDTO;
import com.codigo.sanidaddivina.request.IngresoRequest;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IngresoService {
    IngresoDTO crearIngreso(IngresoRequest ingresoRequest);
    Optional<IngresoDTO> buscarIngresoxId(Long id);
    Optional<List<IngresoDTO>> buscarIngresoxFecha(Date fecha);
    List<IngresoDTO> buscarIngresoTodos();
    IngresoDTO actualizarIngreso(Long id, IngresoRequest ingresoRequest);
    IngresoDTO deleteIngreso(Long id);
}
