package com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.adapter;

import com.codigo.sanidaddivina.dao.EgresoRepository;
import com.codigo.sanidaddivina.domain.model.Egreso;
import com.codigo.sanidaddivina.domain.model.enums.TipoEgreso;
import com.codigo.sanidaddivina.domain.port.out.EgresoRepositoryPort;
import com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.mapper.EgresoMapper;
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
public class EgresoPersistenceAdapter implements EgresoRepositoryPort {

    private final EgresoRepository egresoRepository;
    private final EgresoMapper egresoMapper;

    @Override
    public Egreso guardar(Egreso egreso) {
        return egresoMapper.toDomain(egresoRepository.save(egresoMapper.toEntity(egreso)));
    }

    @Override
    public Optional<Egreso> buscarPorId(Long id) {
        return egresoRepository.findById(id).map(egresoMapper::toDomain);
    }

    @Override
    public Page<Egreso> buscarTodos(Pageable pageable) {
        return egresoRepository.findAll(pageable).map(egresoMapper::toDomain);
    }

    @Override
    public Page<Egreso> buscarPorTipo(TipoEgreso tipoEgreso, Pageable pageable) {
        return egresoRepository.findByTipoEgreso(tipoEgreso, pageable).map(egresoMapper::toDomain);
    }

    @Override
    public List<Egreso> buscarPorRangoDeFecha(LocalDate desde, LocalDate hasta) {
        return egresoRepository.findByFechaEgresoBetween(desde, hasta)
                .stream().map(egresoMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Egreso> buscarPorMesYAnio(int mes, int anio) {
        return egresoRepository.findByMesYAnio(mes, anio)
                .stream().map(egresoMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public void eliminar(Long id) {
        egresoRepository.deleteById(id);
    }
}
