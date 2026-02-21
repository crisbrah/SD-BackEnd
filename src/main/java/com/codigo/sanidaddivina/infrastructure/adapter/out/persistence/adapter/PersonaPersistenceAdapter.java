package com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.adapter;

import com.codigo.sanidaddivina.dao.PersonaRepository;
import com.codigo.sanidaddivina.domain.model.Persona;
import com.codigo.sanidaddivina.domain.port.out.PersonaRepositoryPort;
import com.codigo.sanidaddivina.entities.PersonaEntity;
import com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.mapper.PersonaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PersonaPersistenceAdapter implements PersonaRepositoryPort {

    private final PersonaRepository personaRepository;
    private final PersonaMapper personaMapper;

    @Override
    public Persona guardar(Persona persona) {
        PersonaEntity entity = personaMapper.toEntity(persona);
        return personaMapper.toDomain(personaRepository.save(entity));
    }

    @Override
    public Optional<Persona> buscarPorId(Long id) {
        return personaRepository.findById(id).map(personaMapper::toDomain);
    }

    @Override
    public Optional<Persona> buscarPorDni(String dni) {
        return personaRepository.findByDni(dni).map(personaMapper::toDomain);
    }

    @Override
    public Page<Persona> buscarPorNombre(String nombre, Pageable pageable) {
        return personaRepository
                .findByNombresContainingIgnoreCaseOrApePatContainingIgnoreCaseOrApeMatContainingIgnoreCase(
                        nombre, nombre, nombre, pageable)
                .map(personaMapper::toDomain);
    }

    @Override
    public Page<Persona> buscarTodos(Pageable pageable) {
        return personaRepository.findByEstado(true, pageable).map(personaMapper::toDomain);
    }

    @Override
    public List<Persona> buscarCumpleaniosHoy() {
        LocalDate hoy = LocalDate.now();
        return personaRepository.findByBirthday(hoy.getMonthValue(), hoy.getDayOfMonth())
                .stream().map(personaMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Persona> buscarCumpleaniosPorMes(int mes) {
        return personaRepository.findByBirthdayMonth(mes)
                .stream().map(personaMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Persona> buscarCumpleaniosProximos(int diasAdelante) {
        LocalDate hoy = LocalDate.now();
        LocalDate limite = hoy.plusDays(diasAdelante);

        if (hoy.getYear() == limite.getYear()) {
            return personaRepository.findBirthdaysInRange(
                            hoy.getMonthValue(), hoy.getDayOfMonth(),
                            limite.getMonthValue(), limite.getDayOfMonth())
                    .stream().map(personaMapper::toDomain).collect(Collectors.toList());
        }
        // Cruce de año: ej. 28 dic → 5 ene
        List<Persona> result = new ArrayList<>();
        result.addAll(personaRepository.findBirthdaysInRange(
                        hoy.getMonthValue(), hoy.getDayOfMonth(), 12, 31)
                .stream().map(personaMapper::toDomain).toList());
        result.addAll(personaRepository.findBirthdaysInRange(
                        1, 1, limite.getMonthValue(), limite.getDayOfMonth())
                .stream().map(personaMapper::toDomain).toList());
        return result;
    }

    @Override
    public boolean existePorDni(String dni) {
        return personaRepository.existsByDni(dni);
    }

    @Override
    public void eliminar(Long id) {
        personaRepository.findById(id).ifPresent(entity -> {
            entity.setEstado(false);
            personaRepository.save(entity);
        });
    }
}
