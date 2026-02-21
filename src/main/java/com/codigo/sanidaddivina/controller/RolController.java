package com.codigo.sanidaddivina.controller;


import com.codigo.sanidaddivina.business.RolService;
import com.codigo.sanidaddivina.dto.RolDTO;
import com.codigo.sanidaddivina.request.RolRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sanidad-divina/v1/rol")
@AllArgsConstructor

public class RolController {
    private final RolService rolServiceIn;

    @PostMapping("/crear")
    public ResponseEntity<RolDTO> registrar(@RequestBody RolRequest rolRequest){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(rolServiceIn.crearRol(rolRequest));
    }
    @GetMapping("/buscar/{id}")
    public ResponseEntity<RolDTO> buscarxId(@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(rolServiceIn.buscarRolxId(id).get());
    }

    @GetMapping("/nombre/{name}")
    public ResponseEntity<List<RolDTO>> buscarxNombre(@PathVariable String name) {
        Optional<List<RolDTO>> rol = rolServiceIn.buscarRolxNombre(name);
        if (rol.isPresent() && !rol.get().isEmpty()) {
            return ResponseEntity.ok(rol.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/buscartodos")
    public ResponseEntity<List<RolDTO>> buscartodos(){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(rolServiceIn.buscarRolTodos());
    }
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<RolDTO> actualizar(@PathVariable Long id, @RequestBody RolRequest rolRequest){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(rolServiceIn.actualizarRol(id,rolRequest));
    }

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<RolDTO> delete(@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(rolServiceIn.deleteRol(id));
    }
}
