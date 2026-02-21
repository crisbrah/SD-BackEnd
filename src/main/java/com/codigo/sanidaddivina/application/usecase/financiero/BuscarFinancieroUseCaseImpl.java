package com.codigo.sanidaddivina.application.usecase.financiero;

import com.codigo.sanidaddivina.domain.model.Egreso;
import com.codigo.sanidaddivina.domain.model.Ingreso;
import com.codigo.sanidaddivina.domain.model.enums.TipoEgreso;
import com.codigo.sanidaddivina.domain.model.enums.TipoIngreso;
import com.codigo.sanidaddivina.domain.port.in.financiero.BuscarFinancieroUseCase;
import com.codigo.sanidaddivina.domain.port.out.EgresoRepositoryPort;
import com.codigo.sanidaddivina.domain.port.out.IngresoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BuscarFinancieroUseCaseImpl implements BuscarFinancieroUseCase {

    private final IngresoRepositoryPort ingresoRepository;
    private final EgresoRepositoryPort egresoRepository;

    @Override
    public Optional<Ingreso> ingresoPorId(Long id) {
        return ingresoRepository.buscarPorId(id);
    }

    @Override
    public Page<Ingreso> ingresosTodos(Pageable pageable) {
        return ingresoRepository.buscarTodos(pageable);
    }

    @Override
    public Page<Ingreso> ingresosPorTipo(TipoIngreso tipo, Pageable pageable) {
        return ingresoRepository.buscarPorTipo(tipo, pageable);
    }

    @Override
    public List<Ingreso> ingresosPorMes(int mes, int anio) {
        return ingresoRepository.buscarPorMesYAnio(mes, anio);
    }

    @Override
    public Optional<Egreso> egresoPorId(Long id) {
        return egresoRepository.buscarPorId(id);
    }

    @Override
    public Page<Egreso> egresosTodos(Pageable pageable) {
        return egresoRepository.buscarTodos(pageable);
    }

    @Override
    public Page<Egreso> egresosPorTipo(TipoEgreso tipo, Pageable pageable) {
        return egresoRepository.buscarPorTipo(tipo, pageable);
    }

    @Override
    public List<Egreso> egresosPorMes(int mes, int anio) {
        return egresoRepository.buscarPorMesYAnio(mes, anio);
    }
}
