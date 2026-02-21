package com.codigo.sanidaddivina.business.implement;

import com.codigo.sanidaddivina.business.CelulaService;
import com.codigo.sanidaddivina.dao.CelulaRepository;
import com.codigo.sanidaddivina.dto.CelulaDTO;
import com.codigo.sanidaddivina.entities.CelulaEntity;
import com.codigo.sanidaddivina.request.CelulaRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CelulaAdapter implements CelulaService {
    @Autowired
    private CelulaRepository celulaRepository;

    @Override
    public CelulaDTO crearCelula(CelulaRequest celulaRequest) {
/*        // Crear una nueva instancia de CelulaEntity
        CelulaEntity entity = new CelulaEntity();

        // Configurar los campos necesarios de la entidad
        entity.setNombreCelula(celulaRequest.getNombreCelula());
        entity.setEstadoCelula(Constant.STATUS_ACTIVE);
        entity.setUsuaCrea(Constant.USU_ADMIN);
        entity.setDateCreate(getTimestamp());

        // Guardar la entidad usando el repositorio
        CelulaEntity savedEntity = celulaRepository.save(entity);

        // Convertir la entidad guardada a DTO utilizando el mapeador
        //return CelulaMapper.fromEntity(savedEntity);

        return CelulaDTO.fromEntity(savedEntity);*/
        log.info("Iniciando creación de célula con nombre: {}", celulaRequest.getNombreCelula());

        CelulaEntity entity = new CelulaEntity();
        entity.setNombreCelula(celulaRequest.getNombreCelula());
        entity.setEstadoCelula(true);
        entity.setUsuaCrea("Jhoner perez");

        CelulaEntity savedEntity = celulaRepository.save(entity);
        log.info("Célula creada con ID: {}", savedEntity.getIdCelula());

        return CelulaDTO.fromEntity(savedEntity);
    }

    Timestamp getTimestamp(){
        long currenTIme = System.currentTimeMillis();
        return new Timestamp(currenTIme);
    }

    @Override
    public Optional<CelulaDTO> buscarCelulaxId(Long id) {

        Optional<CelulaEntity> optionalEntity = celulaRepository.findById(id);
        if (optionalEntity.isPresent()) {
            CelulaEntity entity = optionalEntity.get();
            CelulaDTO celulaDTO = CelulaDTO.fromEntity(entity);

            return Optional.of(celulaDTO);
        } else {
            // Manejar el caso cuando no se encuentra la entidad
            return Optional.empty();
        }

    }

    @Override
    public Optional<List<CelulaDTO>> buscarCelulaxNombre(String nombre) {
        // buscar en la base de datos
        List<CelulaEntity> entities = celulaRepository.findByNombreCelulaContainingIgnoreCase(nombre);

        if (!entities.isEmpty()) {
            // Convertir la lista de entidades a una lista de DTOs
            List<CelulaDTO> celulaDTOs = entities.stream()
                    .map(CelulaDTO::fromEntity)
                    .collect(Collectors.toList());


            return Optional.of(celulaDTOs);
        } else {
            // Si no se encuentra en la base de datos, devolver Optional vacío
            return Optional.empty();
        }

    }

/*    @Override
    public List<CelulaDTO> buscarCelulaTodosOut() {
        List<CelulaDTO> listaDto = new ArrayList<>();
        List<CelulaEntity> entidades = celulaRepository.findAll();
        for (CelulaEntity dato :entidades){
            listaDto.add(CelulaDTO.fromEntity(dato));
        }
        return listaDto;
    }*/

    @Override
    public List<CelulaDTO> buscarCelulaTodos() {
        log.info("=== Iniciando buscarCelulaTodosOut en Adapter ===");
        try {
            List<CelulaEntity> entidades = celulaRepository.findAll();
            log.info("Entidades encontradas en BD: {}",
                    entidades != null ? entidades.size() : "null");

            if (entidades == null || entidades.isEmpty()) {
                log.warn("No se encontraron entidades en la BD");
                return Collections.emptyList();
            }

            // Log de la primera entidad
            log.info("Primera entidad: {}", entidades.get(0));

            List<CelulaDTO> listaDto = entidades.stream()
                    .map(CelulaDTO::fromEntity)
                    .collect(Collectors.toList());

            log.info("DTOs convertidos: {}", listaDto.size());
            return listaDto;

        } catch (Exception e) {
            log.error("Error en buscarCelulaTodosOut: ", e);
            throw e;
        }
    }

    @Override
    public CelulaDTO actualizarCelula(Long id, CelulaRequest celulaRequest) {
        Optional<CelulaEntity> datoExtraido = celulaRepository.findById(id);
        CelulaEntity entity = new CelulaEntity();
        if (datoExtraido.isPresent()) {
            entity.setIdCelula(id);
            CelulaEntity existingEntity = datoExtraido.get();
            entity.setNombreCelula(celulaRequest.getNombreCelula());
            entity.setEstadoCelula(true);
            entity.setUsuaModif("Constant.USU_ADMIN");
            entity.setDateModif(getTimestamp());
            // Conservar los valores existentes de usuaCrea y dateCreate
            entity.setUsuaCrea(existingEntity.getUsuaCrea());
            entity.setDateCreate(existingEntity.getDateCreate());
            return CelulaDTO.fromEntity(celulaRepository.save(entity));
        } else {
            return null;
        }
    }



    @Override
    public CelulaDTO deleteCelula(Long id) {
        Optional<CelulaEntity> datoExtraido = celulaRepository.findById(id);
        if(datoExtraido.isPresent()){
            datoExtraido.get().setEstadoCelula(false);
            datoExtraido.get().setUsuaDelet("Constant.USU_ADMIN");
            datoExtraido.get().setDateDelet(getTimestamp());
            return CelulaDTO.fromEntity(celulaRepository.save(datoExtraido.get()));
        }else {
            return null;
        }
    }

}
