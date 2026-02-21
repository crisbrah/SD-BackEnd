package com.codigo.sanidaddivina.controller;


import com.codigo.sanidaddivina.business.implement.PersonaService;

import com.codigo.sanidaddivina.dto.PersonaDTO;
import com.codigo.sanidaddivina.request.PersonaRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sanidad-divina/v1/persona")
@AllArgsConstructor
public class PersonaController {
    private final PersonaService personaServiceIn;

  //private final ClientSeguridad clientSeguridad;

    @PostMapping("/crear")
    public ResponseEntity<PersonaDTO> registrar(@RequestBody PersonaRequest requestPersona){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(personaServiceIn.crearPersona(requestPersona));
    }
    @GetMapping("/buscarid/{id}")
    public ResponseEntity<PersonaDTO> buscarxId(@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(personaServiceIn.buscarPersonaxId(id).get());
    }

    @GetMapping("/buscardni/{dni}")
    public ResponseEntity<List<PersonaDTO>> buscarxDni(@PathVariable String dni){
        List<PersonaDTO> personas = personaServiceIn.buscarPersonaxIdDni(dni);
        if (personas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok(personas);
        }
    }

    @GetMapping("/buscarnombre/{name}")
    public ResponseEntity<List<PersonaDTO>> buscarxNombre(@PathVariable String name) {
        Optional<List<PersonaDTO>> personas = personaServiceIn.buscarPersonaxNombre(name);

        if (personas.isPresent() && !personas.get().isEmpty()) {
            return ResponseEntity.ok(personas.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/buscartodos")
    public ResponseEntity<List<PersonaDTO>> buscartodos(){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(personaServiceIn.buscarPersonaTodos());
    }
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<PersonaDTO> actualizar(@PathVariable Long id, @RequestBody PersonaRequest personaRequest){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(personaServiceIn.actualizarPersona(id,personaRequest));
    }

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<PersonaDTO> delete(@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(personaServiceIn.deletePersona(id));
    }


}
