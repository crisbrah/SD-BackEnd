package com.codigo.sanidaddivina.business.implement;


import com.codigo.sanidaddivina.business.RolService;
import com.codigo.sanidaddivina.dao.RolRepository;
import com.codigo.sanidaddivina.dto.RolDTO;
import com.codigo.sanidaddivina.entities.RolEntity;
import com.codigo.sanidaddivina.request.RolRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RolAdapter implements RolService {
    @Autowired
    private RolRepository rolRepository;


    @Override
    public RolDTO crearRol(RolRequest rolRequest) {
        RolEntity entity = new RolEntity();

        // Configurar los campos necesarios de la entidad
        entity.setNombreRol(rolRequest.getNombreRol());
        entity.setEstadoRol(true);
        entity.setUsuaCrea("Jhoner");
        entity.setDateCreate(getTimestamp());

        // Guardar la entidad usando el repositorio
        RolEntity savedEntity = rolRepository.save(entity);

        // Convertir la entidad guardada a DTO utilizando el mapeador
        return RolDTO.fromEntity(savedEntity);
    }

    private Timestamp getTimestamp(){
        long currenTIme = System.currentTimeMillis();
        return new Timestamp(currenTIme);
    }



    @Override
    public Optional<RolDTO> buscarRolxId(Long id) {

            Optional<RolEntity> optionalEntity = rolRepository.findById(id);
            if (optionalEntity.isPresent()) {
                RolEntity entity = optionalEntity.get();
                RolDTO rolDTO = RolDTO.fromEntity(entity);

                return Optional.of(rolDTO);
            } else {
                // Manejar el caso cuando no se encuentra el rol
                return Optional.empty();
            }

    }

    @Override
    public Optional<List<RolDTO>> buscarRolxNombre(String nombreRol) {

            // Si no existe en Redis, buscar en la base de datos
            List<RolEntity> entities = rolRepository.findByNombreRolContainingIgnoreCase(nombreRol);

            if (!entities.isEmpty()) {
                // Convertir la lista de entidades a una lista de DTOs
                List<RolDTO> rolDTOs = entities.stream()
                        .map(RolDTO::fromEntity)
                        .collect(Collectors.toList());



                return Optional.of(rolDTOs);
            } else {
                // Si no se encuentra en la base de datos, devolver Optional vacío
                return Optional.empty();
            }

    }

    @Override
    public List<RolDTO> buscarRolTodos() {
        List<RolDTO> listaDto = new ArrayList<>();
        List<RolEntity> entidades = rolRepository.findAll();
        for (RolEntity dato :entidades){
            listaDto.add(RolDTO.fromEntity(dato));
        }
        return listaDto;
    }

    @Override
    public RolDTO actualizarRol(Long id, RolRequest rolRequest) {
        Optional<RolEntity> datoExtraido = rolRepository.findById(id);
        RolEntity entity = new RolEntity();
        if (datoExtraido.isPresent()) {
            entity.setIdRol(id);
            RolEntity existingEntity = datoExtraido.get();
            entity.setNombreRol(rolRequest.getNombreRol());
            entity.setEstadoRol(true);
            entity.setUsuaModif("jhpner");
            entity.setDateModif(getTimestamp());
            // Conservar los valores existentes de usuaCrea y dateCreate
            entity.setUsuaCrea(existingEntity.getUsuaCrea());
            entity.setDateCreate(existingEntity.getDateCreate());
            return RolDTO.fromEntity(rolRepository.save(entity));
        } else {
            throw new ObjetoNoEncontradaException("No se encontró el rol con el ID: " + id);
        }
    }

    @Override
    public RolDTO deleteRol(Long id) {
        Optional<RolEntity> datoExtraido = rolRepository.findById(id);
        if(datoExtraido.isPresent()){
            datoExtraido.get().setEstadoRol(false);
            datoExtraido.get().setUsuaDelet("jhom");
            datoExtraido.get().setDateDelet(getTimestamp());
            return RolDTO.fromEntity(rolRepository.save(datoExtraido.get()));
        }else {
            throw new ObjetoNoEncontradaException("No se encontró el rol con el ID: " + id);
        }
    }
}