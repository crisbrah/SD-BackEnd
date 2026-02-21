package com.codigo.sanidaddivina.domain.port.out;

import com.codigo.sanidaddivina.domain.model.Escuela;
import com.codigo.sanidaddivina.domain.model.enums.FaseEscuela;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface EscuelaRepositoryPort {
    Escuela guardar(Escuela escuela);
    Optional<Escuela> buscarPorId(Long id);
    Page<Escuela> buscarTodos(Pageable pageable);
    List<Escuela> buscarPorFase(FaseEscuela fase);
    void eliminar(Long id);
}
