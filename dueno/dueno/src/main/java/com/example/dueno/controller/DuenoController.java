package com.example.dueno.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.dueno.dto.ApiResponse;
import com.example.dueno.dto.DuenoDTO;
import com.example.dueno.model.Dueno;
import com.example.dueno.service.DuenoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/dueno")
@RequiredArgsConstructor
public class DuenoController {

    private final DuenoService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<ApiResponse<Dueno>> crear(@Valid @RequestBody DuenoDTO dto) {

        Dueno dueno = service.crear(dto);

        return ResponseEntity.status(201).body(
                ApiResponse.<Dueno>builder()
                        .success(true)
                        .message("Dueno creado")
                        .data(dueno)
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<List<Dueno>>> listar() {

        return ResponseEntity.ok(
                ApiResponse.<List<Dueno>>builder()
                        .success(true)
                        .message("Listado obtenido")
                        .data(service.listar())
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<Dueno>> obtener(@PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.<Dueno>builder()
                        .success(true)
                        .message("Dueno obtenido")
                        .data(service.obtener(id))
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Dueno>> actualizar(@PathVariable Long id,
                                                        @Valid @RequestBody DuenoDTO dto) {

        Dueno dueno = service.actualizar(id, dto);

        return ResponseEntity.ok(
                ApiResponse.<Dueno>builder()
                        .success(true)
                        .message("Dueno actualizado")
                        .data(dueno)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {

        service.eliminar(id);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Dueno eliminado")
                        .build()
        );
    }
}