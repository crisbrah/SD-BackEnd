package com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.adapter;

import com.codigo.sanidaddivina.dao.IngresoRepository;
import com.codigo.sanidaddivina.domain.model.Ingreso;
import com.codigo.sanidaddivina.domain.model.enums.TipoIngreso;
import com.codigo.sanidaddivina.domain.port.out.IngresoRepositoryPort;
import com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.mapper.IngresoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class IngressoPersistenceAdapter implements IngresoRepositoryPort {

    private final IngresoRepository ingresoRepository;
    private final IngresoMapper ingresoMapper;

    @Override
    public Ingreso guardar(Ingreso ingreso) {
        return ingresoMapper.toDomain(ingresoRepository.save(ingresoMapper.toEntity(ingreso)));
    }

    @Override
    public Optional<Ingreso> buscarPorId(Long id) {
        return ingresoRepository.findById(id).map(ingresoMapper::toDomain);
    }

    @Override
    public Page<Ingreso> buscarTodos(Pageable pageable) {
        return ingresoRepository.findAll(pageable).map(ingresoMapper::toDomain);
    }

    @Override
    public Page<Ingreso> buscarPorTipo(TipoIngreso tipoIngreso, Pageable pageable) {
        return ingresoRepository.findByTipoIngreso(tipoIngreso, pageable).map(ingresoMapper::toDomain);
    }

    @Override
    public List<Ingreso> buscarPorRangoDeFecha(LocalDate desde, LocalDate hasta) {
        return ingresoRepository.findByFechaIngresoBetween(desde, hasta)
                .stream().map(ingresoMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Ingreso> buscarPorMesYAnio(int mes, int anio) {
        return ingresoRepository.findByMesYAnio(mes, anio)
                .stream().map(ingresoMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public void eliminar(Long id) {
        ingresoRepository.deleteById(id);
    }
}
