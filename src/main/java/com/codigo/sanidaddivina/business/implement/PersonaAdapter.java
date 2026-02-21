package com.codigo.sanidaddivina.business.implement;

import com.codigo.sanidaddivina.client.ClientReniec;
import com.codigo.sanidaddivina.dao.PersonaRepository;
import com.codigo.sanidaddivina.dto.PersonaDTO;
import com.codigo.sanidaddivina.dto.ReniecDTO;
import com.codigo.sanidaddivina.entities.PersonaEntity;
import com.codigo.sanidaddivina.request.PersonaRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class PersonaAdapter implements PersonaService {
    @Autowired
    private PersonaRepository personaRepository;
    @Autowired
    private ClientReniec clientReniec;

    @Value("${token.reniec}")
    private String tokenReniec;

    @Override
    public PersonaDTO crearPersona(PersonaRequest personaRequest) {
        // Verificar si ya existe una persona con el mismo dni
        if (personaRepository.existsByDni(personaRequest.getDni())) {
            throw new DataIntegrityViolationException("DNI duplicado: " + personaRequest.getDni());
        }
        PersonaEntity personaEntity = getEntity(personaRequest, 1, null);

        return PersonaDTO.fromEntity(personaRepository.save(personaEntity));
    }

    private PersonaEntity getEntity(PersonaRequest personaRequest, int actuar, Long id){
        //Exec servicio
        ReniecDTO reniecDto = getExecReniec(personaRequest.getDni());
        PersonaEntity entity = new PersonaEntity();
        if (actuar==1)
        {
            entity.setNombres(reniecDto.getNombres());
            entity.setApePat(reniecDto.getApellidoPaterno());
            entity.setApeMat(reniecDto.getApellidoMaterno());
            entity.setFechaNacimiento(personaRequest.getFechaNacimiento());
            entity.setLugarNacimiento(personaRequest.getLugarNacimiento());
            entity.setEsCivil(personaRequest.getEsCivil());
            entity.setNumHijos(personaRequest.getNumHijos());
            entity.setDni(personaRequest.getDni());
            entity.setOcupacion(personaRequest.getOcupacion());
            entity.setDireccion(personaRequest.getDireccion());
            entity.setDistrito(personaRequest.getDistrito());
            entity.setProvincia(personaRequest.getProvincia());
            entity.setDepartamento(personaRequest.getDepartamento());
            entity.setEstado(true);
            //Datos de auditoria donde corresponda
            entity.setUsuaCrea("Jhoner");


        }
        else if (actuar==2)
        {
            entity.setIdPersona(id);
            entity.setNombres(reniecDto.getNombres());
            entity.setApePat(reniecDto.getApellidoPaterno());
            entity.setApeMat(reniecDto.getApellidoMaterno());
            entity.setFechaNacimiento(personaRequest.getFechaNacimiento());
            entity.setLugarNacimiento(personaRequest.getLugarNacimiento());
            entity.setEsCivil(personaRequest.getEsCivil());
            entity.setNumHijos(personaRequest.getNumHijos());
            entity.setDni(personaRequest.getDni());
            entity.setOcupacion(personaRequest.getOcupacion());
            entity.setDireccion(personaRequest.getDireccion());
            entity.setDistrito(personaRequest.getDistrito());
            entity.setProvincia(personaRequest.getProvincia());
            entity.setDepartamento(personaRequest.getDepartamento());
            entity.setEstado(true);
            entity.setUsuaModif("Jhoner");
        }

        return entity;

    }

    private ReniecDTO getExecReniec(String dni){
        String authorization = "Bearer "+tokenReniec;
        return clientReniec.getInfoReniec(dni,authorization);
    }
    private Timestamp getTimestamp(){
        long currenTIme = System.currentTimeMillis();
        return new Timestamp(currenTIme);
    }
    @Override
    public Optional<PersonaDTO> buscarPersonaxId(Long id) {
       Optional<PersonaEntity> optionalEntity = personaRepository.findById(id);
            if (optionalEntity.isPresent()) {
                PersonaEntity entity = optionalEntity.get();
                PersonaDTO personaDto = PersonaDTO.fromEntity(entity);
                return Optional.of(personaDto);
            } else {
                // Manejar el caso cuando no se encuentra la persona
                return Optional.empty();
            }

    }
    @Override
    public List<PersonaDTO> buscarPersonaxIdDni(String dni) {
        return personaRepository.findByDni(dni)
                .map(entity -> List.of(PersonaDTO.fromEntity(entity)))
                .orElse(Collections.emptyList());
    }

    @Override
    public Optional<List<PersonaDTO>> buscarPersonaxNombre(String nombres) {

            // Si no existe en Redis, buscar en la base de datos
            List<PersonaEntity> entities = personaRepository.findByNombresContainingIgnoreCase(nombres);

            if (!entities.isEmpty()) {
                // Convertir la lista de entidades a una lista de DTOs
                List<PersonaDTO> personaDTOs = entities.stream()
                        .map(PersonaDTO::fromEntity)
                        .collect(Collectors.toList());

                 return Optional.of(personaDTOs);
            } else {
                // Si no se encuentra en la base de datos, devolver Optional vacío
                return Optional.empty();
            }

    }

    @Override
    public List<PersonaDTO> buscarPersonaTodos() {
        List<PersonaDTO> listaDto = new ArrayList<>();
        List<PersonaEntity> entidades = personaRepository.findAll();
        for (PersonaEntity dato :entidades){
            listaDto.add(PersonaDTO.fromEntity(dato));
        }
        return listaDto;
    }

    @Override
    public PersonaDTO actualizarPersona(Long id, PersonaRequest personaRequest) {
        Optional<PersonaEntity> datoExtraido = personaRepository.findById(id);
        if (datoExtraido.isPresent()) {
            PersonaEntity existingEntity = datoExtraido.get();
            PersonaEntity updatedEntity = getEntity(personaRequest, 2, id);
            // Conservar los valores existentes de usuaCrea y dateCreate
            updatedEntity.setUsuaCrea(existingEntity.getUsuaCrea());
            updatedEntity.setDateCreate(existingEntity.getDateCreate());
            return PersonaDTO.fromEntity(personaRepository.save(updatedEntity));
        } else {
            throw new ObjetoNoEncontradaException("No se encontró la persona con el ID: " + id);
        }
    }
    @Override
    public PersonaDTO deletePersona(Long id) {
        Optional<PersonaEntity> datoExtraido = personaRepository.findById(id);
        if(datoExtraido.isPresent()){
            datoExtraido.get().setUsuaDelet("Jhoner");
            datoExtraido.get().setEstado(false);
            datoExtraido.get().setDateDelet(getTimestamp());

            return PersonaDTO.fromEntity(personaRepository.save(datoExtraido.get()));
        }else {
            throw new ObjetoNoEncontradaException("No se encontró la persona con el ID: " + id);
        }
    }

}
