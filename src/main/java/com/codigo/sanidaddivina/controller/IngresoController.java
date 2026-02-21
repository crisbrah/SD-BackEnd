package com.codigo.sanidaddivina.controller;


import com.codigo.sanidaddivina.business.IngresoService;
import com.codigo.sanidaddivina.dto.IngresoDTO;
import com.codigo.sanidaddivina.request.IngresoRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sanidad-divina/v1/ingreso")
@AllArgsConstructor

public class IngresoController {
    private final IngresoService ingresoServiceIn;

    @PostMapping("/crear")
    public ResponseEntity<IngresoDTO> registrar(@RequestBody IngresoRequest ingresoRequest){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ingresoServiceIn.crearIngreso(ingresoRequest));
    }
    @GetMapping("/buscar/{id}")
    public ResponseEntity<IngresoDTO> buscarxId(@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ingresoServiceIn.buscarIngresoxId(id).get());
    }



    @GetMapping("/buscartodos")
    public ResponseEntity<List<IngresoDTO>> buscartodos(){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ingresoServiceIn.buscarIngresoTodos());
    }
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<IngresoDTO> actualizar(@PathVariable Long id, @RequestBody IngresoRequest ingresoRequest){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ingresoServiceIn.actualizarIngreso(id,ingresoRequest));
    }

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<IngresoDTO> delete(@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ingresoServiceIn.deleteIngreso(id));
    }
}
