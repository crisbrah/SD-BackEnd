package com.codigo.sanidaddivina.controller;

import com.codigo.sanidaddivina.business.CelulaService;
import com.codigo.sanidaddivina.dto.CelulaDTO;
import com.codigo.sanidaddivina.request.CelulaRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sanidad-divina/v1/celula")
@AllArgsConstructor

public class CelulaController {
    private final CelulaService celulaServiceIn;

    @PostMapping("/crear")
    public ResponseEntity<CelulaDTO> registrar(@RequestBody CelulaRequest celulaRequest){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(celulaServiceIn.crearCelula(celulaRequest));
    }
    @GetMapping("/buscar/{id}")
    public ResponseEntity<CelulaDTO> buscarxId(@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(celulaServiceIn.buscarCelulaxId(id).get());
    }

    @GetMapping("/nombre/{name}")
    public ResponseEntity<List<CelulaDTO>> buscarxNombre(@PathVariable String name) {
        Optional<List<CelulaDTO>> celulas = celulaServiceIn.buscarCelulaxNombre(name);

        if (celulas.isPresent() && !celulas.get().isEmpty()) {
            return ResponseEntity.ok(celulas.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/buscartodos")
    public ResponseEntity<List<CelulaDTO>> buscartodos(){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(celulaServiceIn.buscarCelulaTodos());
    }
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<CelulaDTO> actualizar(@PathVariable Long id, @RequestBody CelulaRequest celulaRequest){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(celulaServiceIn.actualizarCelula(id,celulaRequest));
    }

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<CelulaDTO> delete(@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(celulaServiceIn.deleteCelula(id));
    }

}
