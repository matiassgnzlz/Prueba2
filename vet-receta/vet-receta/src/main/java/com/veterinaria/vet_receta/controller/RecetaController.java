package com.veterinaria.vet_receta.controller;
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

import com.veterinaria.vet_receta.dto.RecetaDTO;
import com.veterinaria.vet_receta.model.Receta;
import com.veterinaria.vet_receta.service.RecetaService;

@RestController
@RequestMapping("/api/recetas")

public class RecetaController {
    private final RecetaService recetaService;

    public RecetaController (RecetaService recetaService) {
        this.recetaService = recetaService;
    }

    //listar pagos
    @GetMapping()
    public ResponseEntity <List<RecetaDTO>> getListar() {
        List<Receta> recetas = recetaService.listar();
        List<RecetaDTO> dtos = recetas.stream().map(RecetaDTO::fromModel).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    //buscar pago por id
    @GetMapping("/{id}")
    public ResponseEntity<Receta> obtenerPorId(@PathVariable Long id) {
        Receta recet = recetaService.buscarRecetamodel(id);
        return ResponseEntity.ok(recet);
    }

    @PostMapping()
    public ResponseEntity<RecetaDTO> postSave(RecetaDTO recetaDTO) {
        Receta receta = recetaService.guardar(recetaDTO.toModel());
        return ResponseEntity.ok(RecetaDTO.fromModel(receta));
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<RecetaDTO> putModificarProducto(@PathVariable Long id, @RequestBody RecetaDTO recetaDTO) {
        Receta resultado = recetaService.actualizar(id, recetaDTO.toModel()); // ← ya no se llama .toModel()
        if (resultado != null) {
            return ResponseEntity.ok(RecetaDTO.fromModel(resultado));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        recetaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
