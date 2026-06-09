package com.example.Vacunacion.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Vacunacion.dto.VacunacionDTO;
import com.example.Vacunacion.model.Vacunacion;
import com.example.Vacunacion.service.VacunacionService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/vacunacion")
@RequiredArgsConstructor
public class VacunacionController {
    private final VacunacionService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<ApiResponse<Vacunacion>> crear(
            @Valid @RequestBody VacunacionDTO dto) {

        return ResponseEntity.status(201).body(
                ApiResponse.<Vacunacion>builder()
                        .success(true)
                        .message("Vacuna registrada")
                        .data(service.crear(dto))
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<ApiResponse<List<Vacunacion>>> listar() {

        return ResponseEntity.ok(
                ApiResponse.<List<Vacunacion>>builder()
                        .success(true)
                        .message("Listado obtenido")
                        .data(service.listar())
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Vacunacion>> obtener(@PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.<Vacunacion>builder()
                        .success(true)
                        .message("Vacuna encontrada")
                        .data(service.obtener(id))
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Vacunacion>> actualizar(
            @PathVariable Long id,
            @RequestBody VacunacionDTO dto) {

        return ResponseEntity.ok(
                ApiResponse.<Vacunacion>builder()
                        .success(true)
                        .message("Vacuna actualizada")
                        .data(service.actualizar(id, dto))
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {

        service.eliminar(id);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Vacuna eliminada")
                        .build()
        );
    }
}
