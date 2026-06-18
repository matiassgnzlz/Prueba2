package com.example.Vacunacion.controller;

import com.example.Vacunacion.dto.VacunacionDTO;
import com.example.Vacunacion.model.Vacunacion;
import com.example.Vacunacion.service.VacunacionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vacunacion")
@RequiredArgsConstructor
public class VacunacionController {

    private final VacunacionService service;

    @PostMapping
    public ResponseEntity<Vacunacion> crear(
            @Valid @RequestBody VacunacionDTO dto) {

        return ResponseEntity.status(201)
                .body(service.crear(dto));
    }

    @GetMapping
    public ResponseEntity<List<Vacunacion>> listar() {

        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vacunacion> obtener(
            @PathVariable Long id) {

        return ResponseEntity.ok(service.obtener(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vacunacion> actualizar(
            @PathVariable Long id,
            @RequestBody VacunacionDTO dto) {

        return ResponseEntity.ok(
                service.actualizar(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(
            @PathVariable Long id) {

        service.eliminar(id);

        return ResponseEntity.ok("Vacuna eliminada correctamente");
    }
}