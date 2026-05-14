package com.veterinaria.vet_farmacia.controller;


import java.util.List;
import java.util.stream.Collectors;
 
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.veterinaria.vet_farmacia.dto.FarmaciaDTO;
import com.veterinaria.vet_farmacia.model.Farmacia;
import com.veterinaria.vet_farmacia.service.FarmaciaService;
 
 
@RestController
@RequestMapping("/api/farmacias")
public class FarmaciaController {
    private final FarmaciaService farmaciaService;
 
    public FarmaciaController(FarmaciaService farmaciaService) {
        this.farmaciaService = farmaciaService;
    }

    @GetMapping()
    public ResponseEntity<List<FarmaciaDTO>> getListar() {
        List<Farmacia> farmacias = farmaciaService.listar();
        List<FarmaciaDTO> dtos = farmacias.stream().map(FarmaciaDTO::fromModel).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
 

    @GetMapping("/{id}")
    public ResponseEntity<FarmaciaDTO> obtenerPorId(@PathVariable Long id) {
        Farmacia farmacia = farmaciaService.buscarFarmaciaModel(id);
        return ResponseEntity.ok(FarmaciaDTO.fromModel(farmacia));
    }
 
   
    @PostMapping()
    public ResponseEntity<FarmaciaDTO> postSave(@RequestBody FarmaciaDTO farmaciaDTO) {
        Farmacia farmacia = farmaciaService.guardar(farmaciaDTO.toModel());
        return ResponseEntity.ok(FarmaciaDTO.fromModel(farmacia));
    }
 
    
    @PutMapping("/modificar/{id}")
    public ResponseEntity<FarmaciaDTO> putModificar(@PathVariable Long id,
                                                     @RequestBody FarmaciaDTO farmaciaDTO) {
        Farmacia resultado = farmaciaService.actualizar(id, farmaciaDTO.toModel());
        if (resultado != null) {
            return ResponseEntity.ok(FarmaciaDTO.fromModel(resultado));
        }
        return ResponseEntity.notFound().build();
    }
 

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        farmaciaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
