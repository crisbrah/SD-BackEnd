package com.codigo.sanidaddivina.business;


import com.codigo.sanidaddivina.dto.MinisterioDTO;
import com.codigo.sanidaddivina.request.MinisterioRequest;

import java.util.List;
import java.util.Optional;

public interface MinisterioService {
    MinisterioDTO crearMinisterio(MinisterioRequest ministerioRequest);
    Optional<MinisterioDTO> buscarMinisterioxId(Long id);
    Optional<List<MinisterioDTO>> buscarMinisterioxNombre(String nombre);
    List<MinisterioDTO> buscarMinisterioTodos();
    MinisterioDTO actualizarMinisterio(Long id, MinisterioRequest ministerioRequest);
    MinisterioDTO deleteMinisterio(Long id);
}
