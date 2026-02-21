package com.codigo.sanidaddivina.business.implement;

import com.codigo.sanidaddivina.business.MinisterioService;
import com.codigo.sanidaddivina.dao.MinisterioRepository;
import com.codigo.sanidaddivina.dto.MinisterioDTO;
import com.codigo.sanidaddivina.entities.MinisterioEntity;
import com.codigo.sanidaddivina.request.MinisterioRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MinisterioAdapter implements MinisterioService {

    @Autowired
    private MinisterioRepository ministerioRepository;

    @Override
    public MinisterioDTO crearMinisterio(MinisterioRequest ministerioRequest) {
        // Crear una nueva instancia de CelulaEntity
        MinisterioEntity entity = new MinisterioEntity();

        // Configurar los campos necesarios de la entidad
        entity.setNombreMinisterio(ministerioRequest.getNombreMinisterio());
        entity.setEstadoMinisterio(true);
        entity.setUsuaCrea("Jhoner");
        entity.setDateCreate(getTimestamp());

        // Guardar la entidad usando el repositorio
        MinisterioEntity savedEntity = ministerioRepository.save(entity);

        // Convertir la entidad guardada a DTO utilizando el mapeador
        return MinisterioDTO.fromEntity(savedEntity);
    }

    private Timestamp getTimestamp(){
        long currenTIme = System.currentTimeMillis();
        return new Timestamp(currenTIme);
    }

    @Override
    public Optional<MinisterioDTO> buscarMinisterioxId(Long id) {

            Optional<MinisterioEntity> optionalEntity = ministerioRepository.findById(id);
            if (optionalEntity.isPresent()) {
                MinisterioEntity entity = optionalEntity.get();
                MinisterioDTO ministerioDTO = MinisterioDTO.fromEntity(entity);

                return Optional.of(ministerioDTO);
            } else {
                // Manejar el caso cuando no se encuentra el ministerio
                return Optional.empty();
            }

    }

    @Override
    public Optional<List<MinisterioDTO>> buscarMinisterioxNombre(String nombre) {

            List<MinisterioEntity> entities = ministerioRepository.findByNombreMinisterioContainingIgnoreCase(nombre);
            if (!entities.isEmpty()) {

                List<MinisterioDTO> ministerioDTOs = entities.stream()
                        .map(MinisterioDTO::fromEntity)
                        .collect(Collectors.toList());

                   return Optional.of(ministerioDTOs);
            } else {
                // Si no se encuentra en la base de datos, devolver Optional vacío
                return Optional.empty();
            }
        }

    @Override
    public List<MinisterioDTO> buscarMinisterioTodos() {
        return List.of();
    }

    @Override
    public MinisterioDTO actualizarMinisterio(Long id, MinisterioRequest ministerioRequest) {
        return null;
    }


    @Override
    public MinisterioDTO deleteMinisterio(Long id) {
        return null;
    }
}

  /*  @Override
    public List<MinisterioDTO> buscarMinisterioTodos() {
        List<MinisterioDTO> listaDto = new ArrayList<>();
        List<MinisterioEntity> entidades = ministerioRepository.findAll();
        for (MinisterioEntity dato :entidades){
            listaDto.add(MinisterioDTO.fromEntity(dato));
        }
        return listaDto;
    }

    @Override
    public MinisterioDTO actualizarMinisterio(Long id, MinisterioRequest ministerioRequest) {

        Optional<MinisterioEntity> datoExtraido =  ministerioRepository.findById(id);
        MinisterioEntity entity = new MinisterioEntity();
        if (datoExtraido.isPresent()) {
            entity.setIdMinisterio(id);
            MinisterioEntity existingEntity = datoExtraido.get();
            entity.setNombreMinisterio(ministerioRequest.getNombreMinisterio());
            entity.setEstadoMinisterio(true);
            entity.setUsuaModif("Jhoner");
            entity.setDateModif(getTimestamp());
            // Conservar los valores existentes de usuaCrea y dateCreate
            entity.setUsuaCrea(existingEntity.getUsuaCrea());
            entity.setDateCreate(existingEntity.getDateCreate());
            return MinisterioDTO.fromEntity(ministerioRepository.save(entity));
        } else {
            throw new ObjetoNoEncontradaException("No se encontró el ministerio con el ID: " + id);
        }
    }

    @Override
    public MinisterioDTO deleteMinisterio(Long id) {
        Optional<MinisterioEntity> datoExtraido = ministerioRepository.findById(id);
        if(datoExtraido.isPresent()){
            datoExtraido.get().setEstadoMinisterio(Constant.STATUS_DESACTIVE);
            datoExtraido.get().setUsuaDelet(Constant.USU_ADMIN);
            datoExtraido.get().setDateDelet(getTimestamp());
            return MinisterioMapper.fromEntity(ministerioRepository.save(datoExtraido.get()));
        }else {
            throw new ObjetoNoEncontradaException("No se encontró el ministerio con el ID: " + id);
        }
    }
}*/
