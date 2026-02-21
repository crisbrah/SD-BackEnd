package com.codigo.sanidaddivina.domain.port.in.financiero;

import com.codigo.sanidaddivina.domain.model.Egreso;
import com.codigo.sanidaddivina.domain.model.Ingreso;
import com.codigo.sanidaddivina.domain.model.enums.TipoEgreso;
import com.codigo.sanidaddivina.domain.model.enums.TipoIngreso;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BuscarFinancieroUseCase {
    Optional<Ingreso> ingresoPorId(Long id);
    Page<Ingreso> ingresosTodos(Pageable pageable);
    Page<Ingreso> ingresosPorTipo(TipoIngreso tipo, Pageable pageable);
    List<Ingreso> ingresosPorMes(int mes, int anio);

    Optional<Egreso> egresoPorId(Long id);
    Page<Egreso> egresosTodos(Pageable pageable);
    Page<Egreso> egresosPorTipo(TipoEgreso tipo, Pageable pageable);
    List<Egreso> egresosPorMes(int mes, int anio);
}
