package com.codigo.sanidaddivina.business.implement;


import com.codigo.sanidaddivina.business.IngresoService;
import com.codigo.sanidaddivina.dao.IngresoRepository;
import com.codigo.sanidaddivina.domain.model.enums.MetodoPago;
import com.codigo.sanidaddivina.dto.IngresoDTO;
import com.codigo.sanidaddivina.entities.IngresoEntity;
import com.codigo.sanidaddivina.request.IngresoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class IngresoAdapter implements IngresoService {
    private final IngresoRepository ingresoRepository;

    public IngresoAdapter(IngresoRepository ingresoRepository) {
        this.ingresoRepository = ingresoRepository;
    }


    @Override
    public IngresoDTO crearIngreso(IngresoRequest ingresoRequest) {
        // Crear una nueva instancia de CelulaEntity
        IngresoEntity entity = new IngresoEntity();

        // Configurar los campos necesarios de la entidad
        entity.setConcepto(ingresoRequest.getConcepto());
        entity.setMonto(ingresoRequest.getMonto());
        entity.setMetodoPago(MetodoPago.valueOf(ingresoRequest.getMetodoPago()));
        entity.setUsuaCrea("Jhoner");
        entity.setDateCreate(getTimestamp());

        // Guardar la entidad usando el repositorio
        IngresoEntity savedEntity = ingresoRepository.save(entity);

        // Convertir la entidad guardada a DTO utilizando el mapeador
        return IngresoDTO.fromEntity(savedEntity);
    }

    private Timestamp getTimestamp(){
        long currenTIme = System.currentTimeMillis();
        return new Timestamp(currenTIme);
    }

    @Override
    public Optional<IngresoDTO> buscarIngresoxId(Long id) {
            Optional<IngresoEntity> optionalEntity = ingresoRepository.findById(id);
            if (optionalEntity.isPresent()) {
                IngresoEntity entity = optionalEntity.get();
                IngresoDTO ingresoDTO = IngresoDTO.fromEntity(entity);
                return Optional.of(ingresoDTO);
            } else {
                // Manejar el caso cuando no se encuentra el ingreso
                return Optional.empty();
            }

    }

    @Override
    public Optional<List<IngresoDTO>> buscarIngresoxFecha(Date fecha) {
        return Optional.empty();
    }


    public List<IngresoDTO> buscarIngresoTodos() {
        List<IngresoDTO> listaDto = new ArrayList<>();
        List<IngresoEntity> entidades = ingresoRepository.findAll();
        for (IngresoEntity dato :entidades){
            listaDto.add(IngresoDTO.fromEntity(dato));
        }
        return listaDto;
    }

    @Override
    public IngresoDTO actualizarIngreso(Long id, IngresoRequest ingresoRequest) {
        Optional<IngresoEntity> datoExtraido = ingresoRepository.findById(id);
        IngresoEntity entity = new IngresoEntity();
        if (datoExtraido.isPresent()) {
            entity.setIdIngreso(id);
            IngresoEntity existingEntity = datoExtraido.get();
            entity.setConcepto(ingresoRequest.getConcepto());
            entity.setMonto(ingresoRequest.getMonto());
            entity.setMetodoPago(MetodoPago.valueOf(ingresoRequest.getMetodoPago()));
            entity.setUsuaModif("Jhoner");
            entity.setDateModif(getTimestamp());
            // Conservar los valores existentes de usuaCrea y dateCreate
            entity.setUsuaCrea(existingEntity.getUsuaCrea());
            entity.setDateCreate(existingEntity.getDateCreate());
            return IngresoDTO.fromEntity(ingresoRepository.save(entity));
        } else {
            throw new ObjetoNoEncontradaException("No se encontró el ingreso con el ID: " + id);
        }
    }

    @Override
    public IngresoDTO deleteIngreso(Long id) {
        Optional<IngresoEntity> datoExtraido = ingresoRepository.findById(id);
        if(datoExtraido.isPresent()){
            datoExtraido.get().setUsuaDelet("Constant.USU_ADMIN");
            datoExtraido.get().setDateDelet(getTimestamp());
            return IngresoDTO.fromEntity(ingresoRepository.save(datoExtraido.get()));
        }else {
            throw new ObjetoNoEncontradaException("No se encontró el ingreso con el ID: " + id);
        }
    }
}
