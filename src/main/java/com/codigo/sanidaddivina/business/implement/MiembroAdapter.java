package com.codigo.sanidaddivina.business.implement;

import com.codigo.sanidaddivina.business.MiembroService;
import com.codigo.sanidaddivina.dao.CelulaRepository;
import com.codigo.sanidaddivina.dao.MiembroRepository;
import com.codigo.sanidaddivina.dao.PersonaRepository;
import com.codigo.sanidaddivina.dto.MiembroDTO;
import com.codigo.sanidaddivina.entities.CelulaEntity;
import com.codigo.sanidaddivina.entities.MiembroEntity;
import com.codigo.sanidaddivina.entities.PersonaEntity;
import com.codigo.sanidaddivina.request.MiembroRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MiembroAdapter implements MiembroService {
    @Autowired
    private MiembroRepository miembroRepository;
    @Autowired
    private PersonaRepository personaRepository;
    @Autowired
    private CelulaRepository celulaRepository;


    @Override

    public MiembroDTO crearMiembro(MiembroRequest miembroRequest) {
         MiembroEntity entity = new MiembroEntity();

        // Buscar la persona por su ID
        PersonaEntity persona = personaRepository.findById(miembroRequest.getIdPersona())
                .orElseThrow(() -> new RuntimeException("Persona no encontrada"));

        // Verificar si se proporcionó un ID de célula
        CelulaEntity celula = null;
        if (miembroRequest.getIdCelula() != null) {
            celula = celulaRepository.findById(miembroRequest.getIdCelula())
                    .orElseThrow(() -> new RuntimeException("Célula no encontrada"));
        }

        entity.setFechaConversion(miembroRequest.getFechaConversion());
        entity.setEstadoMiembro(miembroRequest.isEstadoMiembro());
        entity.setEmail(miembroRequest.getEmail());
        entity.setPassword(new BCryptPasswordEncoder().encode(miembroRequest.getPassword()));
        entity.setPersona(persona);  // Asignar la entidad persona

        // Asignar la entidad célula solo si no es nula
        if (celula != null) {
            entity.setCelula(celula);
        }

        entity.setUsuaCrea("jhoner");
        entity.setDateCreate(getTimestamp());

        MiembroEntity savedEntity = miembroRepository.save(entity);

        // Convertir la entidad guardada a DTO utilizando el mapeador
        return MiembroDTO.fromEntity(savedEntity);

    }


    private Timestamp getTimestamp(){
        long currenTIme = System.currentTimeMillis();
        return new Timestamp(currenTIme);
    }



    @Override
    public Optional<MiembroDTO> buscarMiembroxId(Long id) {

            Optional<MiembroEntity> optionalEntity = miembroRepository.findById(id);
            if (optionalEntity.isPresent()) {
                MiembroEntity entity = optionalEntity.get();
                MiembroDTO miembroDTO = MiembroDTO.fromEntity(entity);

                return Optional.of(miembroDTO);
            } else {
                // Manejar el caso cuando no se encuentra el miembro
                return Optional.empty();
            }

    }

    @Override
    public List<MiembroDTO> buscarMiembroTodos() {
        List<MiembroDTO> listaDto = new ArrayList<>();
        List<MiembroEntity> entidades = miembroRepository.findAll();
        for (MiembroEntity dato :entidades){
            listaDto.add(MiembroDTO.fromEntity(dato));
        }
        return listaDto;
    }

    @Override
    public MiembroDTO actualizarMiembro(Long id, MiembroRequest miembroRequest) {
        Optional<MiembroEntity> datoExtraido = miembroRepository.findById(id);
        MiembroEntity entity = new MiembroEntity();
        if (datoExtraido.isPresent()) {
            entity.setIdMiembro(id);
            entity.setFechaConversion(miembroRequest.getFechaConversion());
            MiembroEntity existingEntity = datoExtraido.get();
            entity.setEstadoMiembro(true);
            entity.setUsuaModif("Jhoner");
            entity.setDateModif(getTimestamp());

            // Buscar la persona por su ID
            PersonaEntity persona = personaRepository.findById(miembroRequest.getIdPersona())
                    .orElseThrow(() -> new RuntimeException("Persona no encontrada"));

            // Verificar si se proporcionó un ID de célula
            CelulaEntity celula = null;
            if (miembroRequest.getIdCelula() != null) {
                celula = celulaRepository.findById(miembroRequest.getIdCelula())
                        .orElseThrow(() -> new RuntimeException("Célula no encontrada"));
            }

            entity.setPersona(persona);
            entity.setCelula(celula);
            // Conservar los valores existentes de usuaCrea y dateCreate
            entity.setUsuaCrea(existingEntity.getUsuaCrea());
            entity.setDateCreate(existingEntity.getDateCreate());
            return MiembroDTO.fromEntity(miembroRepository.save(entity));
        } else {
            throw new ObjetoNoEncontradaException("No se encontró el miembro con el ID: " + id);
        }
    }

    @Override
    public MiembroDTO deleteMiembro(Long id) {
        Optional<MiembroEntity> datoExtraido = miembroRepository.findById(id);
        if(datoExtraido.isPresent()){
            datoExtraido.get().setEstadoMiembro(false);
            datoExtraido.get().setUsuaDelet("Jhoner");
            datoExtraido.get().setDateDelet(getTimestamp());
            return MiembroDTO.fromEntity(miembroRepository.save(datoExtraido.get()));
        }else {
            throw new ObjetoNoEncontradaException("No se encontró el miembro con el ID: " + id);
        }
    }
}
