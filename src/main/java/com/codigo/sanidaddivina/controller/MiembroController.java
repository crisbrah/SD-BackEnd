package com.codigo.sanidaddivina.controller;


import com.codigo.sanidaddivina.business.MiembroService;
import com.codigo.sanidaddivina.dto.MiembroDTO;
import com.codigo.sanidaddivina.request.MiembroRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sanidad-divina/v1/miembro")
@AllArgsConstructor

public class MiembroController {

    private final MiembroService miembroServiceIn;

    @PostMapping("/crear")
    public ResponseEntity<MiembroDTO> registrar(@RequestBody MiembroRequest miembroRequest){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(miembroServiceIn.crearMiembro(miembroRequest));
    }
    @GetMapping("/buscar/{id}")
    public ResponseEntity<MiembroDTO> buscarxId(@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(miembroServiceIn.buscarMiembroxId(id).get());
    }

    @GetMapping("/buscartodos")
    public ResponseEntity<List<MiembroDTO>> buscartodos(){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(miembroServiceIn.buscarMiembroTodos());
    }
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<MiembroDTO> actualizar(@PathVariable Long id, @RequestBody MiembroRequest miembroRequest){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(miembroServiceIn.actualizarMiembro(id,miembroRequest));
    }

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<MiembroDTO> delete(@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(miembroServiceIn.deleteMiembro(id));
    }
}
