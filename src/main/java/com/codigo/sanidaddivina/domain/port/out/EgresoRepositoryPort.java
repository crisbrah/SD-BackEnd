package com.codigo.sanidaddivina.domain.port.out;

import com.codigo.sanidaddivina.domain.model.Egreso;
import com.codigo.sanidaddivina.domain.model.enums.TipoEgreso;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EgresoRepositoryPort {
    Egreso guardar(Egreso egreso);
    Optional<Egreso> buscarPorId(Long id);
    Page<Egreso> buscarTodos(Pageable pageable);
    Page<Egreso> buscarPorTipo(TipoEgreso tipoEgreso, Pageable pageable);
    List<Egreso> buscarPorRangoDeFecha(LocalDate desde, LocalDate hasta);
    List<Egreso> buscarPorMesYAnio(int mes, int anio);
    void eliminar(Long id);
}
