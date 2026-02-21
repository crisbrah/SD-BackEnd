package com.codigo.sanidaddivina.domain.port.out;

import com.codigo.sanidaddivina.domain.model.Ingreso;
import com.codigo.sanidaddivina.domain.model.enums.TipoIngreso;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IngresoRepositoryPort {
    Ingreso guardar(Ingreso ingreso);
    Optional<Ingreso> buscarPorId(Long id);
    Page<Ingreso> buscarTodos(Pageable pageable);
    Page<Ingreso> buscarPorTipo(TipoIngreso tipoIngreso, Pageable pageable);
    List<Ingreso> buscarPorRangoDeFecha(LocalDate desde, LocalDate hasta);
    List<Ingreso> buscarPorMesYAnio(int mes, int anio);
    void eliminar(Long id);
}
