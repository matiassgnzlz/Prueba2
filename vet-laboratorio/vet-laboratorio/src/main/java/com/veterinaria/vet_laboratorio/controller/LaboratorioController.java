package com.veterinaria.vet_laboratorio.controller;

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

import com.veterinaria.vet_laboratorio.dto.LaboratorioDTO;
import com.veterinaria.vet_laboratorio.model.Laboratorio;
import com.veterinaria.vet_laboratorio.service.LaboratorioService;

@RestController
@RequestMapping("api/laboratorios")
public class LaboratorioController {
    private final LaboratorioService laboratorioService;
 
    public LaboratorioController(LaboratorioService laboratorioService) {
        this.laboratorioService = laboratorioService;
    }
 
    // listar
    @GetMapping()
    public ResponseEntity<List<LaboratorioDTO>> getListar() {
        List<Laboratorio> laboratorios = laboratorioService.listar();
        List<LaboratorioDTO> dtos = laboratorios.stream().map(LaboratorioDTO::fromModel).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
 
    // listar por persona a cargo
    @GetMapping("/persona/{persona}")
    public ResponseEntity<List<LaboratorioDTO>> getListarPorPersona(@PathVariable String persona) {
        List<Laboratorio> laboratorios = laboratorioService.listarPorPersona(persona);
        List<LaboratorioDTO> dtos = laboratorios.stream().map(LaboratorioDTO::fromModel).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
 
    // buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<LaboratorioDTO> obtenerPorId(@PathVariable Long id) {
        Laboratorio laboratorio = laboratorioService.buscarLaboratorioModel(id);
        return ResponseEntity.ok(LaboratorioDTO.fromModel(laboratorio));
    }
 
    // guardar
    @PostMapping()
    public ResponseEntity<LaboratorioDTO> postSave(@RequestBody LaboratorioDTO laboratorioDTO) {
        Laboratorio laboratorio = laboratorioService.guardar(laboratorioDTO.toModel());
        return ResponseEntity.ok(LaboratorioDTO.fromModel(laboratorio));
    }
 
    // modificar
    @PutMapping("/modificar/{id}")
    public ResponseEntity<LaboratorioDTO> putModificar(@PathVariable Long id,
                                                        @RequestBody LaboratorioDTO laboratorioDTO) {
        Laboratorio resultado = laboratorioService.actualizar(id, laboratorioDTO.toModel());
        if (resultado != null) {
            return ResponseEntity.ok(LaboratorioDTO.fromModel(resultado));
        }
        return ResponseEntity.notFound().build();
    }
 
    // eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        laboratorioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
