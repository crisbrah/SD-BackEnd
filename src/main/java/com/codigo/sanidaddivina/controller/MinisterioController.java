package com.codigo.sanidaddivina.controller;

import com.codigo.sanidaddivina.business.MinisterioService;
import com.codigo.sanidaddivina.dto.MinisterioDTO;
import com.codigo.sanidaddivina.request.MinisterioRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sanidad-divina/v1/ministerio")
@AllArgsConstructor
public class MinisterioController {

    private final MinisterioService ministerioServiceIn;

    @PostMapping("/crear")
    public ResponseEntity<MinisterioDTO> registrar(@RequestBody MinisterioRequest ministerioRequest){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ministerioServiceIn.crearMinisterio(ministerioRequest));
    }
    @GetMapping("/buscar/{id}")
    public ResponseEntity<MinisterioDTO> buscarxId(@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ministerioServiceIn.buscarMinisterioxId(id).get());
    }

    @GetMapping("/nombre/{name}")
    public ResponseEntity<List<MinisterioDTO>> buscarxNombre(@PathVariable String name) {
        Optional<List<MinisterioDTO>> ministerioDTOS = ministerioServiceIn.buscarMinisterioxNombre(name);

        if (ministerioDTOS.isPresent() && !ministerioDTOS.get().isEmpty()) {
            return ResponseEntity.ok(ministerioDTOS.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/buscartodos")
    public ResponseEntity<List<MinisterioDTO>> buscartodos(){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ministerioServiceIn.buscarMinisterioTodos());
    }
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<MinisterioDTO> actualizar(@PathVariable Long id, @RequestBody MinisterioRequest ministerioRequest){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ministerioServiceIn.actualizarMinisterio(id,ministerioRequest));
    }

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<MinisterioDTO> delete(@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ministerioServiceIn.deleteMinisterio(id));
    }

}
