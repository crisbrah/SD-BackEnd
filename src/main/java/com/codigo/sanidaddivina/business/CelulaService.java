package com.codigo.sanidaddivina.business;

import com.codigo.sanidaddivina.dto.CelulaDTO;
import com.codigo.sanidaddivina.request.CelulaRequest;

import java.util.List;
import java.util.Optional;


public interface CelulaService {

    CelulaDTO crearCelula(CelulaRequest celulaRequest);
    Optional<CelulaDTO> buscarCelulaxId(Long id);
    Optional<List<CelulaDTO>> buscarCelulaxNombre(String nombre);
    List<CelulaDTO> buscarCelulaTodos();
    CelulaDTO actualizarCelula(Long id, CelulaRequest celulaRequest);
    CelulaDTO deleteCelula(Long id);
}
